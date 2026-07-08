import type { RouteLocationMatched, RouteLocationNormalized } from 'vue-router'

export default {
  /**
   * 刷新当前tab页签
   * @param obj 标签对象
   */
  async refreshPage(obj?: RouteLocationNormalized): Promise<void> {
    const { path, query, matched } = router.currentRoute.value
    // 防止在重定向过程中重复刷新
    if (path.startsWith('/redirect/')) {
      return Promise.resolve()
    }
    if (obj === undefined) {
      matched.forEach((m: RouteLocationMatched) => {
        if (m.components && m.components.default && m.components.default.name) {
          if (!['Layout', 'ParentView'].includes(m.components.default.name)) {
            obj = {
              name: m.components.default.name,
              path,
              query,
              matched: [],
              fullPath: path,
              hash: '',
              params: {},
              redirectedFrom: undefined,
              meta: m.meta || {},
            } as RouteLocationNormalized
          }
        }
      })
    }
    await useTagViewStore().delCachedView(obj)
    await router.replace({
      path: `${obj?.path ?? ''}`,
      query: obj?.query ?? {},
    })
  },
  // 关闭指定tab页签
  async closePage(obj?: RouteLocationNormalized): Promise<{ visitedViews: RouteLocationNormalized[], cachedViews: string[] } | any> {
    if (obj === undefined) {
      // prettier-ignore
      const { visitedViews } = await useTagViewStore().delView(router.currentRoute.value)
      const latestView = visitedViews.slice(-1)[0]
      if (latestView) {
        return router.push(latestView.fullPath)
      }
      return router.push('/')
    }
    return useTagViewStore().delView(obj)
  },
  // 关闭所有tab页签
  closeAllPage() {
    return useTagViewStore().delAllViews()
  },
  // 关闭左侧tab页签
  closeLeftPage(obj?: RouteLocationNormalized) {
    return useTagViewStore().delLeftTags(obj || router.currentRoute.value)
  },
  // 关闭右侧tab页签
  closeRightPage(obj?: RouteLocationNormalized) {
    return useTagViewStore().delRightTags(obj || router.currentRoute.value)
  },
  // 关闭其他tab页签
  closeOtherPage(obj?: RouteLocationNormalized) {
    return useTagViewStore().delOthersViews(obj || router.currentRoute.value)
  },
}
