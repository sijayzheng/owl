package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.dto.SysUserMfaRecoveryCodesQuery;
import cn.sijay.owl.system.entity.SysUserMfaRecoveryCodes;
import cn.sijay.owl.system.mapper.SysUserMfaRecoveryCodesMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static cn.sijay.owl.system.entity.table.SysUserMfaRecoveryCodesTableDef.SYS_USER_MFA_RECOVERY_CODES;

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
     * @param pageQuery            分页参数
     * @param sysUserMfaRecoveryCodesQuery 查询条件
     * @return MFA备用验证码分页数据
     */
    public Page<SysUserMfaRecoveryCodes> page(PageQuery pageQuery, SysUserMfaRecoveryCodesQuery sysUserMfaRecoveryCodesQuery) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysUserMfaRecoveryCodesQuery)));
    }

    /**
     * 构建查询条件
     *
     * @param sysUserMfaRecoveryCodesQuery 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysUserMfaRecoveryCodesQuery sysUserMfaRecoveryCodesQuery) {
        QueryWrapper query = query();
        query.and(SYS_USER_MFA_RECOVERY_CODES.USER_ID.eq(sysUserMfaRecoveryCodesQuery.userId()));
        return query;
    }

    /**
     * 查询MFA备用验证码列表
     *
     * @param sysUserMfaRecoveryCodesQuery 查询条件
     * @return MFA备用验证码列表
     */
    public List<SysUserMfaRecoveryCodes> list(SysUserMfaRecoveryCodesQuery sysUserMfaRecoveryCodesQuery) {
        return list(query(sysUserMfaRecoveryCodesQuery));
    }

    /**
     * 校验并保存MFA备用验证码
     *
     * @param sysUserMfaRecoveryCodes MFA备用验证码实体
     * @return 保存结果
     */
    public boolean validSave(SysUserMfaRecoveryCodes sysUserMfaRecoveryCodes) {
        return saveOrUpdate(sysUserMfaRecoveryCodes);
    }
}
