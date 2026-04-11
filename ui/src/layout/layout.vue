<template>
  <div class="layout-container">
    <aside :class="['sidebar', { 'is-collapse': isCollapse }]">
      <Logo/>
      <div class="menu-wrapper">
        <Menu/>
      </div>
    </aside>
    <div class="main-container">
      <Header/>
      <TagView/>
      <Main/>
    </div>
  </div>
</template>
<script setup lang="ts">
import Header from '@/layout/components/Header.vue'
import Logo from '@/layout/components/Logo.vue'
import Main from '@/layout/components/Main.vue'
import Menu from '@/layout/components/Menu.vue'
import TagView from '@/layout/components/TagView.vue'

const layoutStore = useLayoutStore()
const isCollapse = computed(() => layoutStore.isCollapse)
</script>
<style lang="scss">
$sidebar-width: 200px;
$sidebar-collapsed-width: 54px;
$logo-height: 50px;
$header-height: 50px;
$tagview-height: 32px;
$transition-duration: 0.3s;

.layout-container {
  display: flex;
  height: 100vh;
  width: 100%;
  overflow: hidden;

  .sidebar {
    position: relative;
    width: $sidebar-width;
    background-color: var(--el-bg-color);
    border-right: 1px solid var(--el-border-color-light);
    transition: width $transition-duration ease;
    display: flex;
    flex-direction: column;

    &.is-collapse {
      width: $sidebar-collapsed-width;
    }

    .logo-wrapper {
      height: $logo-height;
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
        transition: opacity $transition-duration ease;
      }

      .is-collapse & .title {
        opacity: 0;
        width: 0;
        margin-left: 0;
      }
    }

    .menu-wrapper {
      flex: 1;
      overflow-y: auto;
      overflow-x: hidden;

      &::-webkit-scrollbar {
        width: 6px;
      }

      &::-webkit-scrollbar-thumb {
        background-color: var(--el-border-color);
        border-radius: 3px;
      }

      &::-webkit-scrollbar-track {
        background-color: transparent;
      }
    }
  }

  .main-container {
    flex: 1;
    display: flex;
    flex-direction: column;
    min-width: 0;
    overflow: hidden;

    .header-wrapper {
      height: $header-height;
      background-color: var(--el-bg-color);
      border-bottom: 1px solid var(--el-border-color-light);
      display: flex;
      align-items: center;
      padding: 0 16px;
      flex-shrink: 0;

      .header-left {
        display: flex;
        align-items: center;
        flex: 1;
        min-width: 0;

        .collapse-btn {
          cursor: pointer;
          font-size: 20px;
          color: var(--el-text-color-regular);
          display: flex;
          align-items: center;
          justify-content: center;
          width: 32px;
          height: 32px;
          border-radius: 4px;

          &:hover {
            background-color: var(--el-fill-color-light);
          }
        }

        .breadcrumb-wrapper {
          margin-left: 16px;
          flex: 1;
          min-width: 0;
        }
      }

      .header-right {
        display: flex;
        align-items: center;
        gap: 16px;

        .header-icon {
          cursor: pointer;
          font-size: 20px;
          color: var(--el-text-color-regular);
          position: relative;

          &:hover {
            color: var(--el-color-primary);
          }

          .badge {
            position: absolute;
            top: -4px;
            right: -4px;
            background-color: var(--el-color-danger);
            border-radius: 50%;
            min-width: 16px;
            height: 16px;
            font-size: 10px;
            color: #fff;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 0 4px;
          }
        }

        .user-dropdown {
          cursor: pointer;
          display: flex;
          align-items: center;
          gap: 8px;

          .avatar {
            width: 32px;
            height: 32px;
            border-radius: 50%;
          }

          .username {
            font-size: 14px;
            color: var(--el-text-color-regular);
          }
        }
      }
    }

    .tagview-wrapper {
      height: $tagview-height;
      background-color: var(--el-bg-color);
      border-bottom: 1px solid var(--el-border-color-light);
      flex-shrink: 0;
      display: flex;
      align-items: center;
      padding: 0 8px;
      overflow: hidden;
    }

    .main-wrapper {
      flex: 1;
      overflow: hidden;
      background-color: var(--el-fill-color-light);

      .main-content {
        padding: 8px;
        height: 100%;
        box-sizing: border-box;
      }
    }
  }
}

.el-menu--horizontal {
  & > .el-menu-item {
    height: $header-height;
    line-height: $header-height;
  }
}

</style>
