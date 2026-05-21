import type { RouteRecordRaw } from 'vue-router'

export const commonApi = {
  // 获取用户信息
  getUserInfo() {
    return request.get<UserInfo>('/common/userinfo')
  },
  // 获取所有路由
  getRoutes() {
    return request.get<Array<RouteRecordRaw>>('/common/getRoutes')
  },
}
