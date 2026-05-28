package cn.sijay.owl.system.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色菜单关联实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_role_menu", comment = "角色菜单关联表")
public class SysRoleMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @Id(comment = "角色id")
    @Column(value = "role_id", comment = "角色id")
    private Long roleId;

    /**
     * 菜单id
     */
    @Id(comment = "菜单id")
    @Column(value = "menu_id", comment = "菜单id")
    private Long menuId;

}
