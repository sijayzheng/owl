package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.entity.SysMessage;
import cn.sijay.owl.system.mapper.SysMessageMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.system.entity.table.SysMessageTableDef.SYS_MESSAGE;

/**
 * 系统消息服务类
 * 提供系统消息的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysMessageService extends ServiceImpl<SysMessageMapper, SysMessage> implements IService<SysMessage> {
    private final SysMessageMapper sysMessageMapper;

    /**
     * 分页查询系统消息
     *
     * @param pageQuery 分页参数
     * @param sysMessage 查询条件
     * @return 系统消息分页数据
     */
    public Page<SysMessage> page(PageQuery pageQuery, SysMessage sysMessage) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysMessage)));
    }

    /**
     * 构建查询条件
     *
     * @param sysMessage 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysMessage sysMessage) {
        QueryWrapper query = query();
        query.and(SYS_MESSAGE.MESSAGE_TITLE.like(sysMessage.getMessageTitle()));
        query.and(SYS_MESSAGE.HAS_READ.eq(sysMessage.getHasRead()));
        return query;
    }

    /**
     * 查询系统消息列表(不分页)
     *
     * @param sysMessage 查询条件
     * @return 系统消息列表
     */
    public List<SysMessage> list(SysMessage sysMessage) {
        return list(query(sysMessage));
    }

    /**
     * 删除系统消息
     *
     * @param id 系统消息ID
     * @return 删除结果
     * @throws ServiceException 当系统消息不存在时抛出异常
     */
    public boolean delete(Long id) {
        SysMessage user = getById(id);
        if (user == null) {
            throw new ServiceException(SysMessageService.class, "主键为{}的系统消息不存在", id);
        }
        return removeById(id);
    }
}
