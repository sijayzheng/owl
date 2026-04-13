package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
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
     * @param pageQuery 分页参数
     * @param sysConfig 查询条件
     * @return 参数配置分页数据
     */
    public Page<SysConfig> page(PageQuery pageQuery, SysConfig sysConfig) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysConfig)));
    }

    /**
     * 构建查询条件
     *
     * @param sysConfig 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysConfig sysConfig) {
        QueryWrapper query = query();
        query.and(SYS_CONFIG.CONFIG_NAME.like(sysConfig.getConfigName()));
        query.and(SYS_CONFIG.CONFIG_KEY.like(sysConfig.getConfigKey()));
        return query;
    }

    /**
     * 查询参数配置列表
     *
     * @param sysConfig 查询条件
     * @return 参数配置列表
     */
    public List<SysConfig> list(SysConfig sysConfig) {
        return list(query(sysConfig));
    }

    /**
     * 删除参数配置
     *
     * @param id 参数配置ID
     * @return 删除结果
     * @throws ServiceException 当参数配置不存在时抛出异常
     */
    public boolean delete(Long id) {
        SysConfig user = getById(id);
        if (user == null) {
            throw new ServiceException(SysConfigService.class, "主键为{}的参数配置不存在", id);
        }
        return removeById(id);
    }

}
