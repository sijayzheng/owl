package cn.sijay.owl.gen.service;


import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.common.utils.StringUtil;
import cn.sijay.owl.gen.entity.GenColumn;
import cn.sijay.owl.gen.entity.GenTable;
import cn.sijay.owl.gen.enums.HtmlType;
import cn.sijay.owl.gen.enums.JavaType;
import cn.sijay.owl.gen.enums.QueryType;
import cn.sijay.owl.gen.mapper.GenTableMapper;
import cn.sijay.owl.gen.properties.GenProperties;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * GenService
 *
 * @author sijay
 * @since 2026/4/8
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class GenService {
    private final GenTableService tableService;
    private final GenColumnService columnService;
    private final GenTableMapper genTableMapper;
    private final GenProperties genProperties;
    private final static List<String> NEEDLESS = List.of("create_dept", "create_by", "create_time", "is_deleted", "update_by", "update_time", "version");

    @Transactional
    public boolean importTable(@NotEmpty List<String> tables) {
        for (String table : tables) {
            GenTable genTable = tableService.getOne(QueryWrapper.create()
                                                                .select("TABLE_NAME", "TABLE_COMMENT")
                                                                .from("information_schema.TABLES")
                                                                .where("TABLE_SCHEMA=schema()")
                                                                .and(new QueryColumn("table_name").eq(table)));
            if (genTable == null) {
                throw new ServiceException(GenService.class, "表{}不存在", table);
            }
            genTable.setModuleName(StringUtils.substringBefore(table, "_"));
            genTable.setClassName(StringUtil.toUpperCamelCase(table));
            genTable.setClassComment(genTable.getTableComment().replaceAll("表$", ""));
            genTable.setFunctionName(StringUtil.toLowerSnakeCase(table));
            tableService.save(genTable);
            Long tableId = genTable.getId();
            List<GenColumn> list = columnService.list(QueryWrapper.create()
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
                                                                  .and(new QueryColumn("table_name").eq(table)));
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
                    case "binary", "varbinary", "tinyblob", "blob", "mediumblob" -> JavaType.BYTE_ARRAY;
                    default -> JavaType.STRING;
                });
                column.setJavaField(StringUtil.toLowerCamelCase(column.getColumnName()));
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
                    default -> HtmlType.INPUT;
                });
                boolean need = !NEEDLESS.contains(columnName);
                column.setInsertable(need && !column.getPrimaryKey());
                column.setEditable(need);
                column.setListable(need);
                column.setQueryable(need && !column.getPrimaryKey() && !"remark".equals(columnName));
                columnService.save(column);
            }
        }
        return true;
    }

    private int getLength(String columnType) {
        return Integer.parseInt(StringUtils.substringBetween(columnType, "(", ")"));
    }

    public boolean generateCode(Long id) {
        return true;
    }
}
