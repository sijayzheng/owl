<template>
  <div class="header-wrapper">
    <div class="header-left">
      <div class="collapse-btn" @click="layoutStore.toggleCollapse">
        <el-icon>
          <Fold v-if="!layoutStore.isCollapse"/>
          <Expand v-else/>
        </el-icon>
      </div>

      <div class="breadcrumb-wrapper">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item v-for="(item, index) in breadcrumbs" :key="item.path">
            <span v-if="index === breadcrumbs.length - 1" class="breadcrumb-title">
              {{ item.title }}
            </span>
            <router-link v-else :to="item.path" class="breadcrumb-link">
              {{ item.title }}
            </router-link>
          </el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>

    <div class="header-right">
      <div class="header-icon" @click="toggleDark"
           :title="isDark ? '切换到亮色模式' : '切换到暗色模式'">
        <el-icon size="20">
          <Moon v-if="!isDark"/>
          <Sunny v-else/>
        </el-icon>
      </div>

      <div class="header-icon">
        <el-badge :value="5" :max="99" size="small">
          <el-icon size="20">
            <Bell/>
          </el-icon>
        </el-badge>
      </div>

      <el-dropdown trigger="click" @command="handleCommand">
        <div class="user-dropdown">
          <el-avatar class="avatar"
                     src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
          <span class="username">管理员</span>
          <el-icon>
            <ArrowDown/>
          </el-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="profile">
              <el-icon>
                <User/>
              </el-icon>
              个人中心
            </el-dropdown-item>
            <el-dropdown-item command="logout" divided>
              <el-icon>
                <SwitchButton/>
              </el-icon>
              退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>
<script setup lang="ts">

import {ArrowDown, Bell, Expand, Fold, Moon, Sunny, SwitchButton, User} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const layoutStore = useLayoutStore()

const isDark = ref(false)

const breadcrumbs = computed(() => {
  const matched = route.matched.filter(item => item.meta && item.meta.title)
  return matched.map(item => ({
    path: item.path,
    title: item.meta.title as string,
  }))
})

const toggleDark = () => {
  isDark.value = !isDark.value
  if (isDark.value) {
    document.documentElement.classList.add('dark')
  } else {
    document.documentElement.classList.remove('dark')
  }
}

const handleCommand = (command: string) => {
  if (command === 'profile') {
    router.push('/profile')
  } else if (command === 'logout') {
    handleLogout()
  }
}

const handleLogout = () => {
  console.log('logout')
}
</script>
<style scoped lang="scss">
.header-wrapper {
  height: 50px;
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

      :deep(.el-breadcrumb) {
        display: flex;
        align-items: center;
        white-space: nowrap;

        .el-breadcrumb__item {
          display: flex;
          align-items: center;
        }

        .breadcrumb-link {
          color: var(--el-text-color-regular);
          text-decoration: none;
          transition: color 0.2s;

          &:hover {
            color: var(--el-color-primary);
          }
        }

        .breadcrumb-title {
          color: var(--el-text-color-primary);
          font-weight: 500;
        }
      }
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
      display: flex;
      align-items: center;

      &:hover {
        color: var(--el-color-primary);
      }

      :deep(.el-badge) {
        display: flex;
        align-items: center;
      }
    }

    .user-dropdown {
      cursor: pointer;
      display: flex;
      align-items: center;
      gap: 8px;
      padding: 4px 8px;
      border-radius: 4px;
      transition: background-color 0.2s;

      &:hover {
        background-color: var(--el-fill-color-light);
      }

      .avatar {
        width: 32px;
        height: 32px;
      }

      .username {
        font-size: 14px;
        color: var(--el-text-color-regular);
      }
    }
  }
}
</style>
