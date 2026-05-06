export interface SysDept extends BaseEntity {
  // 主键
  id: number
  // 子列表
  children: Array<SysDept>
  // 父部门id
  parentId: number
  // 子列表
  children: Array<SysDept>
  // 祖级列表
  ancestors: string
  // 子列表
  children: Array<SysDept>
  // 部门名称
  deptName: string
  // 子列表
  children: Array<SysDept>
  // 部门类别
  deptCategory: number
  // 子列表
  children: Array<SysDept>
  // 显示顺序
  sort: number
  // 子列表
  children: Array<SysDept>
  // 负责人
  leader: number
  // 子列表
  children: Array<SysDept>
  // 联系电话
  phone: string
  // 子列表
  children: Array<SysDept>
  // 邮箱
  email: string
  // 子列表
  children: Array<SysDept>
  // 启用
  enabled: boolean
  // 子列表
  children: Array<SysDept>
  // 删除
  deleted: boolean
  // 子列表
  children: Array<SysDept>
}

export interface SysDeptQuery {
  // 部门名称
  deptName?: string
  // 启用
  enabled?: boolean
}

export interface SysDeptForm {
  // 主键
  id?: number
  // 父部门id
  parentId?: number
  // 祖级列表
  ancestors?: string
  // 部门名称
  deptName?: string
  // 部门类别
  deptCategory?: number
  // 显示顺序
  sort?: number
  // 负责人
  leader?: number
  // 联系电话
  phone?: string
  // 邮箱
  email?: string
  // 启用
  enabled?: boolean
}
