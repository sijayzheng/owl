package cn.sijay.owl.gen.service;

import cn.sijay.owl.common.enums.NamingCase;
import cn.sijay.owl.common.exceptions.BaseException;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.common.utils.FileUtil;
import cn.sijay.owl.common.utils.NamingUtil;
import cn.sijay.owl.gen.constants.GenConstants;
import cn.sijay.owl.gen.entity.ColumnInfo;
import cn.sijay.owl.gen.entity.GenColumn;
import cn.sijay.owl.gen.entity.GenTable;
import cn.sijay.owl.gen.entity.TableInfo;
import cn.sijay.owl.gen.enums.HtmlType;
import cn.sijay.owl.gen.enums.JavaType;
import cn.sijay.owl.gen.enums.QueryType;
import cn.sijay.owl.gen.properties.GenProperties;
import cn.sijay.owl.gen.utils.SqlTypesUtil;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    private final JdbcTemplate jdbcTemplate;

    /**
     * 获取数据库中所有表的信息
     */
    List<TableInfo> getTables(List<String> tableNames) throws SQLException {
        List<TableInfo> tables = new ArrayList<>();
        try (Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String catalog = connection.getCatalog();
            String schema = connection.getSchema();
            ResultSet rs = metaData.getTables(catalog, schema, "%", new String[]{"TABLE"});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                if (Strings.CI.equalsAny(tableName, tableNames.toArray(new String[0]))) {
                    List<String> primaryKeys = getPrimaryKeys(catalog, schema, tableName);
                    tables.add(new TableInfo(
                        tableName.toLowerCase(),
                        rs.getString("REMARKS"),
                        getTableColumns(catalog, schema, tableName, primaryKeys)
                    ));
                }
            }
            rs.close();
        }
        return tables;
    }

    /**
     * 获取指定表的所有字段信息
     *
     * @param tableName 表名
     */
    List<ColumnInfo> getTableColumns(String catalog, String schema, String tableName, List<String> primaryKeys) throws SQLException {
        List<ColumnInfo> columns = new ArrayList<>();

        try (Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getColumns(catalog, schema, tableName, null);
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                ColumnInfo columnInfo = new ColumnInfo(
                    columnName,     // 列名
                    rs.getInt("DATA_TYPE"),             // SQL 数据类型（int 值）
                    rs.getInt("COLUMN_SIZE"),         // 列长度/精度
                    rs.getBoolean("NULLABLE"),              // 是否允许 NULL
                    rs.getString("REMARKS"),             // 列注释
                    rs.getInt("ORDINAL_POSITION"),             // 列注释
                    rs.getBoolean("IS_AUTOINCREMENT"),// 是否自增
                    primaryKeys.contains(columnName)
                );
                columns.add(columnInfo);
            }
            rs.close();
        }
        return columns;
    }

    /**
     * 获取指定表的主键信息
     */
    List<String> getPrimaryKeys(String catalog, String schema, String tableName) throws SQLException {
        List<String> primaryKeys = new ArrayList<>();
        try (Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getPrimaryKeys(catalog, schema, tableName);
            while (rs.next()) {
                primaryKeys.add(rs.getString("COLUMN_NAME"));
            }
            rs.close();
        }
        return primaryKeys;
    }

    @Transactional
    public void importTable(List<String> tables) {
        try {
            List<TableInfo> tableInfos = getTables(tables);
            for (TableInfo tableInfo : tableInfos) {
                String tableName = tableInfo.tableName();
                String remarks = tableInfo.remarks();
                GenTable genTable = new GenTable();
                genTable.setTableName(tableName);
                genTable.setTableComment(remarks);
                genTable.setModuleName(StringUtils.substringBefore(tableName, "_"));
                genTable.setClassName(NamingUtil.caseConvert(tableName, NamingCase.UPPER_CAMEL_CASE));
                genTable.setClassComment(remarks.replaceAll("表$", ""));
                genTable.setFunctionName(NamingUtil.caseConvert(tableName, NamingCase.LOWER_CAMEL_CASE));
                tableService.save(genTable);
                Long tableId = genTable.getId();
                List<GenColumn> list = tableInfo.columns()
                                                .stream()
                                                .map(columnInfo -> {
                                                    String columnName = columnInfo.columnName().toLowerCase();
                                                    GenColumn column = new GenColumn();
                                                    column.setTableId(tableId);
                                                    column.setColumnName(columnName);
                                                    column.setColumnComment(columnInfo.remarks());
                                                    column.setColumnType(SqlTypesUtil.getDataTypeName(columnInfo.dataType()));
                                                    column.setJavaType(switch (column.getColumnType()) {
                                                        case "bit", "boolean" -> JavaType.BOOLEAN;
                                                        case "tinyint", "smallint", "integer" -> JavaType.INTEGER;
                                                        case "bigint" -> JavaType.LONG;
                                                        case "real" -> JavaType.FLOAT;
                                                        case "float", "double" -> JavaType.DOUBLE;
                                                        case "decimal", "numeric" -> JavaType.BIG_DECIMAL;
                                                        case "binary", "varbinary", "longvarbinary", "blob" -> JavaType.BYTE_ARRAY;
                                                        case "date" -> JavaType.LOCAL_DATE;
                                                        case "time", "time_with_timezone" -> JavaType.LOCAL_TIME;
                                                        case "timestamp", "timestamp_with_timezone" -> JavaType.LOCAL_DATE_TIME;
                                                        default -> JavaType.STRING;
                                                    });
                                                    column.setJavaField(NamingUtil.caseConvert(columnName, NamingCase.LOWER_CAMEL_CASE));
                                                    boolean primaryKey = columnInfo.primaryKey();
                                                    column.setPrimaryKey(primaryKey);
                                                    column.setIncremental(columnInfo.increment());
                                                    column.setRequired(!columnInfo.nullable());
                                                    column.setSort(columnInfo.sort());
                                                    int maxLength = columnInfo.maxLength();
                                                    if (JavaType.STRING.equals(column.getJavaType())) {
                                                        column.setMaxLength(maxLength);
                                                    }
                                                    column.setQueryType(switch (column.getJavaType()) {
                                                        case JavaType.STRING -> QueryType.LIKE;
                                                        case JavaType.LOCAL_DATE, JavaType.LOCAL_TIME, JavaType.LOCAL_DATE_TIME -> QueryType.BETWEEN;
                                                        default -> QueryType.EQUALS;
                                                    });
                                                    column.setHtmlType(switch (column.getJavaType()) {
                                                        case JavaType.STRING -> maxLength < 500 ? HtmlType.INPUT : HtmlType.TEXTAREA;
                                                        case JavaType.LOCAL_DATE -> HtmlType.DATE;
                                                        case JavaType.LOCAL_TIME -> HtmlType.TIME;
                                                        case JavaType.LOCAL_DATE_TIME -> HtmlType.DATETIME;
                                                        case JavaType.BOOLEAN -> HtmlType.RADIO;
                                                        case JavaType.INTEGER, JavaType.FLOAT, JavaType.DOUBLE, JavaType.BIG_DECIMAL -> HtmlType.NUMBER;
                                                        case JavaType.LONG -> columnName.contains("id") ? HtmlType.SELECT : HtmlType.NUMBER;
                                                        case JavaType.BYTE_ARRAY -> HtmlType.FILE;
                                                    });
                                                    boolean need = !GenConstants.NEEDLESS.contains(columnName);
                                                    column.setEditable(need);
                                                    boolean flag = need && GenConstants.NEED_QUERY.contains(column.getHtmlType());
                                                    column.setListable(flag);
                                                    column.setQueryable(!primaryKey && !"remark".equals(columnName) && flag);
                                                    return column;
                                                })
                                                .toList();
                columnService.saveBatch(list);
            }
        } catch (SQLException e) {
            throw new BaseException("数据表导入失败，" + e.getMessage());
        }
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
        String vuePath = FileUtil.joinPath(rootPath, "ui", "src");
        try {
//            FileUtil.writeToFile(FileUtil.joinPath(javaPath, "entity", className + ".java"), codeMap.get("entity.java"));
            if (!table.getEntityOnly()) {
//                FileUtil.writeToFile(FileUtil.joinPath(javaPath, "dto", className + "Query.java"), codeMap.get("query.java"));
//                FileUtil.writeToFile(FileUtil.joinPath(javaPath, "mapper", className + "Mapper.java"), codeMap.get("mapper.java"));
//                FileUtil.writeToFile(FileUtil.joinPath(javaPath, "service", className + "Service.java"), codeMap.get("service.java"));
//                FileUtil.writeToFile(FileUtil.joinPath(javaPath, "controller", className + "Controller.java"), codeMap.get("controller.java"));
//                FileUtil.writeToFile(FileUtil.joinPath(rootPath, "menuSql", className + ".sql"), codeMap.get("sql"));
//                FileUtil.writeToFile(FileUtil.joinPath(vuePath, "types", moduleName, className + "Types.ts"), codeMap.get("types.ts"));
                FileUtil.writeToFile(FileUtil.joinPath(vuePath, "api", moduleName, functionName + "Api.ts"), codeMap.get("api.ts"));
                FileUtil.writeToFile(FileUtil.joinPath(vuePath, "views", moduleName, functionName + ".vue"), codeMap.get("index.vue"));
            }
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
        codes.put("index.vue", processTemplate("index" + (isTree ? "-tree" : "") + ".vue.ftl", data));
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
        data.put("primaryKey", columns.stream().filter(GenColumn::getPrimaryKey).findFirst().orElse(new GenColumn()));
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
