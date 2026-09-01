export interface SysDictType extends BaseEntity {
  // 主键
  id: number
  // 字典名称
  typeName: string
  // 字典编码
  typeCode: string
}

export interface SysDictTypeQuery {
  // 字典名称
  typeName?: string
  // 字典编码
  typeCode?: string
}

export interface SysDictTypePageQuery extends SysDictTypeQuery, PageQuery {
}

export interface SysDictTypeForm {
  // 主键
  id?: number
  // 字典名称
  typeName?: string
  // 字典编码
  typeCode?: string
}

export const sysDictTypeInitData: SysDictTypeForm = {
  // 主键
  id: undefined,
  // 字典名称
  typeName: undefined,
  // 字典编码
  typeCode: undefined,
}
