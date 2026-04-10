declare global {
  /**
   * 统一响应结果类型
   */
  interface Result<T = unknown> {
    /** 响应码 */
    code: number
    /** 响应消息 */
    message: string
    /** 响应数据 */
    data: T
    /** 数据总量 */
    total: number
  }

  /**
   * 分页查询参数
   */
  interface PageQuery {
    page: number
    size: number
    orderBy?: string
    asc?: boolean
  }

  interface SelectNode {
    label: string
    value: string
  }

  interface TreeNode {
    label: string
    value: string
    children: Array<TreeNode>
  }

}
export {}
