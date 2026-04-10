<template>
  <div class="error-page">
    <div class="error-content">
      <div class="error-icon">
        <el-icon size="120" color="#409EFF">
          <DocumentDelete/>
        </el-icon>
      </div>
      <h1 class="error-code">404</h1>
      <p class="error-message">抱歉，您访问的页面不存在</p>
      <p class="error-description">请检查您输入的网址是否正确，或点击下方按钮返回首页</p>
      <div class="error-actions">
        <el-button type="primary" size="large" @click="goHome">
          <el-icon>
            <HomeFilled/>
          </el-icon>
          返回首页
        </el-button>
        <el-button size="large" @click="goBack">
          <el-icon>
            <ArrowLeft/>
          </el-icon>
          返回上一页
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useRouter} from 'vue-router'
import {ArrowLeft, DocumentDelete, HomeFilled} from '@element-plus/icons-vue'

const router = useRouter()

const goHome = () => {
  router.push('/')
}

const goBack = () => {
  router.back()
}
</script>

<style scoped lang="scss">
.error-page {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);

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
      color: #409EFF;
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
