package cn.sijay.owl.gen.entity;

import cn.sijay.owl.gen.constants.GenConstants;
import cn.sijay.owl.gen.enums.HtmlType;
import cn.sijay.owl.gen.enums.JavaType;
import cn.sijay.owl.gen.enums.QueryType;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * GenColumn
 *
 * @author sijay
 * @since 2026-04-07
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(value = "gen_column", comment = "代码生成列信息")
public class GenColumn implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    private Long id;

    /**
     * 表id
     */
    @Column(comment = "表id")
    private Long tableId;

    /**
     * 列名
     */
    @Column(comment = "列名")
    private String columnName;

    /**
     * 列注释
     */
    @Column(comment = "列注释")
    private String columnComment;

    /**
     * 数据库类型
     */
    @Column(comment = "数据库类型")
    private String columnType;

    /**
     * Java类型
     */
    @Column(comment = "Java类型")
    private JavaType javaType;

    /**
     * Java字段名
     */
    @Column(comment = "Java字段名")
    private String javaField;

    /**
     * 是否主键
     */
    @Column(comment = "是否主键")
    private Boolean primaryKey;

    /**
     * 是否自增
     */
    @Column(comment = "是否自增")
    private Boolean incremental;

    /**
     * 是否必填
     */
    @Column(comment = "是否必填")
    private Boolean required;

    /**
     * 是否插入
     */
    @Column(comment = "是否插入")
    private Boolean insertable;

    /**
     * 是否编辑
     */
    @Column(comment = "是否编辑")
    private Boolean editable;

    /**
     * 是否列表
     */
    @Column(comment = "是否列表")
    private Boolean listable;

    /**
     * 是否查询
     */
    @Column(comment = "是否查询")
    private Boolean queryable;

    /**
     * 查询方式
     */
    @Column(comment = "查询方式")
    private QueryType queryType;

    /**
     * 显示类型
     */
    @Column(comment = "显示类型")
    private HtmlType htmlType;

    /**
     * 字典类型
     */
    @Column(comment = "字典类型")
    private String dictType;

    /**
     * 排序
     */
    @Column(comment = "排序")
    private Integer sort;

    public boolean based(boolean hasBase) {
        return hasBase && GenConstants.BASE_FIELD.contains(columnName.toLowerCase());
    }

    public String shotComment() {
        return StringUtils.substringBefore(StringUtils.substringBefore(columnComment, "（"), "(");
    }

    public boolean export() {
        return !GenConstants.NEEDLESS.contains(columnName.toLowerCase());
    }
}
