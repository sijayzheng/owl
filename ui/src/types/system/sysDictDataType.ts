export interface SysDictData extends BaseEntity {
  // 主键
  id: number
  // 字典类型id
  dictTypeId: number
  // 字典编码
  dictCode: string
  // 字典标签
  dictLabel: string
  // 字典键值
  dictValue: string
  // 字典排序
  sort: number
  // 样式属性
  cssClass: string
  // 表格回显样式
  listClass: string
  // 是否默认
  defaulted: boolean
  // 启用
  enabled: boolean
}

export interface SysDictDataQuery {
  // 字典编码
  dictCode?: string
  // 字典标签
  dictLabel?: string
}

export interface SysDictDataForm {
  // 主键
  id?: number
  // 字典类型id
  dictTypeId?: number
  // 字典编码
  dictCode?: string
  // 字典标签
  dictLabel?: string
  // 字典键值
  dictValue?: string
  // 字典排序
  sort?: number
  // 样式属性
  cssClass?: string
  // 表格回显样式
  listClass?: string
  // 是否默认
  defaulted?: boolean
  // 启用
  enabled?: boolean
}
