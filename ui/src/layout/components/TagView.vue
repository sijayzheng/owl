<template>
  <div class="tagview-wrapper">
    <el-scrollbar ref="scrollbarRef" class="tag-scroll">
      <div class="tag-container">
        <span v-for="tag in tagList" :key="tag.fullPath" :ref="el => tagRefs[tag.fullPath] = el"
              class="tag-item"
              :class="{
            'is-active': isActive(tag),
            'is-affix': tag.affix
          }" @click="handleTagClick(tag)" @contextmenu.prevent="handleContextMenu(tag, $event)">
          <el-tag :closable="!tag.affix" :type="isActive(tag) ? 'primary' : 'info'" size="small"
                  @close="handleClose(tag, $event)">
            <el-icon v-if="tag.icon" style="margin-right: 4px">
              <component :is="tag.icon"/>
            </el-icon>
            {{ tag.title }}
          </el-tag>
        </span>
      </div>
    </el-scrollbar>

    <teleport to="body">
      <transition name="el-zoom-in-top">
        <el-dropdown-menu v-if="contextMenuVisible" class="context-menu" :style="{
          left: contextMenuPosition.x + 'px',
          top: contextMenuPosition.y + 'px'
        }">
          <el-dropdown-item command="refresh">
            <el-icon>
              <Refresh/>
            </el-icon>
            刷新页面
          </el-dropdown-item>
          <el-dropdown-item command="close" :disabled="selectedTag?.affix">
            <el-icon>
              <Close/>
            </el-icon>
            关闭当前
          </el-dropdown-item>
          <el-dropdown-item command="closeOther">
            <el-icon>
              <FolderDelete/>
            </el-icon>
            关闭其它
          </el-dropdown-item>
          <el-dropdown-item command="closeAll">
            <el-icon>
              <Delete/>
            </el-icon>
            关闭全部
          </el-dropdown-item>
        </el-dropdown-menu>
      </transition>
    </teleport>
  </div>
</template>
<script setup lang="ts">

const route = useRoute()
const router = useRouter()
const tagViewStore = useTagViewStore()

const tagList = computed(() => tagViewStore.visitedViews)
const activeView = computed(() => tagViewStore.activeView)

const contextMenuVisible = ref(false)
const contextMenuPosition = ref({
  x: 0,
  y: 0
})
const selectedTag = ref<TagView | null>(null)

const tagRefs = ref<Record<string, any>>({})

const addTags = () => {
  const view: TagView = {
    fullPath: route.fullPath,
    path: route.path,
    title: route.meta.title as string || '未命名',
    name: route.name as string,
    icon: route.meta.icon as string,
    affix: route.meta.affix as boolean,
    query: route.query as Record<string, string>,
    params: route.params as Record<string, string>,
  }
  tagViewStore.addView(view)
  tagViewStore.addCachedView(view)
  nextTick(() => {
    moveToCurrentTag()
  })
}

const moveToCurrentTag = () => {
  nextTick(() => {
    const currentTag = tagRefs.value[route.fullPath]
    if (currentTag) {
      currentTag.scrollIntoView({
        behavior: 'smooth',
        inline: 'nearest'
      })
    }
  })
}

const isActive = (tag: TagView) => {
  return tag.fullPath === route.fullPath
}

const handleTagClick = (tag: TagView) => {
  tagViewStore.setActiveView(tag)
  router.push(tag.fullPath)
}

const handleClose = (tag: TagView, event: Event) => {
  event.stopPropagation()
  if (tag.affix) {
    return
  }

  const closedView = tag
  tagViewStore.delView(tag)

  if (isActive(tag)) {
    const latestView = tagList.value[tagList.value.length - 1]
    if (latestView) {
      router.push(latestView.fullPath)
    }
  }
}

const handleContextMenu = (tag: TagView, event: MouseEvent) => {
  event.preventDefault()
  event.stopPropagation()
  selectedTag.value = tag
  contextMenuPosition.value = {
    x: event.clientX,
    y: event.clientY
  }
  contextMenuVisible.value = true
}

const closeContextMenu = () => {
  contextMenuVisible.value = false
  selectedTag.value = null
}

const handleCommand = (command: string) => {
  if (!selectedTag.value) {
    return
  }

  switch (command) {
    case 'refresh':
      handleRefresh(selectedTag.value)
      break
    case 'close':
      handleCloseTag(selectedTag.value)
      break
    case 'closeOther':
      handleCloseOther(selectedTag.value)
      break
    case 'closeAll':
      handleCloseAll()
      break
  }
  closeContextMenu()
}

const handleRefresh = (tag: TagView) => {
  tagViewStore.refreshView(tag)
  if (isActive(tag)) {
    nextTick(() => {
      router.replace({
        path: '/redirect' + tag.fullPath,
        query: tag.query,
        params: tag.params
      })
    })
  }
}

const handleCloseTag = (tag: TagView) => {
  if (tag.affix) {
    return
  }
  tagViewStore.delView(tag)
  if (isActive(tag)) {
    const latestView = tagList.value[tagList.value.length - 1]
    if (latestView) {
      router.push(latestView.fullPath)
    }
  }
}

const handleCloseOther = (tag: TagView) => {
  tagViewStore.delOtherViews(tag)
  router.push(tag.fullPath)
}

const handleCloseAll = () => {
  tagViewStore.delAllViews()
  const affixTag = tagList.value.find(tag => tag.affix)
  if (affixTag) {
    router.push(affixTag.fullPath)
  }
}

watch(() => route.path, () => {
  addTags()
}, {immediate: true})

watch(() => contextMenuVisible.value, (val) => {
  if (val) {
    document.addEventListener('click', closeContextMenu)
  } else {
    document.removeEventListener('click', closeContextMenu)
  }
})
</script>
<style scoped lang="scss">
.tagview-wrapper {
  height: 32px;
  background-color: var(--el-bg-color);
  border-bottom: 1px solid var(--el-border-color-light);
  flex-shrink: 0;
  display: flex;
  align-items: center;
  padding: 0 8px;
  overflow: hidden;

  .tag-scroll {
    flex: 1;
    overflow: hidden;

    :deep(.el-scrollbar__wrap) {
      overflow-y: hidden;
    }

    .tag-container {
      display: flex;
      gap: 4px;
      padding: 4px 0;

      .tag-item {
        flex-shrink: 0;

        :deep(.el-tag) {
          cursor: pointer;
          border: none;
          background-color: var(--el-fill-color);
          color: var(--el-text-color-regular);
          height: 24px;
          padding: 0 8px;
          font-size: 12px;
          line-height: 24px;

          &:hover {
            background-color: var(--el-fill-color-dark);
          }
        }

        &.is-active {
          :deep(.el-tag) {
            background-color: var(--el-color-primary-light-9);
            color: var(--el-color-primary);
          }
        }
      }
    }
  }
}

.context-menu {
  position: fixed;
  z-index: 3000;
  min-width: 120px;
}
</style>
