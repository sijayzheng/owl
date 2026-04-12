package cn.sijay.owl.system.mapper;

import cn.sijay.owl.system.entity.SysUserMfaRecoveryCodes;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * MFA备用验证码Mapper
 *
 * @author sijay
 * @since 2026-04-09
 */
@Mapper
public interface SysUserMfaRecoveryCodesMapper extends BaseMapper<SysUserMfaRecoveryCodes> {
}