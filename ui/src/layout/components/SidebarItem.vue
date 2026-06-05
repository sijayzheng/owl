<template>
  <div v-if="!item.meta?.hidden">
    <template v-if="hasOneShowingChild(item, item.children) && (!onlyOneChild.children || onlyOneChild.noShowingChildren) && !item.meta?.alwaysShow">
      <component
        :is="isHttp(resolvePath(onlyOneChild.path, onlyOneChild.query)) ? 'a' : 'router-link'"
        v-if="onlyOneChild"
        v-bind="isHttp(resolvePath(onlyOneChild.path, onlyOneChild.query)) ? { href: resolvePath(onlyOneChild.path, onlyOneChild.query), target: '_blank', rel: 'noopener' } : { to: resolvePath(onlyOneChild.path, onlyOneChild.query) }"
      >
        <el-menu-item
          :class="{ 'submenu-title-noDropdown': !isNest }"
          :index="resolvePath(onlyOneChild.path)"
        >
          <el-icon v-if="onlyOneChild.meta?.icon || item.meta?.icon">
            <component :is="onlyOneChild.meta?.icon || item.meta?.icon" />
          </el-icon>
          <template #title>
            <span :title="hasTitle(onlyOneChild.meta?.title || item.meta?.title)" class="menu-title">
              {{ onlyOneChild.meta?.title || item.meta?.title || '未命名' }}
            </span>
          </template>
        </el-menu-item>
      </component>
    </template>
    <el-sub-menu
      v-else
      :index="resolvePath(item.path)"
      :popper-class="popperClass"
      :popper-offset="isNest ? 4 : 12"
      teleported
    >
      <template v-if="item.meta" #title>
        <el-icon v-if=" item.meta?.icon">
          <component :is=" item.meta.icon" />
        </el-icon>
        <span v-if="!isSidebarCollapse" :title="hasTitle(item.meta?.title)" class="menu-title">{{ item.meta?.title }}</span>
      </template>

      <sidebar-item
        v-for="(child, index) in item.children"
        :key="child.path + index"
        :base-path="resolvePath(child.path)"
        :is-nest="true"
        :item="child"
        :popper-class="popperClass"
        class="nest-menu"
      />
    </el-sub-menu>
  </div>
</template>

<script lang="ts" setup>
import type { RouteRecordRaw } from 'vue-router'

const props = defineProps({
  item: {
    type: Object as PropType<RouteRecordRaw>,
    required: true,
  },
  isNest: {
    type: Boolean,
    default: false,
  },
  basePath: {
    type: String,
    default: '',
  },
  popperClass: {
    type: String,
    default: '',
  },
})
const isSidebarCollapse = computed(() => useLayoutStore().isSidebarCollapse)
const onlyOneChild = ref<any>({})

function hasOneShowingChild(parent: RouteRecordRaw, children?: RouteRecordRaw[]) {
  if (!children) {
    children = []
  }
  const showingChildren = children.filter((item) => {
    if (item.meta?.hidden) {
      return false
    }
    onlyOneChild.value = item
    return true
  })
  if (showingChildren.length === 1 && parent.name === 'root') {
    return true
  }
  if (showingChildren.length === 0) {
    onlyOneChild.value = { ...parent, path: '', noShowingChildren: true }
    return true
  }
  return false
}

function resolvePath(routePath: string, routeQuery?: string): any {
  if (isHttp(routePath)) {
    return routePath
  }
  if (isHttp(props.basePath as string)) {
    return props.basePath
  }
  if (routeQuery) {
    const query = JSON.parse(routeQuery)
    return {
      path: getNormalPath(`${props.basePath}/${routePath}`),
      query,
    }
  }
  return getNormalPath(`${props.basePath}/${routePath}`)
}

function hasTitle(title: any): string {
  return !title || title.length <= 5 ? '' : title
}
</script>
