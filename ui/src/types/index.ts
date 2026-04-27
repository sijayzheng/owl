declare global {
  /**
   * 统一响应结果类型
   */
  interface Result<T = unknown> {
    // 响应码
    code: number
    // 响应消息
    message: string
    // 响应数据
    data: T
    // 数据总量
    total: number
  }

  /**
   * 分页查询参数
   */
  interface PageQuery {
    // 页码
    page: number
    // 页大小
    size: number
    // 排序列
    orderBy?: string
    // 排序方向
    asc?: boolean
  }

  interface SelectNode {
    // 标签
    label: string
    // 值
    value: string
  }

  interface TreeNode {
    // 标签
    label: string
    // 值
    value: string
    // 孩子
    children: Array<TreeNode>
  }

  interface BaseEntity {
    //创建部门
    createDept: number
    //创建人
    createBy: number
    //创建时间
    createTime: string
    //更新人
    updateBy: number
    //更新时间
    updateTime: string
  }
}
export {}
