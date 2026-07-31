export interface SysDept extends BaseEntity {
  // 主键
  id: number
  // 父部门id
  parentId: number
  // 部门名称
  deptName: string
  // 显示顺序
  sort: number
  // 负责人
  leader: number
  // 联系电话
  phone: string
  // 邮箱
  email: string
  // 启用
  enabled: boolean
  // 子列表
  children: SysDept[]
}

export interface SysDeptQuery {
  // 父部门id
  parentId?: number
  // 部门名称
  deptName?: string
  // 启用
  enabled?: boolean
}

export interface SysDeptPageQuery extends SysDeptQuery, PageQuery {
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

export const sysDeptInitData: SysDeptForm = {
  // 主键
  id: undefined,
  // 父部门id
  parentId: undefined,
  // 祖级列表
  ancestors: undefined,
  // 部门名称
  deptName: undefined,
  // 显示顺序
  sort: undefined,
  // 负责人
  leader: undefined,
  // 联系电话
  phone: undefined,
  // 邮箱
  email: undefined,
  // 启用
  enabled: undefined,
}
