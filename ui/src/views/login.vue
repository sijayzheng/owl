<template>
  <el-form>
    <el-form-item label="用户" v-model="loginFormData" prop="username">
      <el-input v-model="loginFormData.username"/>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="loginFormData.password"/>
    </el-form-item>
    <el-form-item label="验证码" prop="captcha">
      <el-input v-model="loginFormData.captcha" @keyup.enter="login()">
        <template #prefix>
          <el-image :src="captchaImg" @click="getCaptcha()" style="cursor: pointer"/>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item>
      <el-button @click="login">登录</el-button>
    </el-form-item>
  </el-form>
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

<style scoped></style>
