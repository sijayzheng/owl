export interface SysUser extends BaseEntity {
  // 主键
  id: number
  // 部门id
  deptId: number
  // 用户账号
  username: string
  // 用户姓名
  realname: string
  // 邮箱
  email: string
  // 手机号
  phone: string
  // 性别
  gender: string
  // 头像
  avatar: string
  // 密码
  password: string
  // 是否启用MFA
  mfaEnabled: boolean
  // totp密钥
  totpSecret: string
  // 启用
  enabled: boolean
  // 删除
  deleted: boolean
}

export interface SysUserQuery {
  // 部门id
  deptId?: number
  // 用户账号
  username?: string
  // 用户姓名
  realname?: string
  // 启用
  enabled?: boolean
}

export interface SysUserForm {
  // 主键
  id?: number
  // 部门id
  deptId?: number
  // 用户账号
  username?: string
  // 用户姓名
  realname?: string
  // 邮箱
  email?: string
  // 手机号
  phone?: string
  // 性别
  gender?: string
  // 头像
  avatar?: string
  // 密码
  password?: string
  // 是否启用MFA
  mfaEnabled?: boolean
  // totp密钥
  totpSecret?: string
  // 启用
  enabled?: boolean
}
