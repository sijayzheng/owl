<template>
  <div class="main-wrapper">
    <el-scrollbar class="main-content">
      <router-view v-slot="{ Component, route }">
        <transition name="fade-transform" mode="out-in">
          <keep-alive :include="cachedViews">
            <component :is="Component" :key="route.fullPath"/>
          </keep-alive>
        </transition>
      </router-view>
    </el-scrollbar>
  </div>
</template>
<script setup lang="ts">
const tagViewStore = useTagViewStore()
const cachedViews = computed(() => tagViewStore.cachedViews)
</script>
<style scoped lang="scss">
.main-wrapper {
  flex: 1;
  overflow: hidden;
  background-color: var(--el-fill-color-light);

  .main-content {
    padding: 8px;
    height: 100%;
    box-sizing: border-box;

    :deep(.el-scrollbar__wrap) {
      overflow-x: hidden;
    }
  }
}

.fade-transform-leave-active,
.fade-transform-enter-active {
  transition: all 0.3s;
}

.fade-transform-enter-from {
  opacity: 0;
  transform: translateX(-10px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(10px);
}
</style>
