import type { RouteRecordRaw } from 'vue-router'
import { routes as allRoutes } from 'vue-router/auto-routes'
import { commonApi } from '@/api/auth/commonApi'
import InnerLink from '@/layout/components/InnerLink/index.vue'
import Layout from '@/layout/index.vue'
import { staticRouters } from '@/router'

export const usePermissionStore = defineStore('permission', () => {
  const routes = ref<RouteRecordRaw[]>([])

  // 递归实现（深度优先）
  function flattenTree(tree: RouteRecordRaw[]): RouteRecordRaw[] {
    const result: RouteRecordRaw[] = []

    function traverse(node: RouteRecordRaw) {
      // 提取当前节点，排除 children 字段
      const { children, ...rest } = node
      result.push(rest as RouteRecordRaw)
      // 递归处理子节点
      if (children && Array.isArray(children)) {
        children.forEach(child => traverse(child))
      }
    }

    // 遍历根节点数组
    tree.forEach(root => traverse(root))
    return result
  }

  const routeMap = Object.fromEntries(flattenTree(allRoutes).filter((route: RouteRecordRaw): boolean => !!route.path && !!route.component).map(item => [item.path, item.component]))

  /**
   * 遍历后台传来的路由字符串，转换为组件对象
   * @param asyncRouterMap 后台传来的路由字符串
   */
  const filterAsyncRouter = (asyncRouterMap: RouteRecordRaw[]): RouteRecordRaw[] => {
    return asyncRouterMap.filter((route) => {
      // Layout   组件特殊处理
      if (route.component?.toString() === 'Layout') {
        route.component = Layout
      } else if (route.component?.toString() === 'InnerLink') {
        route.component = InnerLink
      } else {
        route.component = routeMap[route.path]
      }
      if (route.children && route.children.length) {
        route.children = filterAsyncRouter(route.children)
      } else {
        delete route.children
        delete route.redirect
      }
      return true
    })
  }

  const generateRoutes = async (): Promise<RouteRecordRaw[]> => {
    const data = await commonApi.getRoutes()
    const asyncRouters = filterAsyncRouter(data)
    routes.value = staticRouters.concat(asyncRouters)
    return new Promise<RouteRecordRaw[]>(resolve => resolve(asyncRouters))
  }

  return {
    routes,
    generateRoutes,
  }
})
