package cn.sijay.owl.system.entity;

import cn.sijay.owl.common.entity.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

/**
 * 用户实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_user", comment = "用户表")
public class SysUser extends BaseEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 部门id
     */
    @Column(value = "dept_id", comment = "部门id")
    @ExcelProperty(value = "部门id")
    private Long deptId;

    /**
     * 用户账号
     */
    @Column(value = "username", comment = "用户账号")
    @NotBlank(message = "用户账号不能为空")
    @ExcelProperty(value = "用户账号")
    private String username;

    /**
     * 用户昵称
     */
    @Column(value = "nickname", comment = "用户昵称")
    @ExcelProperty(value = "用户昵称")
    private String nickname;

    /**
     * 邮箱
     */
    @Column(value = "email", comment = "邮箱")
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @Column(value = "phone", comment = "手机号")
    @ExcelProperty(value = "手机号")
    private String phone;

    /**
     * 性别
     */
    @Column(value = "gender", comment = "性别")
    @ExcelProperty(value = "性别")
    private String gender;

    /**
     * 头像地址
     */
    @Column(value = "avatar", comment = "头像地址")
    @ExcelProperty(value = "头像地址")
    private Long avatar;

    /**
     * 密码
     */
    @Column(value = "password", comment = "密码")
    @NotBlank(message = "密码不能为空")
    @ExcelProperty(value = "密码")
    private String password;

    /**
     * 是否启用MFA
     */
    @Column(value = "mfa_enabled", comment = "是否启用MFA")
    @ExcelProperty(value = "是否启用MFA")
    private Boolean mfaEnabled;

    /**
     * totp密钥
     */
    @Column(value = "totp_secret", comment = "totp密钥")
    @ExcelProperty(value = "totp密钥")
    private String totpSecret;

    /**
     * 启用
     */
    @Column(value = "enabled", comment = "启用")
    @ExcelProperty(value = "启用")
    private Boolean enabled;

    /**
     * 删除
     */
    @Column(value = "deleted", comment = "删除")
    private Boolean deleted;

}
