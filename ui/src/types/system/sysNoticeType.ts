export interface SysNotice extends BaseEntity {
  // 主键
  id: number
  // 公告标题
  noticeTitle: string
  // 公告类型
  noticeType: string
  // 公告内容
  noticeContent: string
  // 是否关闭
  closed: boolean
  // 删除
  deleted: boolean
}

export interface SysNoticeQuery {
  // 公告标题
  noticeTitle?: string
  // 公告类型
  noticeType?: string
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
