package cn.sijay.owl.system.entity;


import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * MFA备用验证码实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_user_mfa_recovery_codes", comment = "MFA备用验证码表")
public class SysUserMfaRecoveryCodes implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 用户id
     */
    @Column(value = "user_id", comment = "用户id")
    @NotNull(message = "用户id不能为空")
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 备用验证码
     */
    @Column(value = "code", comment = "备用验证码")
    @NotBlank(message = "备用验证码不能为空")
    @ExcelProperty(value = "备用验证码")
    private String code;

    /**
     * 已用
     */
    @Column(value = "used", comment = "已用")
    @ExcelProperty(value = "已用")
    private Boolean used;

    /**
     * 使用时间
     */
    @Column(value = "used_time", comment = "使用时间")
    @ExcelProperty(value = "使用时间")
    private LocalDateTime usedTime;

}
