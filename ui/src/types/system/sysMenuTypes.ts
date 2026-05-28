export interface SysMenu extends BaseEntity {
  // 主键
  id: number
  // 菜单名称
  menuName: string
  // 父菜单id
  parentId: number
  // 显示顺序
  sort: number
  // 路由地址
  path: string
  // 组件路径
  component: string
  // 路由参数
  queryParam: string
  // 是否为外链
  foreignLink: boolean
  // 是否缓存
  cached: boolean
  // 菜单类型
  menuType: string
  // 显示
  visible: boolean
  // 启用
  enabled: boolean
  // 权限标识
  perms: string
  // 菜单图标
  icon: string
  // 高亮菜单
  activeMenu: string
  // 子列表
  children: SysMenu[]
}

export interface SysMenuQuery {
  // 菜单名称
  menuName?: string
  // 启用
  enabled?: boolean
}

export interface SysMenuPageQuery extends SysMenuQuery, PageQuery {
}

export interface SysMenuForm {
  // 主键
  id?: number
  // 菜单名称
  menuName?: string
  // 父菜单id
  parentId?: number
  // 显示顺序
  sort?: number
  // 路由地址
  path?: string
  // 组件路径
  component?: string
  // 路由参数
  queryParam?: string
  // 是否为外链
  foreignLink?: boolean
  // 是否缓存
  cached?: boolean
  // 菜单类型
  menuType?: string
  // 显示
  visible?: boolean
  // 启用
  enabled?: boolean
  // 权限标识
  perms?: string
  // 菜单图标
  icon?: string
  // 高亮菜单
  activeMenu?: string
}

export const sysMenuInitData: SysMenuForm = {
  // 主键
  id: undefined,
  // 菜单名称
  menuName: undefined,
  // 父菜单id
  parentId: undefined,
  // 显示顺序
  sort: undefined,
  // 路由地址
  path: undefined,
  // 组件路径
  component: undefined,
  // 路由参数
  queryParam: undefined,
  // 是否为外链
  foreignLink: undefined,
  // 是否缓存
  cached: undefined,
  // 菜单类型
  menuType: undefined,
  // 显示
  visible: undefined,
  // 启用
  enabled: undefined,
  // 权限标识
  perms: undefined,
  // 菜单图标
  icon: undefined,
  // 高亮菜单
  activeMenu: undefined,
}
