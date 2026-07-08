export interface GenTable {
  /**
   * 主键
   */
  id: number
  /**
   * 物理表名
   */
  tableName: string
  /**
   * 表注释
   */
  tableComment: string
  /**
   * 模块名
   */
  moduleName: string
  /**
   * 实体类名
   */
  className: string
  /**
   * 实体类注释
   */
  classComment: string
  /**
   * 功能名
   */
  functionName: string
  /**
   * 是否树表
   */
  treeTable: boolean
  /**
   * 树编码字段
   */
  treeKey: string
  /**
   * 树父编码字段
   */
  treeParentKey: string
  /**
   * 树名称字段
   */
  treeLabel: string
  /**
   * 所属菜单
   */
  menuId: number
  /**
   * 仅生成实体类
   */
  entityOnly: boolean

  columns: GenColumn[]
}

export interface GenTableQuery {
  /**
   * 物理表名
   */
  tableName?: string
  /**
   * 表注释
   */
  tableComment?: string
}

export interface GenTablePageQuery extends PageQuery {
  /**
   * 物理表名
   */
  tableName?: string
  /**
   * 表注释
   */
  tableComment?: string
}
