export interface SysUser extends BaseEntity {
  // 主键
  id: number
  // 用户账号
  username: string
  // 用户姓名
  realName: string
  // 邮箱
  email: string
  // 手机号
  phone: string
  // 性别
  gender: string
  // 头像
  avatar: string
  // 是否启用MFA
  mfaEnabled: boolean
  // 启用
  enabled: boolean
}

export interface SysUserQuery {
  // 用户账号
  username?: string
  // 用户姓名
  realName?: string
  // 邮箱
  email?: string
  // 手机号
  phone?: string
  // 启用
  enabled?: boolean
}

export interface SysUserPageQuery extends SysUserQuery, PageQuery {
}

export interface SysUserForm {
  // 主键
  id?: number
  // 用户账号
  username?: string
  // 用户姓名
  realName?: string
  // 邮箱
  email?: string
  // 手机号
  phone?: string
  // 性别
  gender?: string
  // 头像
  avatar?: string
  // 是否启用MFA
  mfaEnabled?: boolean
  // 启用
  enabled?: boolean
}

export const sysUserInitData: SysUserForm = {
  // 主键
  id: undefined,
  // 用户账号
  username: undefined,
  // 用户姓名
  realName: undefined,
  // 邮箱
  email: undefined,
  // 手机号
  phone: undefined,
  // 性别
  gender: undefined,
  // 头像
  avatar: undefined,
  // 是否启用MFA
  mfaEnabled: undefined,
  // 启用
  enabled: undefined,
}
