package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.system.dto.SysConfigQuery;
import cn.sijay.owl.system.entity.SysConfig;
import cn.sijay.owl.system.mapper.SysConfigMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.system.entity.table.SysConfigTableDef.SYS_CONFIG;

/**
 * 参数配置服务类
 * 提供参数配置的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysConfigService extends ServiceImpl<SysConfigMapper, SysConfig> implements IService<SysConfig> {
    private final SysConfigMapper sysConfigMapper;

    /**
     * 分页查询参数配置
     *
     * @param pageQuery      分页参数
     * @param sysConfigQuery 查询条件
     * @return 参数配置分页数据
     */
    public Page<SysConfig> page(PageQuery pageQuery, SysConfigQuery sysConfigQuery) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysConfigQuery)));
    }

    /**
     * 构建查询条件
     *
     * @param sysConfigQuery 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysConfigQuery sysConfigQuery) {
        QueryWrapper query = query();
        query.and(SYS_CONFIG.CONFIG_NAME.like(sysConfigQuery.configName()));
        query.and(SYS_CONFIG.CONFIG_KEY.like(sysConfigQuery.configKey()));
        return query;
    }

    /**
     * 查询参数配置列表
     *
     * @param sysConfigQuery 查询条件
     * @return 参数配置列表
     */
    public List<SysConfig> list(SysConfigQuery sysConfigQuery) {
        return list(query(sysConfigQuery));
    }

    /**
     * 校验并保存参数配置
     *
     * @param sysConfig 参数配置实体
     * @return 保存结果
     */
    public boolean validSave(SysConfig sysConfig) {
        return saveOrUpdate(sysConfig);
    }
}
