export interface GenColumn {
  /**
   * 主键
   */
  id: number
  /**
   * 表id
   */
  tableId: number
  /**
   * 列名
   */
  columnName: string
  /**
   * 列注释
   */
  columnComment: string
  /**
   * 数据库类型
   */
  columnType: string
  /**
   * Java类型
   */
  javaType: string
  /**
   * Java字段名
   */
  javaField: string
  /**
   * 是否主键
   */
  primaryKey: boolean
  /**
   * 是否自增
   */
  incremental: boolean
  /**
   * 是否必填
   */
  required: boolean
  /**
   * 是否插入
   */
  insertable: boolean
  /**
   * 是否编辑
   */
  editable: boolean
  /**
   * 是否列表
   */
  listable: boolean
  /**
   * 是否查询
   */
  queryable: boolean
  /**
   * 查询方式
   */
  queryType: string
  /**
   * 显示类型
   */
  htmlType: string
  /**
   * 字典类型
   */
  dictType: string
  /**
   * 排序
   */
  sort: number
}
