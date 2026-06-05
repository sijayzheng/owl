<template>
  <div class="layout-container">
    <div class="sidebar-container" :class="{ collapse: isSidebarCollapse }">
      <el-card class="sidebar-logo-container" shadow="always" :class="{ collapse: isSidebarCollapse }">
        <router-link key="collapse" class="sidebar-logo-link" to="/">
          <img :src="logoImg" class="sidebar-logo" alt="">
          <el-link v-if="!isSidebarCollapse" type="primary" class="sidebar-title">
            {{ title }}
          </el-link>
        </router-link>
      </el-card>
      <el-card class="sidebar-menu" body-style="padding:0" shadow="always">
        <ElScrollbar>
          <el-menu :default-active="activeMenu" :collapse="isSidebarCollapse" :unique-opened="true" :collapse-transition="false" :popper-offset="12" mode="vertical">
            <SidebarItem v-for="(r, index) in sidebarRouters" :key="r.path + index" :item="r" :base-path="r.path" />
          </el-menu>
        </ElScrollbar>
      </el-card>
    </div>
    <div class="main-container" :class="{ collapse: isSidebarCollapse }">
      <el-card class="navbar-container" body-style="padding:0">
        <div class="navbar">
          <div class="navbar-left">
            <div class="hamburger-container" @click="toggleSideBar">
              <svg
                :class="{ active: !isSidebarCollapse }"
                class="hamburger"
                viewBox="0 0 1024 1024"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M408 442h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8zm-8 204c0 4.4 3.6 8 8 8h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56zm504-486H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zm0 632H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zM142.4 642.1L298.7 519a8.84 8.84 0 0 0 0-13.9L142.4 381.9c-5.8-4.6-14.4-.5-14.4 6.9v246.3a8.9 8.9 0 0 0 14.4 7z"
                />
              </svg>
            </div>
            <div class="tag-view">
              <ElScrollbar ref="scrollPaneRef">
                <router-link
                  v-for="tag in visitedViews" :key="tag.fullPath || tag.path" :data-tag-key="tag.fullPath || tag.path"
                  :to="tag.fullPath || tag.path || '/'" class="tag-view-item"
                  @click.middle="!isAffix(tag) ? closeSelectedTag(tag) : undefined"
                  @contextmenu.prevent="openMenu(tag, $event)"
                >
                  <el-tag :closable="!isAffix(tag)" :type="isActive(tag) ? 'primary' : 'info'" @close="() => closeSelectedTag(tag)">
                    <el-icon v-if="tag.meta && tag.meta.icon && tag.meta.icon !== '#'">
                      <component :is="tag.meta.icon" />
                    </el-icon>
                    {{ tag.title || tag.meta?.title }}
                  </el-tag>
                </router-link>
              </ElScrollbar>
            </div>
            <ul v-show="visible" :style="{ left: `${left}px`, top: `${top}px` }" class="tag-view-contextmenu">
              <li @click="refreshSelectedTag(selectedTag)">
                <RefreshRight style="width: 1em; height: 1em" />
                刷新页面
              </li>
              <li v-if="!isAffix(selectedTag)" @click="closeSelectedTag(selectedTag)">
                <Close style="width: 1em; height: 1em" />
                关闭当前
              </li>
              <li @click="closeOthersTags">
                <CircleClose style="width: 1em; height: 1em" />
                关闭其他
              </li>
              <li v-if="!isFirstView()" @click="closeLeftTags">
                <Back style="width: 1em; height: 1em" />
                关闭左侧
              </li>
              <li v-if="!isLastView()" @click="closeRightTags">
                <Right style="width: 1em; height: 1em" />
                关闭右侧
              </li>
              <li @click="closeAllTags(selectedTag)">
                <CircleClose style="width: 1em; height: 1em" />
                全部关闭
              </li>
            </ul>
          </div>
          <div class="navbar-right">
            <el-tooltip content="刷新页面" effect="dark" placement="bottom">
              <div class="navbar-right-item hover-effect" @click="refreshSelectedTag(selectedDropdownTag)">
                <el-icon>
                  <RefreshRight />
                </el-icon>
              </div>
            </el-tooltip>
            <el-tooltip content="消息" effect="dark" placement="bottom">
              <div>
                <el-popover placement="bottom" trigger="click" transition="el-zoom-in-top" :width="300" :persistent="false">
                  <template #reference>
                    <el-badge :value="noticeStore.unreadCount.value > 0 ? noticeStore.unreadCount.value : ''" :max="99">
                      <div class="navbar-right-item hover-effect">
                        <el-icon>
                          <Bell />
                        </el-icon>
                      </div>
                    </el-badge>
                  </template>
                  <template #default>
                    <SysNotice />
                  </template>
                </el-popover>
              </div>
            </el-tooltip>
            <el-tooltip :content="isDark ? '亮色' : '暗色'" effect="dark" placement="bottom">
              <div class="navbar-right-item hover-effect" @click="toggleDark">
                <el-icon>
                  <Moon v-if="!isDark" />
                  <Sunny v-else />
                </el-icon>
              </div>
            </el-tooltip>
            <div class="avatar-container">
              <el-dropdown class="avatar-dropdown" trigger="click">
                <div class="avatar-wrapper">
                  <el-avatar
                    :src="userStore.avatar"
                  />
                  <div class="avatar-meta">
                    <span class="avatar-name">{{ displayName }}</span>
                  </div>
                  <el-icon class="avatar-arrow">
                    <CaretBottom />
                  </el-icon>
                </div>
                <template #dropdown>
                  <el-dropdown-menu>
                    <router-link to="/user/profile">
                      <el-dropdown-item>个人中心</el-dropdown-item>
                    </router-link>
                    <el-dropdown-item divided @click.stop="logout()">
                      <span>退出登录</span>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </div>
        </div>
      </el-card>
      <el-card body-style="padding:0" class="app-main">
        <ElScrollbar>
          <router-view v-slot="{ Component, route }">
            <keep-alive :include="tagsViewStore.cachedViews">
              <component :is="Component" v-if="!route.meta.link" :key="route.path" />
            </keep-alive>
          </router-view>
          <InnerLink
            v-for="(item, index) in tagsViewStore.iframeViews" v-show="currentRoute.path === item.path" :key="item.path" :iframe-id="`iframe${index}`"
            :src="iframeUrl((item.meta?.link || '')as string, item.query)"
          />
        </ElScrollbar>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { ElMessageBoxOptions } from 'element-plus'
import type { RouteLocationNormalized, RouteRecordRaw } from 'vue-router'
import { Back, Bell, CaretBottom, CircleClose, Close, Moon, RefreshRight, Right, Sunny } from '@element-plus/icons-vue'
import { ElScrollbar } from 'element-plus'
import logoImg from '@/assets/logo.png'
import InnerLink from '@/layout/components/InnerLink.vue'
import SidebarItem from '@/layout/components/SidebarItem.vue'
import SysNotice from '@/layout/components/SysNotice.vue'

const title = import.meta.env.VITE_APP_TITLE

const currentRoute = useRoute()
const layoutStore = useLayoutStore()
const userStore = useUserStore()
const noticeStore = storeToRefs(useNoticeStore())
const router = useRouter()
const permissionStore = usePermissionStore()
const tagsViewStore = useTagViewStore()
const isDark = ref(false)
const visible = ref(false)
const top = ref(0)
const left = ref(0)
const selectedTag = ref<RouteLocationNormalized>()
const affixTags = ref<RouteLocationNormalized[]>([])
const canScrollLeft = ref(false)
const canScrollRight = ref(false)
const scrollPaneRef = ref<InstanceType<typeof ElScrollbar>>()

const displayName = computed(() => userStore.realName || '管理员')
const sidebarRouters = computed<RouteRecordRaw[]>(() => permissionStore.getSidebarRoutes())
const isSidebarCollapse = computed(() => useLayoutStore().isSidebarCollapse)
const activeMenu = computed(() => {
  const { meta, path } = currentRoute
  return meta.activeMenu ? meta.activeMenu as string : path
})

function toggleDark() {
  isDark.value = !isDark.value
  if (isDark.value) {
    document.documentElement.classList.add('dark')
  } else {
    document.documentElement.classList.remove('dark')
  }
}

function addIframe() {
  if (currentRoute.meta.link) {
    useTagViewStore().addIframeView(currentRoute)
  }
}

function iframeUrl(url: string | undefined, query: any) {
  if (Object.keys(query).length > 0) {
    const params = Object.keys(query)
      .map(key => `${key}=${query[key]}`)
      .join('&')
    return `${url}?${params}`
  }
  return url
}

function toggleSideBar() {
  layoutStore.toggleSidebarCollapse()
}

async function logout() {
  await ElMessageBox.confirm('确定注销并退出系统吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  } as ElMessageBoxOptions)
  userStore.logout().then(() => {
    router.replace({
      path: '/login',
      query: {
        redirect: encodeURIComponent(router.currentRoute.value.fullPath || '/'),
      },
    })
    tab.closeAllPage()
  })
}

const visitedViews = computed(() => tagsViewStore.getVisitedViews())
const routes = computed(() => permissionStore.getRoutes())
const selectedDropdownTag = computed<RouteLocationNormalized | undefined>(() => {
  return visitedViews.value.find(tag => isActive(tag)) || selectedTag.value
})

watchEffect(() => {
  addIframe()
})
watch(currentRoute, () => {
  addTags()
  moveToCurrentTag()
})

watch(visible, (value) => {
  if (value) {
    document.body.addEventListener('click', closeMenu)
  } else {
    document.body.removeEventListener('click', closeMenu)
  }
})

watch(
  visitedViews,
  () => {
    nextTick(() => {
      updateArrowState()
    })
  },
  { deep: true },
)

function isActive(route: RouteLocationNormalized): boolean {
  return currentRoute.path === route.path
}

function isAffix(tag?: RouteLocationNormalized) {
  return !!tag?.meta?.affix
}

function getOperateTag() {
  return selectedTag.value?.fullPath ? selectedTag.value : selectedDropdownTag.value
}

function isFirstView() {
  const tag = getOperateTag()
  if (!tag) {
    return false
  }
  try {
    return tag.fullPath === '/index' || tag.fullPath === visitedViews.value[1]?.fullPath
  } catch {
    return false
  }
}

function isLastView() {
  const tag = getOperateTag()
  if (!tag) {
    return false
  }
  try {
    return tag.fullPath === visitedViews.value.at(-1)?.fullPath
  } catch {
    return false
  }
}

function filterAffixTags(routeList: RouteRecordRaw[], basePath = '') {
  let tags: RouteLocationNormalized[] = []
  routeList.forEach((item) => {
    if (item.meta?.affix) {
      const tagPath = getNormalPath(`${basePath}/${item.path}`)
      tags.push({
        hash: '',
        matched: [],
        params: {},
        query: {},
        redirectedFrom: undefined,
        fullPath: tagPath,
        path: tagPath,
        name: item.name as string,
        meta: { ...item.meta },
        title: item.meta?.title || 'no-name',
      } as RouteLocationNormalized)
    }
    if (item.children) {
      const tempTags = filterAffixTags(item.children, item.path)
      if (tempTags.length >= 1) {
        tags = [...tags, ...tempTags]
      }
    }
  })
  return tags
}

function initTags() {
  const tags = filterAffixTags(routes.value)
  affixTags.value = tags
  for (const tag of tags) {
    if (tag.name) {
      tagsViewStore.addAffixView(tag)
    }
  }
}

function addTags() {
  if (typeof currentRoute.query.title === 'string') {
    currentRoute.meta.title = currentRoute.query.title
  }
  if (currentRoute.name) {
    tagsViewStore.addView(currentRoute as RouteLocationNormalized)
  }
}

function getScrollWrapper(): HTMLElement | null {
  return scrollPaneRef.value?.wrapRef ?? null
}

function smoothScrollTo(target: number) {
  const scrollWrapper = getScrollWrapper()
  if (!scrollWrapper)
    return
  scrollWrapper.scrollTo({ left: target, behavior: 'smooth' })
  setTimeout(() => {
    updateArrowState()
  }, 350)
}

function moveToTarget(currentTag: RouteLocationNormalized) {
  const container = scrollPaneRef.value?.$el as HTMLElement | undefined
  const scrollWrapper = getScrollWrapper()
  if (!container || !scrollWrapper)
    return

  const containerWidth = container.offsetWidth
  const tagKey = currentTag.fullPath || currentTag.path
  const tagListDom = Array.from(document.querySelectorAll('.tag-view-item')) as HTMLElement[]
  const currentIndex = tagListDom.findIndex(item => item.dataset.tagKey === tagKey)
  if (currentIndex === -1)
    return

  const currentElement = tagListDom[currentIndex]
  const firstTag = tagListDom[0]
  const lastTag = tagListDom.at(-1)

  if (currentElement === firstTag) {
    smoothScrollTo(0)
    return
  }

  if (currentElement === lastTag) {
    smoothScrollTo(scrollWrapper.scrollWidth - containerWidth)
    return
  }

  const prevTag = tagListDom[currentIndex - 1]
  const nextTag = tagListDom[currentIndex + 1]
  if (!prevTag || !nextTag)
    return

  const afterNextTagOffsetLeft = nextTag.offsetLeft + nextTag.offsetWidth + 3
  const beforePrevTagOffsetLeft = prevTag.offsetLeft - 3

  if (afterNextTagOffsetLeft > scrollWrapper.scrollLeft + containerWidth) {
    smoothScrollTo(afterNextTagOffsetLeft - containerWidth)
  } else if (beforePrevTagOffsetLeft < scrollWrapper.scrollLeft) {
    smoothScrollTo(beforePrevTagOffsetLeft)
  }
}

function getScrollState() {
  const scrollWrapper = getScrollWrapper()
  if (!scrollWrapper) {
    return { canLeft: false, canRight: false }
  }
  return {
    canLeft: scrollWrapper.scrollLeft > 0,
    canRight: scrollWrapper.scrollLeft < scrollWrapper.scrollWidth - scrollWrapper.clientWidth - 1,
  }
}

function updateArrowState() {
  nextTick(() => {
    const state = getScrollState()
    canScrollLeft.value = state.canLeft
    canScrollRight.value = state.canRight
  })
}

function moveToCurrentTag() {
  nextTick(() => {
    for (const item of visitedViews.value) {
      if (item.path === currentRoute.path) {
        moveToTarget(item)
        if (item.fullPath !== currentRoute.fullPath) {
          tagsViewStore.updateVisitedView(currentRoute as RouteLocationNormalized)
        }
        break
      }
    }
  })
}

function refreshSelectedTag(view?: RouteLocationNormalized) {
  if (!view)
    return
  tab.refreshPage(view)
  if (currentRoute.meta.link) {
    tagsViewStore.delIframeView(currentRoute as RouteLocationNormalized)
  }
}

function closeSelectedTag(view?: RouteLocationNormalized) {
  if (!view)
    return
  tab.closePage(view).then(({ visitedViews }: { visitedViews: RouteLocationNormalized[] }) => {
    if (isActive(view)) {
      toLastView(visitedViews, view)
    }
  })
}

function closeRightTags() {
  const tag = getOperateTag()
  if (!tag)
    return
  tab.closeRightPage(tag).then((views: RouteLocationNormalized[]) => {
    if (!views.some(item => item.fullPath === currentRoute.fullPath)) {
      toLastView(views)
    }
  })
}

function closeLeftTags() {
  const tag = getOperateTag()
  if (!tag)
    return
  tab.closeLeftPage(tag).then((views: RouteLocationNormalized[]) => {
    if (!views.some(item => item.fullPath === currentRoute.fullPath)) {
      toLastView(views)
    }
  })
}

function closeOthersTags() {
  const tag = getOperateTag()
  if (!tag)
    return
  router.push(tag.fullPath || tag.path || '/').catch(() => {
  })
  tab.closeOtherPage(tag).then(() => {
    moveToCurrentTag()
  })
}

function closeAllTags(view?: RouteLocationNormalized) {
  tab.closeAllPage().then(({ visitedViews: views }: { visitedViews: RouteLocationNormalized[] }) => {
    if (affixTags.value.some(tag => tag.path === currentRoute.path)) {
      return
    }
    toLastView(views, view)
  })
}

function toLastView(views: RouteLocationNormalized[], view?: RouteLocationNormalized) {
  const latestView = views.slice(-1)[0]
  if (latestView?.fullPath) {
    router.push(latestView.fullPath)
  } else if (view?.name === '/Index' && view?.fullPath) {
    router.replace({ path: `/redirect${view?.fullPath}` })
  } else {
    router.push('/')
  }
}

function openMenu(tag: RouteLocationNormalized, e: MouseEvent) {
  left.value = e.clientX
  top.value = e.clientY
  visible.value = true
  selectedTag.value = tag
}

function closeMenu() {
  visible.value = false
}

onMounted(() => {
  initTags()
  addTags()
  updateArrowState()
})

onBeforeUnmount(() => {
  document.body.removeEventListener('click', closeMenu)
})
</script>

<style lang="scss" scoped>
@use '@/styles/mixin.scss';
@use '@/styles/variables.module' as *;

.layout-container {
  @include mixin.clearfix;
  height: 100vh;
  width: 100vw;
  display: flex;

  .sidebar-container {
    height: 100vh;
    width: $sidebar-width;
    display: flex;
    flex-direction: column;
    gap: 4px;

    &.collapse {
      width: $sidebar-collapse-width;
    }

    .sidebar-logo-container {
      height: 50px;
      line-height: 50px;
      text-align: center;

      &.collapse {
        width: $sidebar-collapse-width;
      }

      .sidebar-logo-link {
        height: 100%;
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 0.5rem;

        .sidebar-logo {
          width: 28px;
          height: 28px;
          vertical-align: middle;
          margin-right: 0;
          margin-left: 0;
        }

        .sidebar-title {
          display: inline-block;
          margin: 0;
          font-weight: 600;
          line-height: 1;
          font-size: 15px;
          letter-spacing: 0.03em;
          vertical-align: middle;
        }
      }
    }

    .sidebar-menu {
      height: calc(100vh - 50px);
    }
  }

  .main-container {
    width: calc(100vw - $sidebar-width);
    height: 100vh;
    margin-left: 4px;

    &.collapse {
      width: calc(100vw - $sidebar-collapse-width);
    }

    .navbar-container {
      height: 50px;
      margin-bottom: 4px;

      .navbar {
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 50px;

        .navbar-left {
          display: flex;
          align-items: center;
          height: 50px;
          min-width: 0;

          .hamburger-container {
            height: 50px;
            display: flex;
            align-items: center;

            .hamburger {
              width: 22px;
              height: 22px;
              border: 1px solid transparent;
              cursor: pointer;

              &:hover {
                border-color: var(--el-color-primary-light-5);
              }
            }

            .hamburger.active {
              transform: rotate(180deg);
            }

            html.dark {
              .hamburger {
                fill: white;
              }
            }
          }

          .tag-view {
            margin-left: 8px;
            max-width: calc(100vw - 500px);

            .tag-view-item {
              margin-right: 4px;
            }
          }

          .tag-view-contextmenu {
            margin: 0;
            z-index: 3000;
            position: absolute;
            list-style-type: none;
            padding: 5px 0;
            font-size: 13px;
            background: #fff;
            box-shadow: 0 8px 24px rgba(15, 23, 42, 0.06);

            li {
              margin: 0;
              padding: 7px 16px;
              cursor: pointer;

              &:hover {
                background: var(--el-fill-color-light);
              }
            }
          }
        }

        .navbar-right {
          line-height: 1;
          display: flex;
          align-items: center;
          gap: 6px;
          margin-left: auto;
          flex-wrap: nowrap;
          height: 50px;

          .navbar-right-item {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 32px;
            height: 32px;
            font-size: 16px;
            border: 1px solid transparent;

            &.hover-effect {
              cursor: pointer;
              transition:
                background 0.3s,
                color 0.3s;
              display: inline-flex;

              &:hover {
                background: var(--el-color-primary-light-8);
                color: var(--app-accent-strong);
                border-color: var(--el-color-primary-light-5);
              }
            }
          }

          .avatar-container {
            margin-left: 6px;
            margin-right: 0;
            flex-shrink: 0;

            .avatar-dropdown {
              display: block;
              width: auto;
              height: auto;
              border: none;
              background: transparent;

              .avatar-wrapper {
                position: relative;
                display: flex;
                align-items: center;
                gap: 12px;
                padding: 4px 8px 4px 4px;

                background: var(--app-surface-bg);
                border: 1px solid var(--app-surface-border);
                min-width: 0;
                cursor: pointer;
                transition:
                  background 0.3s,
                  border-color 0.3s;

                &:hover {
                  background: var(--app-accent-soft);
                  border-color: rgba(64, 158, 255, 0.16);
                }

                .avatar-name {
                }

                .avatar-arrow {
                }
              }
            }
          }
        }
      }
    }

    .app-main {
      height: calc(100vh - 58px);
    }
  }
}
</style>
