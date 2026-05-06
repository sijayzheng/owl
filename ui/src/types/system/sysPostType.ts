export interface SysPost extends BaseEntity {
  // 主键
  id: number
  // 部门id
  deptId: number
  // 岗位编码
  postCode: string
  // 岗位类别编码
  postCategory: string
  // 岗位名称
  postName: string
  // 显示顺序
  sort: number
  // 启用
  enabled: boolean
  // 删除
  deleted: boolean
}

export interface SysPostQuery {
  // 部门id
  deptId?: number
  // 岗位名称
  postName?: string
}

export interface SysPostForm {
  // 主键
  id?: number
  // 部门id
  deptId?: number
  // 岗位编码
  postCode?: string
  // 岗位类别编码
  postCategory?: string
  // 岗位名称
  postName?: string
  // 显示顺序
  sort?: number
  // 启用
  enabled?: boolean
}
