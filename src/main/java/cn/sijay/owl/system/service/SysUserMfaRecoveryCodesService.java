package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.system.entity.SysUserMfaRecoveryCodes;
import cn.sijay.owl.system.mapper.SysUserMfaRecoveryCodesMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MFA备用验证码服务类
 * 提供MFA备用验证码的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysUserMfaRecoveryCodesService extends ServiceImpl<SysUserMfaRecoveryCodesMapper, SysUserMfaRecoveryCodes> implements IService<SysUserMfaRecoveryCodes> {
    private final SysUserMfaRecoveryCodesMapper sysUserMfaRecoveryCodesMapper;

    /**
     * 分页查询MFA备用验证码
     *
     * @param pageQuery               分页参数
     * @param sysUserMfaRecoveryCodes 查询条件
     * @return MFA备用验证码分页数据
     */
    public Page<SysUserMfaRecoveryCodes> page(PageQuery pageQuery, SysUserMfaRecoveryCodes sysUserMfaRecoveryCodes) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysUserMfaRecoveryCodes)));
    }

    /**
     * 构建查询条件
     *
     * @param sysUserMfaRecoveryCodes 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysUserMfaRecoveryCodes sysUserMfaRecoveryCodes) {
        QueryWrapper query = query();
        return query;
    }

    /**
     * 查询MFA备用验证码列表
     *
     * @param sysUserMfaRecoveryCodes 查询条件
     * @return MFA备用验证码列表
     */
    public List<SysUserMfaRecoveryCodes> list(SysUserMfaRecoveryCodes sysUserMfaRecoveryCodes) {
        return list(query(sysUserMfaRecoveryCodes));
    }

}
