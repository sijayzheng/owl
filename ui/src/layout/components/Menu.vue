<template>
  <el-menu
    :collapse="isCollapse" :default-opened="defaultOpeneds"
    background-color="var(--el-bg-color)" text-color="var(--el-text-color-regular)"
    active-text-color="var(--el-color-primary)" unique-opened :collapse-transition="false"
    mode="vertical"
    @select="handleSelect"
  >
    <MenuSubMenu :routes="menuRoutes" :is-collapse="isCollapse" />
  </el-menu>
</template>

<script setup lang="ts">
import MenuSubMenu from './MenuSubMenu.vue'

const route = useRoute()
const router = useRouter()
const layoutStore = useLayoutStore()

const isCollapse = computed(() => layoutStore.isCollapse)
const menuRoutes = computed(() => usePermissionStore().getSideBarRoutes())

function handleSelect(index: string) {
  router.push(index)
}

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
