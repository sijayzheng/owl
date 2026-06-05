import defaultAvatar from '@/assets/avatar.jpg'
import router from '@/router'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref<UserInfo>()
  const token = ref<string>()
  const routers = ref<string[]>()
  const rememberMe = ref<boolean>(true)

  const username = ref<string>('')
  const realName = ref<string>('')
  const userId = ref<string | number>('')
  const avatar = ref<string>('')
  const roles = computed(() => userInfo.value?.roles || []) // 用户角色编码集合 → 判断路由权限
  const permissions = computed(() => userInfo.value?.permissions || []) // 用户权限编码集合 → 判断按钮权限

  async function login(params: LoginReq) {
    const data = await authApi.login(params)
    token.value = data.token
    setToken(data.token)
    await getUserInfo()
    const newRoute = router.currentRoute.value
    await router.push((newRoute.query?.redirect && decodeURIComponent(newRoute.query.redirect as string)) || '/')
    await usePermissionStore().generateRoutes()
  }

  async function logout() {
    await authApi.logout()
    userInfo.value = undefined
    token.value = ''
    removeToken()
    await router.push('/login')
  }

  async function getUserInfo() {
    userInfo.value = await commonApi.getUserInfo()
    const { user } = userInfo.value
    username.value = user.username
    realName.value = user.realName
    userId.value = user.id
    avatar.value = user.avatar || defaultAvatar
  }

  return {
    userInfo,
    token,
    routers,
    rememberMe,
    username,
    realName,
    userId,
    avatar,
    roles,
    permissions,
    login,
    logout,
    getUserInfo,
  }
})
