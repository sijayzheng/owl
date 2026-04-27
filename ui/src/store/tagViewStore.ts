import type {RouteLocationNormalized} from 'vue-router'

export const useTagViewStore = defineStore('tagView', () => {
  const visitedViews = ref<TagView[]>([])
  const cachedViews = ref<string[]>([])
  const activeView = ref<TagView | null>(null)

  const addView = (view: TagView) => {
    if (visitedViews.value.some(v => v.fullPath === view.fullPath)) {
      return
    }

    if (view.affix) {
      const index = visitedViews.value.findIndex(v => v.affix)
      visitedViews.value.splice(index + 1, 0, view)
    } else {
      visitedViews.value.push(view)
    }

    activeView.value = view
  }

  const addCachedView = (view: TagView) => {
    if (!view.name) {
      return
    }
    if (cachedViews.value.includes(view.name)) {
      return
    }
    cachedViews.value.push(view.name)
  }

  const delView = (view: TagView) => {
    const index = visitedViews.value.findIndex(v => v.fullPath === view.fullPath)
    if (index > -1) {
      visitedViews.value.splice(index, 1)
    }

    if (view.name && cachedViews.value.includes(view.name)) {
      const cacheIndex = cachedViews.value.indexOf(view.name)
      cachedViews.value.splice(cacheIndex, 1)
    }
  }

  const delOtherViews = (view: TagView) => {
    visitedViews.value = visitedViews.value.filter(v => v.affix || v.fullPath === view.fullPath)
    if (view.name) {
      cachedViews.value = cachedViews.value.filter(name => name === view.name)
    } else {
      cachedViews.value = []
    }
  }

  const delAllViews = () => {
    visitedViews.value = visitedViews.value.filter(v => v.affix)
    cachedViews.value = []
  }

  const refreshView = (view: TagView) => {
    if (view.name) {
      const index = cachedViews.value.indexOf(view.name)
      if (index > -1) {
        cachedViews.value.splice(index, 1)
      }
    }
  }

  const setActiveView = (view: TagView) => {
    activeView.value = view
  }

  const findCurrentView = (route: RouteLocationNormalized) => {
    return visitedViews.value.find(v => v.fullPath === route.fullPath)
  }

  return {
    visitedViews,
    cachedViews,
    activeView,
    addView,
    addCachedView,
    delView,
    delOtherViews,
    delAllViews,
    refreshView,
    setActiveView,
    findCurrentView,
  }
})
