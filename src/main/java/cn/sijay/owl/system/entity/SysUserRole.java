package cn.sijay.owl.system.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户角色关联实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_user_role", comment = "用户角色关联表")
public class SysUserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Id(comment = "用户id")
    @Column(value = "user_id", comment = "用户id")
    private Long userId;

    /**
     * 角色id
     */
    @Id(comment = "角色id")
    @Column(value = "role_id", comment = "角色id")
    private Long roleId;

}
