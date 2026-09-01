package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.dto.SysMessageQuery;
import cn.sijay.owl.system.entity.SysMessage;
import cn.sijay.owl.system.mapper.SysMessageMapper;
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
     * @param pageQuery            分页参数
     * @param sysMessageQuery 查询条件
     * @return 系统消息分页数据
     */
    public Page<SysMessage> page(PageQuery pageQuery, SysMessageQuery sysMessageQuery) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysMessageQuery)));
    }

    /**
     * 构建查询条件
     *
     * @param sysMessageQuery 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysMessageQuery sysMessageQuery) {
        QueryWrapper query = query();
        query.and(SYS_MESSAGE.MESSAGE_TITLE.like(sysMessageQuery.messageTitle()));
        query.and(SYS_MESSAGE.MESSAGE_TYPE.like(sysMessageQuery.messageType()));
        query.and(SYS_MESSAGE.SENDER.eq(sysMessageQuery.sender()));
        query.and(SYS_MESSAGE.RECIPIENT.eq(sysMessageQuery.recipient()));
        query.and(SYS_MESSAGE.HAS_READ.eq(sysMessageQuery.hasRead()));
        return query;
    }

    /**
     * 查询系统消息列表
     *
     * @param sysMessageQuery 查询条件
     * @return 系统消息列表
     */
    public List<SysMessage> list(SysMessageQuery sysMessageQuery) {
        return list(query(sysMessageQuery));
    }

    /**
     * 校验并保存系统消息
     *
     * @param sysMessage 系统消息实体
     * @return 保存结果
     */
    public boolean validSave(SysMessage sysMessage) {
        return saveOrUpdate(sysMessage);
    }
}
