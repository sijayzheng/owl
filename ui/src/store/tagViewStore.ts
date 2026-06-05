import type { RouteLocationNormalized } from 'vue-router'

function normalizeVisitedView(view: RouteLocationNormalized): TagView {
  return Object.assign({}, view, {
    title: String(view.meta?.title || '未命名'),
  })
}

export const useTagViewStore = defineStore('tagView', () => {
  const visitedViews = ref<TagView[]>([])
  const cachedViews = ref<string[]>([])
  const iframeViews = ref<TagView[]>([])

  const getVisitedViews = (): TagView[] => {
    return visitedViews.value
  }
  const getIframeViews = (): TagView[] => {
    return iframeViews.value
  }
  const getCachedViews = (): string[] => {
    return cachedViews.value
  }

  const addVisitedView = (view: RouteLocationNormalized): void => {
    if (visitedViews.value.some((v: RouteLocationNormalized) => v.path === view.path))
      return
    visitedViews.value.push(normalizeVisitedView(view))
  }
  const addCachedView = (view: RouteLocationNormalized): void => {
    const viewName = view.name as string
    if (!viewName)
      return
    if (cachedViews.value.includes(viewName))
      return
    if (!view.meta?.noCache) {
      cachedViews.value.push(viewName)
    }
  }
  const addView = (view: RouteLocationNormalized) => {
    addVisitedView(view)
    addCachedView(view)
  }

  const addIframeView = (view: RouteLocationNormalized): void => {
    if (iframeViews.value.some((v: RouteLocationNormalized) => v.path === view.path))
      return
    iframeViews.value.push(normalizeVisitedView(view))
  }

  const delIframeView = (view: RouteLocationNormalized): Promise<RouteLocationNormalized[]> => {
    return new Promise((resolve) => {
      iframeViews.value = iframeViews.value.filter((item: RouteLocationNormalized) => item.path !== view.path)
      resolve(iframeViews.value.slice())
    })
  }

  const addAffixView = (view: RouteLocationNormalized): void => {
    if (visitedViews.value.some((v: RouteLocationNormalized) => v.path === view.path))
      return
    const insertIndex = visitedViews.value.findIndex(item => !item.meta?.affix)
    const normalizedView = normalizeVisitedView(view)
    if (insertIndex === -1) {
      visitedViews.value.push(normalizedView)
    } else {
      visitedViews.value.splice(insertIndex, 0, normalizedView)
    }
  }

  const delVisitedView = (view: RouteLocationNormalized): Promise<RouteLocationNormalized[]> => {
    return new Promise((resolve) => {
      for (const [i, v] of visitedViews.value.entries()) {
        if (v.path === view.path) {
          visitedViews.value.splice(i, 1)
          break
        }
      }
      resolve(visitedViews.value.slice())
    })
  }

  const delCachedView = (view?: RouteLocationNormalized): Promise<string[]> => {
    let viewName = ''
    if (view) {
      viewName = view.name
    }
    return new Promise((resolve) => {
      const index = cachedViews.value.indexOf(viewName)
      index > -1 && cachedViews.value.splice(index, 1)
      resolve([...cachedViews.value])
    })
  }

  const delView = (
    view: RouteLocationNormalized,
  ): Promise<{
    visitedViews: RouteLocationNormalized[]
    cachedViews: string[]
  }> => {
    return new Promise((resolve) => {
      void delVisitedView(view)
      void delCachedView(view)
      resolve({
        visitedViews: visitedViews.value.slice(),
        cachedViews: [...cachedViews.value],
      })
    })
  }

  const delOthersVisitedViews = (view: RouteLocationNormalized): Promise<RouteLocationNormalized[]> => {
    return new Promise((resolve) => {
      visitedViews.value = visitedViews.value.filter((v: RouteLocationNormalized) => {
        return v.meta?.affix || v.path === view.path
      })
      resolve(visitedViews.value.slice())
    })
  }

  const delOthersCachedViews = (view: RouteLocationNormalized): Promise<string[]> => {
    const viewName = view.name as string
    return new Promise((resolve) => {
      const index = cachedViews.value.indexOf(viewName)
      if (index > -1) {
        cachedViews.value = cachedViews.value.slice(index, index + 1)
      } else {
        cachedViews.value = []
      }
      resolve([...cachedViews.value])
    })
  }
  const delOthersViews = (
    view: RouteLocationNormalized,
  ): Promise<{
    visitedViews: RouteLocationNormalized[]
    cachedViews: string[]
  }> => {
    return new Promise((resolve) => {
      void delOthersVisitedViews(view)
      void delOthersCachedViews(view)
      resolve({
        visitedViews: visitedViews.value.slice(),
        cachedViews: [...cachedViews.value],
      })
    })
  }

  const delAllVisitedViews = (): Promise<RouteLocationNormalized[]> => {
    return new Promise((resolve) => {
      visitedViews.value = visitedViews.value.filter((tag: RouteLocationNormalized) => tag.meta?.affix)
      resolve(visitedViews.value.slice())
    })
  }

  const delAllCachedViews = (): Promise<string[]> => {
    return new Promise((resolve) => {
      cachedViews.value = []
      resolve([...cachedViews.value])
    })
  }

  const delAllViews = (): Promise<{
    visitedViews: RouteLocationNormalized[]
    cachedViews: string[]
  }> => {
    return new Promise((resolve) => {
      void delAllVisitedViews()
      void delAllCachedViews()
      resolve({
        visitedViews: visitedViews.value.slice(),
        cachedViews: [...cachedViews.value],
      })
    })
  }

  const updateVisitedView = (view: RouteLocationNormalized): void => {
    for (let v of visitedViews.value) {
      if (v.path === view.path) {
        v = Object.assign(v, view)
        break
      }
    }
  }

  const delRightTags = (view: RouteLocationNormalized): Promise<RouteLocationNormalized[]> => {
    return new Promise((resolve) => {
      const index = visitedViews.value.findIndex((v: RouteLocationNormalized) => v.path === view.path)
      if (index === -1) {
        resolve(visitedViews.value.slice())
        return
      }
      visitedViews.value = visitedViews.value.filter((item: RouteLocationNormalized, idx: number) => {
        if (idx <= index || (item.meta && item.meta.affix)) {
          return true
        }
        const i = cachedViews.value.indexOf(item.name)
        if (i > -1) {
          cachedViews.value.splice(i, 1)
        }
        return false
      })
      resolve(visitedViews.value.slice())
    })
  }

  const delLeftTags = (view: RouteLocationNormalized): Promise<RouteLocationNormalized[]> => {
    return new Promise((resolve) => {
      const index = visitedViews.value.findIndex((v: RouteLocationNormalized) => v.path === view.path)
      if (index === -1) {
        resolve(visitedViews.value.slice())
        return
      }
      visitedViews.value = visitedViews.value.filter((item: RouteLocationNormalized, idx: number) => {
        if (idx >= index || (item.meta && item.meta.affix)) {
          return true
        }
        const i = cachedViews.value.indexOf(item.name)
        if (i > -1) {
          cachedViews.value.splice(i, 1)
        }
        return false
      })
      resolve(visitedViews.value.slice())
    })
  }

  return {
    visitedViews,
    cachedViews,
    iframeViews,

    getVisitedViews,
    getIframeViews,
    getCachedViews,

    addVisitedView,
    addAffixView,
    addCachedView,
    delVisitedView,
    delCachedView,
    updateVisitedView,
    addView,
    delView,
    delAllViews,
    delAllVisitedViews,
    delAllCachedViews,
    delOthersViews,
    delRightTags,
    delLeftTags,
    addIframeView,
    delIframeView,
  }
})
