import type { RouteComponent, RouteRecordRaw } from 'vue-router'
import { routes as allRoutes } from 'vue-router/auto-routes'
import InnerLink from '@/layout/components/InnerLink.vue'
import ParentView from '@/layout/components/ParentView.vue'
import Layout from '@/layout/Layout.vue'

// 扁平化路由树
function flattenTree(tree: RouteRecordRaw[]): RouteRecordRaw[] {
  const result: RouteRecordRaw[] = []

  function traverse(node: RouteRecordRaw) {
    const { children, ...rest } = node
    result.push(rest as RouteRecordRaw)
    if (children && Array.isArray(children)) {
      children.forEach(child => traverse(child))
    }
  }

  tree.forEach(root => traverse(root))
  return result
}

const allFlatRoutes = flattenTree(allRoutes)

// 构建 name -> component 映射
const routeMap: { [key: string]: RouteComponent } = Object.fromEntries(
  allFlatRoutes
    .filter((route: RouteRecordRaw): boolean => route.name !== undefined && !!route.component)
    .map((item: RouteRecordRaw) => [item.name as string, item.component as RouteComponent]),
)

// 构建 path -> component 映射
const pathToComponentMap: { [key: string]: RouteComponent } = {}
allFlatRoutes.forEach((route) => {
  if (route.component && route.path) {
    const cleanPath = route.path.replace(regexConstants.slashStart, '')
    if (cleanPath) {
      pathToComponentMap[cleanPath] = route.component
    }
    const fileName = cleanPath.split('/').pop()
    if (fileName !== undefined) {
      pathToComponentMap[fileName] = route.component
      if (fileName.endsWith('.vue')) {
        pathToComponentMap[fileName.slice(0, -4)] = route.component
      }
    }
    pathToComponentMap[route.path] = route.component
  }
})

export const usePermissionStore = defineStore('permission', () => {
  const routes = ref<RouteRecordRaw[]>([])
  const sidebarRouters = ref<RouteRecordRaw[]>([])

  const getRoutes = (): RouteRecordRaw[] => {
    return routes.value
  }
  const getSidebarRoutes = (): RouteRecordRaw[] => {
    return sidebarRouters.value
  }

  const generateRoutes = async (): Promise<RouteRecordRaw[]> => {
    const data = await commonApi.getRoutes()

    const sidebarRoutes = filterAsyncRouter(routeToRouteRecord(structuredClone(data)))
    const rewriteRoutes = filterAsyncRouter(routeToRouteRecord(structuredClone(data)), true)

    routes.value = staticRouters.concat(rewriteRoutes)
    sidebarRouters.value = staticRouters.concat(sidebarRoutes)
    return rewriteRoutes
  }

  function routeToRouteRecord(routes?: Route[]): RouteRecordRaw[] {
    if (!routes) {
      return []
    }
    return routes
      .map((route) => {
        const children = routeToRouteRecord(route.children)
        const component = getComponent(route.component)
        return {
          children,
          component,
          path: route.path,
          redirect: route.redirect,
          meta: { ...route.meta },
          query: route.query,
          name: route.name,
        }
      })
      .filter((route) => {
        return (route.children !== undefined && route.children.length > 0) || route.component !== undefined
      })
  }

  function getComponent(component: string | undefined): RouteComponent | undefined {
    if (component === 'Layout') {
      return Layout
    } else if (component === 'ParentView') {
      return ParentView
    } else if (component === 'InnerLink') {
      return InnerLink
    } else if (component != null) {
      if (component.endsWith('/index')) {
        component = component.substring(0, component.length - 5)
      }
      let rc = routeMap[component]
      if (rc !== undefined) {
        return rc
      }
      rc = pathToComponentMap[component]
      if (rc !== undefined) {
        return rc
      }
      const componentWithoutExt = component.replace(regexConstants.vueSuffix, '')
      rc = pathToComponentMap[componentWithoutExt]
      if (rc !== undefined) {
        return rc
      }
    }
    return undefined
  }

  function filterAsyncRouter(asyncRouterMap: RouteRecordRaw[], type = false): RouteRecordRaw[] {
    const validRoutes: RouteRecordRaw[] = []

    asyncRouterMap.forEach((route) => {
      // 创建新对象避免修改原对象
      const newRoute = { ...route }

      if (newRoute.children && type) {
        newRoute.children = filterChildren(newRoute.children, undefined)
      }

      if (newRoute.children && newRoute.children.length) {
        newRoute.children = filterAsyncRouter(newRoute.children, type)
        validRoutes.push(newRoute)
      } else if (newRoute.component) {
        // 确保只删除真正存在的属性
        const cleanRoute = { ...newRoute }
        delete cleanRoute.children
        delete cleanRoute.redirect
        validRoutes.push(cleanRoute)
      }
    })

    return validRoutes
  }

  function filterChildren(childrenMap: RouteRecordRaw[], lastRouter?: RouteRecordRaw): RouteRecordRaw[] {
    let children: RouteRecordRaw[] = []

    childrenMap.forEach((el) => {
      // 创建新对象避免修改原对象
      const newEl = { ...el }

      newEl.path = lastRouter ? `${lastRouter.path}/${el.path}` : el.path

      if (newEl.children && newEl.children.length && newEl.component === ParentView) {
        children = children.concat(filterChildren(newEl.children, newEl))
      } else {
        children.push(newEl)
      }
    })

    return children
  }

  return {
    routes,
    sidebarRouters,
    getRoutes,
    getSidebarRoutes,
    generateRoutes,
  }
})
