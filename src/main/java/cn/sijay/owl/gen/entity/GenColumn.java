package cn.sijay.owl.gen.entity;


import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GenColumn
 *
 * @author sijay
 * @since 2026/4/7
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(value = "gen_column", comment = "代码生成列信息")
public class GenColumn {
    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    private Long id;
    /**
     * 表id
     */
    @Column(comment = "表id")
    private Long table_id;
    /**
     * 列名
     */
    @Column(comment = "列名")
    private String column_name;
    /**
     * 列注释
     */
    @Column(comment = "列注释")
    private String column_comment;
    /**
     * 数据库类型
     */
    @Column(comment = "数据库类型")
    private String column_type;
    /**
     * Java类型
     */
    @Column(comment = "Java类型")
    private String java_type;
    /**
     * Java字段名
     */
    @Column(comment = "Java字段名")
    private String java_field;
    /**
     * 是否主键
     */
    @Column(comment = "是否主键")
    private Boolean primary_key;
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
    private String query_type;
    /**
     * 显示类型
     */
    @Column(comment = "显示类型")
    private String html_type;
    /**
     * 字典类型
     */
    @Column(comment = "字典类型")
    private String dict_type;
    /**
     * 排序
     */
    @Column(comment = "排序")
    private Integer sort;
}
