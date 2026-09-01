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
}

export interface SysUserMfaRecoveryCodesQuery {
  // 用户id
  userId?: number
}

export interface SysUserMfaRecoveryCodesPageQuery extends SysUserMfaRecoveryCodesQuery, PageQuery {
}

export interface SysUserMfaRecoveryCodesForm {
}

export const sysUserMfaRecoveryCodesInitData: SysUserMfaRecoveryCodesForm = {
}
