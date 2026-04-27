export interface SysUserMfaRecoveryCodes  {
  // 主键
  id: number
  // 用户id
  userId: number
  // 备用验证码
  code: string
  // 已用
  used: boolean
  // 使用时间
  usedTime: string
  // 创建时间
  createTime: string
}

export interface SysUserMfaRecoveryCodesQuery {
}

export interface SysUserMfaRecoveryCodesForm {
  // 主键
  id?: number
  // 用户id
  userId?: number
  // 备用验证码
  code?: string
  // 已用
  used?: boolean
  // 使用时间
  usedTime?: string
}
