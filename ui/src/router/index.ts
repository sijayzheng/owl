import type { RouteRecordRaw } from 'vue-router'
import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout/Layout.vue'

/**
 * 不重定向白名单
 */
const NO_REDIRECT_WHITE_LIST = ['/login', '/demo']

export const staticRouters: RouteRecordRaw[] = [
  {
    path: '',
    name: 'root',
    component: Layout,
    // 修复根路由初始跳转目标，与子路由路径保持一致
    redirect: '/index',
    children: [{
      path: 'index',
      name: 'Index',
      component: async () => import('@/views/Index.vue'),
      meta: { title: '首页', icon: 'HomeFilled', affix: true },
    }],
  },
  {
    path: '/login',
    name: 'Login',
    component: async () => import('@/views/Login.vue'),
    meta: { title: '登录', hidden: true },
  },
  {
    path: '/401',
    name: '401',
    component: async () => import('@/views/error/401.vue'),
    meta: { title: '401', hidden: true },
  },
  {
    path: '/404',
    name: '404',
    component: async () => import('@/views/error/404.vue'),
    meta: { title: '404', hidden: true },
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes: [...staticRouters],
})

const {
  start,
  done,
} = useNProgress()

router.beforeEach(async (to) => {
  start()
  if (getToken()) {
    if (to.path === '/login') {
      return { path: '/' }
    } else if (NO_REDIRECT_WHITE_LIST.includes(to.path)) {
      return true
    } else {
      if (useUserStore().roles.length === 0) {
        try {
          await useUserStore().getUserInfo()
          const finalRoutes: RouteRecordRaw[] = await usePermissionStore().generateRoutes()
          finalRoutes.forEach((route) => {
            router.addRoute(route)
          })
          // 添加兜底 404 路由（必须在动态路由之后添加，避免拦截动态路由匹配）
          router.addRoute({
            path: '/:pathMatch(.*)*',
            redirect: '/404',
            meta: { title: '404', hidden: true },
          })
          // 使用路径字符串触发重新导航，让新添加的路由参与匹配
          return to.fullPath
        } catch (err) {
          console.error(err)
          await useUserStore().logout()
          ElMessage.error('用户信息获取失败')
          return { path: '/' }
        }
      } else {
        // 动态路由已加载，检查路由是否真实存在
        if (to.matched.length === 0 || (to.matched.length === 1 && to.matched[0]?.path === '/:pathMatch(.*)*')) {
          return { name: '404', path: '/404' }
        }
        return true
      }
    }
  } else {
    // 没有token
    if (NO_REDIRECT_WHITE_LIST.includes(to.path)) {
      // 在免登录白名单，直接进入
      return true
    } else {
      const redirect = encodeURIComponent(to.fullPath || '/')
      return `/login?redirect=${redirect}` // 否则全部重定向到登录页
    }
  }
})

router.afterEach((to) => {
  const title = import.meta.env.VITE_APP_TITLE as string
  const newTitle = to?.meta?.title as string
  useTitle(newTitle ? `${title} - ${newTitle}` : title, {
    restoreOnUnmount: false,
    observe: false,
  })
  done() // 结束Progress
})

export default router
