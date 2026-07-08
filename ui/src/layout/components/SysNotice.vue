<template>
  <div v-loading="state.loading" class="layout-user-news">
    <div class="head-box">
      <div class="head-box-title">
        消息盒子
      </div>
      <div class="head-box-btn" @click="readAll">
        全部已读
      </div>
    </div>
    <div v-loading="state.loading" class="content-box">
      <template v-if="currentNewsList.length > 0">
        <div v-for="(v, k) in currentNewsList" :key="k" class="content-box-item" @click="onNewsClick(k)">
          <div class="item-content">
            <div class="content-box-title">
              {{ v.title || '消息' }}
            </div>
            <div>{{ v.message }}</div>
            <div v-if="v.content" class="content-box-msg">
              {{ v.content }}
            </div>
            <div class="content-box-time">
              {{ v.time }}
            </div>
          </div>
          <!-- 已读/未读 -->
          <span v-if="v.read" class="el-tag el-tag--success el-tag--mini read">已读</span>
          <span v-else class="el-tag el-tag--danger el-tag--mini read">未读</span>
        </div>
      </template>
      <el-empty v-else description="暂无消息" />
    </div>
  </div>
</template>

<script setup lang="ts">
const noticeStore = useNoticeStore()
const NOTICE_GROUP = {
  SYSTEM: 'system',
  NOTICE: 'notice',
  WORKFLOW: 'workflow',
}
// 定义变量内容
const state = reactive({
  loading: false,
})
const activeTab = ref<string>(NOTICE_GROUP.SYSTEM)
const newsList = computed(() => noticeStore.state.notices)

const currentNewsList = computed(() => {
  return newsList.value.filter((item: any) => {
    return (item.category || NOTICE_GROUP.SYSTEM) === activeTab.value
  })
})

// 点击消息，写入已读
async function onNewsClick(item: any) {
  const current = currentNewsList.value[item]
  if (current?.messageId) {
    // markMessageRead(userStore.userId, current.messageId)
    noticeStore.markRead(current.messageId)
  }
  if (current?.path) {
    await router.push(current.path)
  }
}

function readAll() {
  const ids = newsList.value
    .map((item: any) => item.messageId)
    .filter((item: string | number | undefined) => item !== undefined && item !== null)
  // markMessageReadBatch(userStore.userId, ids)
  noticeStore.markReadBatch(ids)
}
</script>

<style lang="scss" scoped>
.layout-user-news {
  display: flex;
  flex-direction: column;
  min-width: 0;

  .head-box {
    display: flex;
    border-bottom: 1px solid var(--app-surface-border);
    box-sizing: border-box;
    color: var(--app-text-title);
    justify-content: space-between;
    height: 40px;
    align-items: center;
    padding: 0 2px 10px;

    .head-box-title {
      font-size: 14px;
      font-weight: 600;
    }

    .head-box-btn {
      color: var(--app-accent-strong);
      font-size: 12px;
      font-weight: 600;
      cursor: pointer;
      opacity: 0.8;

      &:hover {
        opacity: 1;
      }
    }
  }

  .message-tabs {
    padding-top: 6px;

    :deep(.el-tabs__header) {
      margin-bottom: 0;
    }

    :deep(.el-tabs__nav-wrap::after) {
      background: var(--app-surface-border);
    }

    :deep(.el-tabs__item) {
      font-size: 12px;
      height: 34px;
      color: var(--app-text-muted);
    }

    :deep(.el-tabs__item.is-active) {
      color: var(--app-accent-strong);
      font-weight: 600;
    }
  }

  .content-box {
    height: 300px;
    overflow: auto;
    font-size: 13px;
    padding: 8px 0 0;

    .content-box-item {
      display: flex;
      gap: 10px;
      align-items: flex-start;
      padding: 12px;
      margin: 4px 0;

      cursor: pointer;
      transition:
        background-color 0.2s ease,
        transform 0.2s ease;

      &:hover {
        background: var(--app-accent-soft);
        transform: translateY(-1px);
      }

      .content-box-msg {
        color: var(--el-text-color-secondary);
        margin: 2px 0 0;
        display: -webkit-box;
        overflow: hidden;
        text-overflow: ellipsis;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
      }

      .content-box-time {
        color: var(--app-text-muted);
        font-size: 12px;
      }

      .item-content {
        width: 100%;
        display: flex;
        flex-direction: column;
        gap: 6px;
        color: var(--app-text-title);
        line-height: 1.6;
      }

      .content-box-title {
        font-size: 12px;
        font-weight: 600;
        color: var(--app-accent-strong);
      }

      .read {
        flex-shrink: 0;
        margin-top: 2px;
      }
    }
  }

  :deep(.el-empty__description p) {
    font-size: 13px;
  }
}
</style>
