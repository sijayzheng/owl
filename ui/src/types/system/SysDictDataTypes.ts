export interface SysDictData extends BaseEntity {
  // 主键
  id: number
  // 字典类型id
  dictTypeId: number
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
  // 字典类型id
  dictTypeId?: number
  // 字典标签
  dictLabel?: string
}

export interface SysDictDataPageQuery extends SysDictDataQuery, PageQuery {
}

export interface SysDictDataForm {
  // 主键
  id?: number
  // 字典类型id
  dictTypeId?: number
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

export const sysDictDataInitData: SysDictDataForm = {
  // 主键
  id: undefined,
  // 字典类型id
  dictTypeId: undefined,
  // 字典标签
  dictLabel: undefined,
  // 字典键值
  dictValue: undefined,
  // 字典排序
  sort: undefined,
  // 样式属性
  cssClass: undefined,
  // 表格回显样式
  listClass: undefined,
  // 是否默认
  defaulted: undefined,
  // 启用
  enabled: undefined,
}
