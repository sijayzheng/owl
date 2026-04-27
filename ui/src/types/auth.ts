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

export interface Meta {
  // 设置该路由在侧边栏和面包屑中展示的名字
  title: string
  // 图标
  icon: string
  // 设置为true，则不会被 <keep-alive>缓存
  noCache: boolean
  // 内链地址（http(s)://开头）
  link: string
}

export interface Route {
  // 路由名字
  name: string
  // 路由地址
  path: string
  // 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
  hidden: boolean
  // 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
  redirect: string
  // 组件地址
  component: string
  // 路由参数：如 {"id": 1, "name": "ry"}
  query: string
  // 其他元素
  meta: Meta
  // 子路由
  children: Array<Route>
}

export interface Captcha {
  uuid: string
  img: string
}
