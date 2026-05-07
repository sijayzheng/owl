export interface LogLogin  {
  // 主键
  id: number
  // 用户id
  userId: number
  // 用户账号
  username: string
  // 登录ip地址
  loginIp: string
  // 登录地址
  location: string
  // 浏览器类型
  browser: string
  // 操作系统
  os: string
  // 登录状态
  succeeded: boolean
  // 提示消息
  message: string
  // 登录时间
  loginTime: string
}

export interface LogLoginQuery {
  // 用户id
  userId?: number
  // 登录地址
  location?: string
  // 登录时间
  loginTimeRange?: string[]
}

export interface LogLoginForm {
  // 主键
  id?: number
}
