export interface SysRole extends BaseEntity {
  // 主键
  id: number
  // 角色名称
  roleName: string
  // 角色权限字符串
  roleCode: string
  // 显示顺序
  sort: number
  // 数据权限
  dataScope: string
  // 菜单树选择项关联显示
  menuCheckStrictly: boolean
  // 部门树选择项关联显示
  deptCheckStrictly: boolean
  // 启用
  enabled: boolean
  // 删除
  deleted: boolean
}

export interface SysRoleQuery {
  // 角色名称
  roleName?: string
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
  // 数据权限
  dataScope?: string
  // 菜单树选择项关联显示
  menuCheckStrictly?: boolean
  // 部门树选择项关联显示
  deptCheckStrictly?: boolean
  // 启用
  enabled?: boolean
}
