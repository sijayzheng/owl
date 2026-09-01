import type { RouteLocationNormalized } from 'vue-router'

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
    // 创建人
    createBy: number
    // 创建时间
    createTime: string
    // 更新人
    updateBy: number
    // 更新时间
    updateTime: string
  }

  type TagView = RouteLocationNormalized & {
    title?: string
  }

  interface Meta {
    // 设置该路由在侧边栏和面包屑中展示的名字
    title: string
    // 图标
    icon: string
    // 设置为true，则不会被 <keep-alive>缓存
    noCache: boolean
    // 内链地址（http(s)://开头）
    link: string
  }

  interface Route {
    // 路由名字
    name: string
    // 路由地址
    path: string
    // 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
    hidden: boolean
    // 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
    redirect: string
    // 组件地址
    component: string
    // 路由参数：如 {"id": 1, "name": "ry"}
    query: string
    // 其他元素
    meta: Meta
    // 子路由
    children: Array<Route>
  }

  type Pair = Record<string, any>

  type EnumType = 'JavaType' | 'QueryType' | 'HtmlType'
}
export {}
