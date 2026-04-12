package cn.sijay.owl.system.entity;

import cn.sijay.owl.common.entity.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

import java.time.LocalDateTime;
/**
 * 菜单实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_menu", comment = "菜单表")
public class SysMenu extends BaseEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 菜单名称
     */
    @Column(value = "menu_name", comment = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    @ExcelProperty(value = "菜单名称")
    private String menuName;

    /**
     * 父菜单id
     */
    @Column(value = "parent_id", comment = "父菜单id")
    @ExcelProperty(value = "父菜单id")
    private Long parentId;

    /**
     * 显示顺序
     */
    @Column(value = "sort", comment = "显示顺序")
    @ExcelProperty(value = "显示顺序")
    private Integer sort;

    /**
     * 路由地址
     */
    @Column(value = "path", comment = "路由地址")
    @ExcelProperty(value = "路由地址")
    private String path;

    /**
     * 组件路径
     */
    @Column(value = "component", comment = "组件路径")
    @ExcelProperty(value = "组件路径")
    private String component;

    /**
     * 路由参数
     */
    @Column(value = "query_param", comment = "路由参数")
    @ExcelProperty(value = "路由参数")
    private String queryParam;

    /**
     * 是否为外链
     */
    @Column(value = "foreign_link", comment = "是否为外链")
    @ExcelProperty(value = "是否为外链")
    private Boolean foreignLink;

    /**
     * 是否缓存
     */
    @Column(value = "cached", comment = "是否缓存")
    @ExcelProperty(value = "是否缓存")
    private Boolean cached;

    /**
     * 菜单类型
     */
    @Column(value = "menu_type", comment = "菜单类型")
    @ExcelProperty(value = "菜单类型")
    private String menuType;

    /**
     * 显示
     */
    @Column(value = "visible", comment = "显示")
    @ExcelProperty(value = "显示")
    private Boolean visible;

    /**
     * 启用
     */
    @Column(value = "enabled", comment = "启用")
    @ExcelProperty(value = "启用")
    private Boolean enabled;

    /**
     * 权限标识
     */
    @Column(value = "perms", comment = "权限标识")
    @ExcelProperty(value = "权限标识")
    private String perms;

    /**
     * 菜单图标
     */
    @Column(value = "icon", comment = "菜单图标")
    @ExcelProperty(value = "菜单图标")
    private String icon;

}
