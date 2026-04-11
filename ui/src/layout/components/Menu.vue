<template>
  <el-menu :default-active="activeMenu" :collapse="isCollapse" :default-opened="defaultOpeneds"
           background-color="var(--el-bg-color)" text-color="var(--el-text-color-regular)"
           active-text-color="var(--el-color-primary)" unique-opened :collapse-transition="false"
           mode="vertical"
           @select="handleSelect">
    <menu-sub-menu :routes="menuRoutes" :is-collapse="isCollapse"/>
  </el-menu>
</template>
<script setup lang="ts">
import {type RouteRecordRaw, useRoute, useRouter} from 'vue-router'
import MenuSubMenu from './MenuSubMenu.vue'

const route = useRoute()
const router = useRouter()
const layoutStore = useLayoutStore()

const isCollapse = computed(() => layoutStore.isCollapse)
const menuRoutes = ref<RouteRecordRaw[]>([])

const getMenuRoutes = (routes: RouteRecordRaw[]) => {
  return routes
    .filter(route => route.path && !route.path.startsWith('http'))
    .map(route => {
      if (route.path === '') {
        return route.children || []
      }
      return [route]
    })
    .flat()
    .filter(route => !route.meta?.hidden)
}

watch(
  () => router.options.routes,
  (routes) => {
    menuRoutes.value = getMenuRoutes(routes)
  },
  {immediate: true}
)

const handleSelect = (index: string) => {
  router.push(index)
}

const activeMenu = computed(() => {
  const {
    meta,
    path
  } = route
  if (meta.activeMenu) {
    return meta.activeMenu
  }
  return path
})

const defaultOpeneds = computed(() => {
  const matched = route.matched.filter(item => item.meta && item.meta.title)
  return matched.map(item => item.path)
})
</script>
<style scoped lang="scss">
.el-menu {
  border-right: none;

  .el-menu-item {
    &:hover {
      background-color: var(--el-fill-color-light);
    }

    &.is-active {
      background-color: var(--el-color-primary-light-9);
    }
  }
}
</style>
