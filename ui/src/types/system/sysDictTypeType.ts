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

export interface SysDictTypeForm {
  // 主键
  id?: number
  // 字典名称
  typeName?: string
  // 字典编码
  typeCode?: string
}
