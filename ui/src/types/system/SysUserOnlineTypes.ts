export interface SysUserOnline  {
  // 主键
  id: number
  // 用户id
  userId: number
  // 用户账号
  username: string
  // 部门名称
  deptName: string
  // 登录ip
  loginIp: string
  // 登录地点
  loginLocation: string
  // 浏览器
  browser: string
  // 操作系统
  os: string
  // 登录时间
  loginTime: string
  // 最后访问时间
  lastAccessTime: string
  // 过期时间
  expireTime: string
}

export interface SysUserOnlineQuery {
  // 用户账号
  username?: string
  // 登录ip
  loginIp?: string
  // 登录地点
  loginLocation?: string
  // 登录时间
  loginTimeRange?: string[]
}

export interface SysUserOnlinePageQuery extends SysUserOnlineQuery, PageQuery {
}

export interface SysUserOnlineForm {
}

export const sysUserOnlineInitData: SysUserOnlineForm = {
}
