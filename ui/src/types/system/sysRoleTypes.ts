export interface SysRole extends BaseEntity {
  // 主键
  id: number
  // 角色名称
  roleName: string
  // 角色权限字符串
  roleCode: string
  // 显示顺序
  sort: number
  // 菜单树选择项关联显示
  menuCheckStrictly: boolean
  // 启用
  enabled: boolean
}

export interface SysRoleQuery {
  // 角色名称
  roleName?: string
  // 启用
  enabled?: boolean
}

export interface SysRolePageQuery extends SysRoleQuery, PageQuery {
}

export interface SysRoleForm {
  // 主键
  id?: number
  // 角色名称
  roleName?: string
  // 角色权限字符串
  roleCode?: string
  // 显示顺序
  sort?: number
  // 菜单树选择项关联显示
  menuCheckStrictly?: boolean
  // 启用
  enabled?: boolean
}

export const sysRoleInitData: SysRoleForm = {
  // 主键
  id: undefined,
  // 角色名称
  roleName: undefined,
  // 角色权限字符串
  roleCode: undefined,
  // 显示顺序
  sort: undefined,
  // 菜单树选择项关联显示
  menuCheckStrictly: undefined,
  // 启用
  enabled: undefined,
}
