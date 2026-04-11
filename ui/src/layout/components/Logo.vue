<template>
  <div class="logo-wrapper">
    <svg class="logo" viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg">
      <defs>
        <linearGradient id="logoGradient" x1="0%" y1="0%" x2="100%" y2="100%">
          <stop offset="0%" style="stop-color:#aa3bff;stop-opacity:1"/>
          <stop offset="100%" style="stop-color:#c084fc;stop-opacity:1"/>
        </linearGradient>
      </defs>
      <circle cx="100" cy="100" r="90" fill="url(#logoGradient)"/>
      <text x="100" y="120" font-size="80" text-anchor="middle" fill="white" font-weight="bold">
        O
      </text>
    </svg>
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

  .logo {
    width: 32px;
    height: 32px;
    flex-shrink: 0;
  }

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
