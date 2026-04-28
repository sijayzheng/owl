<template>
  <div class="logo-wrapper">
    <img class="logo" src="@/assets/logo.png" alt="logo">
    <span v-show="!isCollapse" class="title">OWL</span>
  </div>
</template>
<script setup lang="ts">
const layoutStore = useLayoutStore()

const isCollapse = computed(() => layoutStore.isCollapse)
</script>
<style scoped lang="scss">
.logo-wrapper {
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: var(--el-bg-color);
  border-bottom: 1px solid var(--el-border-color-light);
  overflow: hidden;

  .title {
    margin-left: 12px;
    font-size: 16px;
    font-weight: 600;
    color: var(--el-text-color-primary);
    white-space: nowrap;
    transition: opacity 0.3s ease;
  }
}
</style>
