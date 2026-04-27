<template>
  <div class="error-page">
    <div class="error-content">
      <div class="error-icon">
        <el-icon size="120" color="#E6A23C">
          <Lock/>
        </el-icon>
      </div>
      <h1 class="error-code">401</h1>
      <p class="error-message">抱歉，您没有权限访问该页面</p>
      <p class="error-description">您可能需要登录或联系管理员获取访问权限</p>
      <div class="error-actions">
        <el-button type="primary" size="large" @click="goLogin">
          <el-icon>
            <UserFilled/>
          </el-icon>
          {{ remaining }}秒后跳转登录页
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useRouter} from 'vue-router'
import {Lock, UserFilled} from '@element-plus/icons-vue'

const router = useRouter()

const goLogin = () => {
  router.push('/login')
}

const {
  remaining,
  start
} = useCountdown(3, {
  onComplete() {
    goLogin()
  }
})
onMounted(() => {
  start()
})

</script>

<style scoped lang="scss">
.error-page {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%);

  .error-content {
    text-align: center;
    padding: 60px;
    background: #fff;
    border-radius: 16px;
    box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
    max-width: 500px;

    .error-icon {
      margin-bottom: 24px;
    }

    .error-code {
      font-size: 80px;
      font-weight: 700;
      color: #E6A23C;
      margin: 0 0 16px;
      line-height: 1;
    }

    .error-message {
      font-size: 24px;
      color: #303133;
      margin: 0 0 12px;
      font-weight: 500;
    }

    .error-description {
      font-size: 14px;
      color: #909399;
      margin: 0 0 32px;
      line-height: 1.6;
    }

    .error-actions {
      display: flex;
      justify-content: center;
      gap: 16px;

      .el-button {
        display: flex;
        align-items: center;
        gap: 8px;
      }
    }
  }
}

@media (max-width: 768px) {
  .error-page {
    .error-content {
      margin: 20px;
      padding: 40px 20px;

      .error-code {
        font-size: 60px;
      }

      .error-message {
        font-size: 20px;
      }

      .error-actions {
        flex-direction: column;

        .el-button {
          width: 100%;
          justify-content: center;
        }
      }
    }
  }
}
</style>
