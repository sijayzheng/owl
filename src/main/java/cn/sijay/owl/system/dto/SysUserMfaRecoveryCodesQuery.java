package cn.sijay.owl.system.dto;

import java.time.LocalDateTime;
/**
 * MFA备用验证码实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record SysUserMfaRecoveryCodesQuery (
    Long userId
){
}
