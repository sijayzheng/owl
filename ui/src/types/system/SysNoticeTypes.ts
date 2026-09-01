export interface SysNotice extends BaseEntity {
  // 主键
  id: number
  // 公告标题
  noticeTitle: string
  // 公告类型
  noticeType: string
  // 是否关闭
  closed: boolean
}

export interface SysNoticeQuery {
  // 公告标题
  noticeTitle?: string
  // 公告类型
  noticeType?: string
  // 是否关闭
  closed?: boolean
}

export interface SysNoticePageQuery extends SysNoticeQuery, PageQuery {
}

export interface SysNoticeForm {
  // 主键
  id?: number
  // 公告标题
  noticeTitle?: string
  // 公告类型
  noticeType?: string
  // 公告内容
  noticeContent?: string
  // 是否关闭
  closed?: boolean
}

export const sysNoticeInitData: SysNoticeForm = {
  // 主键
  id: undefined,
  // 公告标题
  noticeTitle: undefined,
  // 公告类型
  noticeType: undefined,
  // 公告内容
  noticeContent: undefined,
  // 是否关闭
  closed: undefined,
}
