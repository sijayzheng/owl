package cn.sijay.owl.system.dto;

import java.time.LocalDateTime;
/**
 * 通知公告实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record SysNoticeQuery (
    String noticeTitle,
    String noticeType,
    boolean closed
){
}
