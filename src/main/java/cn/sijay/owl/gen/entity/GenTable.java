package cn.sijay.owl.gen.entity;


import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * GenTable
 *
 * @author sijay
 * @since 2026/4/7
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(value = "gen_table", comment = "代码生成表信息")
public class GenTable implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    private Long id;

    /**
     * 物理表名
     */
    @Column(comment = "物理表名")
    private String tableName;

    /**
     * 表注释
     */
    @Column(comment = "表注释")
    private String tableComment;

    /**
     * 模块名
     */
    @Column(comment = "模块名")
    private String moduleName;

    /**
     * 实体类名
     */
    @Column(comment = "实体类名")
    private String className;

    /**
     * 实体类注释
     */
    @Column(comment = "实体类注释")
    private String classComment;

    /**
     * 功能名
     */
    @Column(comment = "功能名")
    private String functionName;

    /**
     * 是否树表
     */
    @Column(comment = "是否树表")
    private Boolean treeTable;

    /**
     * 树编码字段
     */
    @Column(comment = "树编码字段")
    private String treeKey;

    /**
     * 树父编码字段
     */
    @Column(comment = "树父编码字段")
    private String treeParentKey;

    /**
     * 树名称字段
     */
    @Column(comment = "树名称字段")
    private String treeLabel;

    /**
     * 所属菜单
     */
    @Column(comment = "所属菜单")
    private Long menuId;

    @Column(ignore = true)
    private List<GenColumn> columns;
}
