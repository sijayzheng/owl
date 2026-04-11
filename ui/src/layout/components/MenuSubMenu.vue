<template>
  <template v-for="route in routes" :key="route.path">
    <!-- 只有一个子菜单时，直接显示子菜单 -->
    <template v-if="route.children && hasOneShowingChild(route.children) && !route.meta?.hidden">
      <menu-sub-menu :routes="getShowingChildren(route.children)"
                     :base-path="resolvePath(route.path)"
                     :is-collapse="isCollapse"/>
    </template>

    <!-- 多个子菜单时，显示为父菜单 -->
    <el-sub-menu
      v-else-if="route.children && route.children.length > 0 && !hasNoShowingChildren(route.children) && !route.meta?.hidden"
      :index="resolvePath(route.path)" teleported>
      <template #title>
        <el-icon v-if="route.meta?.icon">
          <component :is="route.meta.icon"/>
        </el-icon>
        <span v-show="!isCollapse">{{ route.meta?.title }}</span>
      </template>
      <menu-sub-menu :routes="route.children" :base-path="resolvePath(route.path)"
                     :is-collapse="isCollapse"/>
    </el-sub-menu>

    <!-- 没有子菜单时，显示为菜单项 -->
    <el-menu-item v-else-if="!route.meta?.hidden" :index="resolvePath(route.path)"
                  @click="$router.push(resolvePath(route.path))">
      <el-icon v-if="route.meta?.icon">
        <component :is="route.meta.icon"/>
      </el-icon>
      <template #title>
        <span v-show="!isCollapse">{{ route.meta?.title }}</span>
      </template>
    </el-menu-item>
  </template>
</template>
<script setup lang="ts">

interface MenuItem {
  path: string
  name?: string
  meta?: {
    title?: string
    icon?: string
    hidden?: boolean
  }
  children?: MenuItem[]
  redirect?: string
  component?: any
}

const props = defineProps<{
  routes: MenuItem[]
  basePath?: string
  isCollapse: boolean
}>()

const getShowingChildren = (children: MenuItem[] = []) => {
  return children.filter(item => !item.meta?.hidden)
}

const hasOneShowingChild = (children: MenuItem[] = []) => {
  const showingChildren = getShowingChildren(children)
  return showingChildren.length === 1
}

const hasNoShowingChildren = (children: MenuItem[] = []) => {
  const showingChildren = getShowingChildren(children)
  return showingChildren.length === 0
}

const resolvePath = (routePath: string) => {
  const basePath = props.basePath || '/'
  if (routePath.startsWith('http://') || routePath.startsWith('https://')) {
    return routePath
  }
  if (routePath.startsWith('/')) {
    return routePath
  }
  return `${basePath}/${routePath}`.replace(/\/+/g, '/')
}
</script>
<style scoped lang="scss">
.el-sub-menu {
  .el-sub-menu__title {
    display: flex;
    align-items: center;
    justify-content: flex-start;
    gap: 12px;
  }

  .el-icon {
    width: 20px;
    height: 20px;
    font-size: 18px;
  }
}

.el-menu-item {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  gap: 12px;

  .el-icon {
    width: 20px;
    height: 20px;
    font-size: 18px;
  }
}
</style>
