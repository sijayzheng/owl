import type {RouteRecordRaw} from 'vue-router'
import {createRouter, createWebHistory} from 'vue-router'
import {ElMessage} from 'element-plus'
import Layout from "@/layout/layout.vue";
import {usePermissionStore} from "@/store/permissionStore";

/**
 * 不重定向白名单
 */
const NO_REDIRECT_WHITE_LIST = ['/login']

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
      component: () => import('@/views/Index.vue'),
      meta: {title: '首页', icon: 'HomeFilled'}
    }]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: {title: '登录', hidden: true}
  },
  {
    path: '/401',
    name: '401',
    component: () => import('@/views/error/401.vue'),
    meta: {title: '401', hidden: true}
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/error/404.vue'),
    meta: {title: '404', hidden: true}
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/404',
    meta: {title: '404', hidden: true}
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...staticRouters],
})


const {
  start,
  done
} = useNProgress()

const {
  loadStart,
  loadDone
} = usePageLoading()

router.beforeEach(async (to, from) => {
  start()
  loadStart()
  if (getToken()) {
    if (to.path === '/login') {
      return {path: '/'}
    } else if (NO_REDIRECT_WHITE_LIST.includes(to.path)) {
      return true
    } else {
      if (useUserStore().roles.length === 0) {
        try {
          await useUserStore().getUserInfo()
          const finalRoutes: RouteRecordRaw[] = await usePermissionStore().generateRoutes()
          finalRoutes.forEach((route) => {
            router.addRoute(route);
          });
          return to
        } catch (err) {
          await useUserStore().logout()
          ElMessage.error('用户信息获取失败')
          return {path: '/'}
        }
      } else {
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
  const title = import.meta.env.VITE_APP_TITLE
  const newTitle = to?.meta?.title as string
  useTitle(newTitle ? `${title} - ${newTitle}` : title, {
    restoreOnUnmount: false,
    observe: false,
  })
  done() // 结束Progress
  loadDone()
})

export default router
