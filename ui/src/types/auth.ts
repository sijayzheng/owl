export interface LoginReq {
  username: string
  password: string
  captcha: string
  uuid: string
}

export interface LoginResp {
  token: string
}

export interface PasswordChangeForm {
  // 旧密码
  oldPassword: string
  // 新密码
  newPassword: string
}

export interface UserInfo {
  userId: number
  // 用户
  user: SysUser
  // 角色
  roles: Array<string>
  // 权限
  permissions: Array<string>
}

export interface Captcha {
  uuid: string
  img: string
}
