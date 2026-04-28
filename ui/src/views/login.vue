<template>
  <div class="login-page">
    <el-card class="login-card">
      <template #header>
        <div class="logo-section">
          <div class="logo-icon">
            <img src="@/assets/logo.png" alt="logo">
          </div>
          <div class="logo-text">OWL</div>
        </div>
      </template>
      <el-form label-position="top" size="large">
        <el-form-item label="用户名" v-model="loginFormData" prop="username">
          <el-input clearable v-model="loginFormData.username"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input clearable show-password type="password" v-model="loginFormData.password"/>
        </el-form-item>
        <el-form-item label="验证码" prop="captcha">
          <el-input clearable v-model="loginFormData.captcha" @keyup.enter="login()">
            <template #prefix>
              <el-image :src="captchaImg" @click="getCaptcha()" style="cursor: pointer"/>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button size="large" type="primary" class="login-btn" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>

  </div>
</template>
<script setup lang="ts">
const userStore = useUserStore()
const loginFormData = ref<LoginReq>({
  username: 'admin',
  password: '123456',
  uuid: '',
  captcha: ''
})

const captchaImg = ref('')

function login() {
  userStore.login(loginFormData.value)
}

function getCaptcha() {
  loginFormData.value.captcha = ''
  authApi.captcha().then(res => {
    loginFormData.value.uuid = res.uuid
    captchaImg.value = res.img
  })
}

onMounted(() => {
  getCaptcha()
})
</script>
<style scoped>
.login-page {
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: flex-end;

  .login-card {
    width: 420px;
    margin-right: 64px;

    .logo-section {
      text-align: center;
      margin-bottom: 28px;

      .logo-icon {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        width: 52px;
        height: 52px;
        margin-bottom: 12px;
      }

      .logo-text {
        font-size: 22px;
        font-weight: 700;
        color: var(--el-color-primary);
        letter-spacing: 1px;
      }
    }
  }
}


.login-btn {
  width: 100%;
  font-size: 16px;
  font-weight: 600;
  margin-top: 16px;
}

</style>
