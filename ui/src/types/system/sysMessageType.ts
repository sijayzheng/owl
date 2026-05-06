export interface SysMessage extends BaseEntity {
  // 主键
  id: number
  // 消息标题
  messageTitle: string
  // 消息内容
  messageContent: string
  // 消息类型
  messageType: string
  // 发送者
  sender: number
  // 接收者
  recipient: number
  // 已读
  hasRead: boolean
  // 删除
  deleted: boolean
}

export interface SysMessageQuery {
  // 消息标题
  messageTitle?: string
  // 已读
  hasRead?: boolean
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
