package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.system.entity.SysNotice;
import cn.sijay.owl.system.mapper.SysNoticeMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.system.entity.table.SysNoticeTableDef.SYS_NOTICE;

/**
 * 通知公告服务类
 * 提供通知公告的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysNoticeService extends ServiceImpl<SysNoticeMapper, SysNotice> implements IService<SysNotice> {
    private final SysNoticeMapper sysNoticeMapper;

    /**
     * 分页查询通知公告
     *
     * @param pageQuery 分页参数
     * @param sysNotice 查询条件
     * @return 通知公告分页数据
     */
    public Page<SysNotice> page(PageQuery pageQuery, SysNotice sysNotice) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysNotice)));
    }

    /**
     * 构建查询条件
     *
     * @param sysNotice 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysNotice sysNotice) {
        QueryWrapper query = query();
        query.and(SYS_NOTICE.NOTICE_TITLE.like(sysNotice.getNoticeTitle()));
        query.and(SYS_NOTICE.NOTICE_TYPE.like(sysNotice.getNoticeType()));
        return query;
    }

    /**
     * 查询通知公告列表
     *
     * @param sysNotice 查询条件
     * @return 通知公告列表
     */
    public List<SysNotice> list(SysNotice sysNotice) {
        return list(query(sysNotice));
    }

}
