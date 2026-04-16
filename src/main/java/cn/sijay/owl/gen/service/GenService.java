package cn.sijay.owl.gen.service;

import cn.sijay.owl.common.enums.NamingCase;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.common.utils.FileUtil;
import cn.sijay.owl.common.utils.NamingUtil;
import cn.sijay.owl.gen.constants.GenConstants;
import cn.sijay.owl.gen.entity.GenColumn;
import cn.sijay.owl.gen.entity.GenTable;
import cn.sijay.owl.gen.enums.HtmlType;
import cn.sijay.owl.gen.enums.JavaType;
import cn.sijay.owl.gen.enums.QueryType;
import cn.sijay.owl.gen.properties.GenProperties;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * GenService
 *
 * @author sijay
 * @since 2026-04-08
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class GenService {
    private final GenTableService tableService;
    private final GenColumnService columnService;
    private final Configuration configuration;
    private final GenProperties genProperties;

    @Transactional
    public void importTable(@NotEmpty List<String> tables) {
        for (String table : tables) {
            GenTable genTable = tableService.getOne(QueryWrapper.create()
                                                                .select("table_name", "table_comment")
                                                                .from("information_schema.tables")
                                                                .where("table_schema=schema()")
                                                                .and(new QueryColumn("table_name").eq(table)));
            if (genTable == null) {
                throw new ServiceException(GenService.class, "表{}不存在", table);
            }
            genTable.setModuleName(StringUtils.substringBefore(table, "_"));
            genTable.setClassName(NamingUtil.caseConvert(table, NamingCase.UPPER_CAMEL_CASE));
            genTable.setClassComment(genTable.getTableComment().replaceAll("表$", ""));
            genTable.setFunctionName(NamingUtil.caseConvert(table, NamingCase.LOWER_CAMEL_CASE));
            tableService.save(genTable);
            Long tableId = genTable.getId();
            QueryWrapper query = QueryWrapper.create()
                                             .select(
                                                 "column_name",
                                                 "ordinal_position as sort",
                                                 "is_nullable='NO' as required",
                                                 "column_type",
                                                 "column_key='PRI' as primary_key",
                                                 "extra='auto_increment' as incremental",
                                                 "column_comment"
                                             )
                                             .from("information_schema.columns")
                                             .where("TABLE_SCHEMA=schema()")
                                             .and(new QueryColumn("table_name").eq(table))
                                             .orderBy(new QueryColumn("ordinal_position").asc());
            List<GenColumn> list = columnService.list(query);
            for (GenColumn column : list) {
                String columnType = column.getColumnType();
                String dataType = (columnType.contains("(") ? StringUtils.substringBefore(columnType, "(") : columnType).toLowerCase();
                // 统一转小写 避免有些数据库默认大写问题 如果需要特别书写方式 请在实体类增加注解标注别名
                String columnName = column.getColumnName().toLowerCase();

                column.setTableId(tableId);
                column.setJavaType(switch (dataType) {
                    case "date" -> JavaType.LOCAL_DATE;
                    case "time" -> JavaType.LOCAL_TIME;
                    case "datetime", "timestamp" -> JavaType.LOCAL_DATE_TIME;
                    case "tinyint" -> columnType.contains("(") && getLength(columnType) > 1 ? JavaType.INTEGER : JavaType.BOOLEAN;
                    case "bit" -> JavaType.BOOLEAN;
                    case "smallint", "mediumint", "int", "integer", "year" -> JavaType.INTEGER;
                    case "bigint" -> JavaType.LONG;
                    case "float" -> JavaType.FLOAT;
                    case "double" -> JavaType.DOUBLE;
                    case "decimal", "numeric" -> JavaType.BIG_DECIMAL;
                    case "binary", "varbinary", "tinyblob", "blob", "mediumblob", "longblob" -> JavaType.BYTE_ARRAY;
                    default -> JavaType.STRING;
                });
                column.setJavaField(NamingUtil.caseConvert(column.getColumnName(), NamingCase.LOWER_CAMEL_CASE));
                boolean isSelect = Strings.CS.containsAny(dataType, "enum", "set");
                column.setQueryType(switch (column.getJavaType()) {
                    case JavaType.STRING -> isSelect ? QueryType.EQUALS : QueryType.LIKE;
                    case JavaType.LOCAL_DATE, JavaType.LOCAL_TIME, JavaType.LOCAL_DATE_TIME -> QueryType.BETWEEN;
                    case JavaType.LONG -> columnName.contains("id") ? QueryType.IN : QueryType.EQUALS;
                    default -> QueryType.EQUALS;
                });
                column.setHtmlType(switch (column.getJavaType()) {
                    case JavaType.STRING -> isSelect ? HtmlType.SELECT : columnType.contains("(") && getLength(columnType) >= 500 ? HtmlType.TEXTAREA : HtmlType.INPUT;
                    case JavaType.LOCAL_DATE -> HtmlType.DATE;
                    case JavaType.LOCAL_TIME -> HtmlType.TIME;
                    case JavaType.LOCAL_DATE_TIME -> HtmlType.DATETIME;
                    case JavaType.BOOLEAN -> HtmlType.RADIO;
                    case JavaType.INTEGER, JavaType.FLOAT, JavaType.DOUBLE, JavaType.BIG_DECIMAL -> HtmlType.NUMBER;
                    case JavaType.LONG -> columnName.contains("id") ? HtmlType.SELECT : HtmlType.NUMBER;
                    case JavaType.BYTE_ARRAY -> HtmlType.FILE;
                });
                boolean need = !GenConstants.NEEDLESS.contains(columnName);
                column.setInsertable(need && !column.getPrimaryKey());
                column.setEditable(need);
                boolean flag = need && switch (column.getHtmlType()) {
                    case HtmlType.INPUT, HtmlType.NUMBER, HtmlType.SELECT, HtmlType.RADIO, HtmlType.DATETIME, HtmlType.DATE, HtmlType.TIME -> true;
                    default -> false;
                };
                column.setListable(flag);
                column.setQueryable(!column.getPrimaryKey() && !"remark".equals(columnName) && flag);
                columnService.save(column);
            }
        }
    }

    private int getLength(String columnType) {
        return Integer.parseInt(StringUtils.substringBetween(columnType, "(", ")"));
    }

    /**
     * 生成代码
     *
     * @param tableId 表ID
     */
    public void generateCode(Long tableId) {
        // 生成代码
        // 获取表信息
        GenTable table = tableService.getById(tableId);
        if (table == null) {
            throw new ServiceException(getClass(), "表信息不存在");
        }
        // 获取列信息
        List<GenColumn> columns = columnService.listByTableId(tableId);
        if (CollectionUtils.isEmpty(columns)) {
            throw new ServiceException(getClass(), "列信息不存在");
        }
        Map<String, Object> data = processData(table, columns);
        Map<String, String> codeMap = genCode(data, table.getTreeTable());

        String moduleName = table.getModuleName();
        String className = table.getClassName();
        String functionName = table.getFunctionName();

        String rootPath = System.getProperty("user.dir");
        String javaPath = FileUtil.joinPath(rootPath, "src", "main", "java", genProperties.getPackageName().replace('.', File.separatorChar), moduleName);
        String vuePath = FileUtil.joinPath(rootPath, "vue", "src");
        try {
            FileUtil.writeToFile(FileUtil.joinPath(javaPath, "entity", className + ".java"), codeMap.get("entity.java"));
            FileUtil.writeToFile(FileUtil.joinPath(javaPath, "mapper", className + "Mapper.java"), codeMap.get("mapper.java"));
            FileUtil.writeToFile(FileUtil.joinPath(javaPath, "service", className + "Service.java"), codeMap.get("service.java"));
            FileUtil.writeToFile(FileUtil.joinPath(javaPath, "controller", className + "Controller.java"), codeMap.get("controller.java"));
            FileUtil.writeToFile(FileUtil.joinPath(rootPath, "menuSql", className + ".sql"), codeMap.get("sql"));
//            FileUtil.writeToFile(FileUtil.joinPath(vuePath, "api", moduleName, functionName + ".ts"), codeMap.get("api.ts"));
//            FileUtil.writeToFile(FileUtil.joinPath(vuePath, "types", moduleName, functionName + ".ts"), codeMap.get("types.ts"));
//            FileUtil.writeToFile(FileUtil.joinPath(vuePath, "pages", moduleName, functionName + ".vue"), codeMap.get("index.vue"));
//            FileUtil.writeToFile(FileUtil.joinPath(vuePath, "pages", moduleName, functionName + "Form.vue"), codeMap.get("dialog.vue"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(getClass(), "渲染模板失败，表名：" + table.getTableName());
        }
    }

    /**
     * 生成代码
     */
    private Map<String, String> genCode(Map<String, Object> data, Boolean isTree) {
        // 生成文件列表
        Map<String, String> codes = new LinkedHashMap<>();
        for (String template : GenConstants.TEMPLATES) {
            codes.put(template, processTemplate(template + ".ftl", data));
        }
//        codes.put("index.vue", processTemplate("index" + (isTree ? "-tree" : "") + ".ftl", data));
        return codes;
    }

    private Map<String, Object> processData(GenTable table, List<GenColumn> columns) {
        Map<String, Object> data = new HashMap<>();
        data.put("tableName", table.getTableName());
        data.put("tableComment", table.getTableComment());
        data.put("packageName", genProperties.getPackageName());
        data.put("moduleName", table.getModuleName());
        data.put("className", table.getClassName());
        data.put("classComment", table.getClassComment());
        data.put("path", NamingUtil.caseConvert(table.getTableName(), NamingCase.KEBAB_CASE));
        data.put("tableDef", table.getTableName().toUpperCase());
        data.put("functionName", table.getFunctionName());
        data.put("author", genProperties.getAuthor());
        data.put("isTree", table.getTreeTable());
        if (table.getTreeTable()) {
            data.put("treeKey", NamingUtil.caseConvert(table.getTreeKey(), NamingCase.LOWER_CAMEL_CASE));
            data.put("treeParentKey", NamingUtil.caseConvert(table.getTreeParentKey(), NamingCase.LOWER_CAMEL_CASE));
            data.put("treeLabel", NamingUtil.caseConvert(table.getTreeLabel(), NamingCase.LOWER_CAMEL_CASE));
        }
        data.put("menuId", table.getMenuId());
        data.put("columns", columns);
        List<String> imports = columns.parallelStream().map(GenColumn::getJavaType).map(JavaType::getPackageName).distinct()
                                      .filter(StringUtils::isNotBlank).sorted()
                                      .toList();
        data.put("imports", imports);
        data.put("hasBase", CollectionUtils.containsAll(columns.parallelStream().map(GenColumn::getColumnName).toList(), GenConstants.BASE_FIELD));
        data.put("date", "2026-04-09");// LocalDate.now());
        return data;
    }

    public List<GenTable> listDbTable(GenTable genTable) {
        return tableService.list(QueryWrapper.create()
                                             .select("table_name", "table_comment")
                                             .from("information_schema.tables")
                                             .where("table_schema=schema()")
                                             .and(new QueryColumn("table_name").like(genTable.getTableName()))
                                             .and(new QueryColumn("table_comment").like(genTable.getTableComment()))
        );
    }

    /**
     * 处理模板并返回结果
     *
     * @param templateName 模板名称
     * @param data         数据模型
     * @return 渲染后的内容
     */
    private String processTemplate(String templateName, Map<String, Object> data) {
        try {
            return FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(templateName), data);
        } catch (IOException | TemplateException e) {
            log.error("渲染模板失败: ", e);
            throw new ServiceException(getClass(), "渲染模板失败: ", e);
        }
    }
}
