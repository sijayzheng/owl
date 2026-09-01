export interface SysMessage extends BaseEntity {
  // 主键
  id: number
  // 消息标题
  messageTitle: string
  // 消息类型
  messageType: string
  // 发送者
  sender: number
  // 接收者
  recipient: number
  // 已读
  hasRead: boolean
}

export interface SysMessageQuery {
  // 消息标题
  messageTitle?: string
  // 消息类型
  messageType?: string
  // 发送者
  sender?: number
  // 接收者
  recipient?: number
  // 已读
  hasRead?: boolean
}

export interface SysMessagePageQuery extends SysMessageQuery, PageQuery {
}

export interface SysMessageForm {
  // 主键
  id?: number
  // 消息标题
  messageTitle?: string
  // 消息内容
  messageContent?: string
  // 消息类型
  messageType?: string
  // 发送者
  sender?: number
  // 接收者
  recipient?: number
  // 已读
  hasRead?: boolean
}

export const sysMessageInitData: SysMessageForm = {
  // 主键
  id: undefined,
  // 消息标题
  messageTitle: undefined,
  // 消息内容
  messageContent: undefined,
  // 消息类型
  messageType: undefined,
  // 发送者
  sender: undefined,
  // 接收者
  recipient: undefined,
  // 已读
  hasRead: undefined,
}
