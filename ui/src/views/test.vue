<template>
  <div class="flex-center wh-full">
    <el-card class="w-400px">
      <template #header>
        <div class="flex-between">
          <span>主题切换演示</span>
          <el-switch
            v-model="isDark"
            inline-prompt
            :active-icon="Moon"
            :inactive-icon="Sunny"
          />
        </div>
      </template>
      <el-space direction="vertical" fill>
        <el-button type="primary">
          Primary
        </el-button>
        <el-button type="success">
          Success
        </el-button>
        <el-button type="warning">
          Warning
        </el-button>
        <el-button type="danger">
          Danger
        </el-button>
        <el-button type="info">
          Info
        </el-button>
      </el-space>
    </el-card>
  </div>
  <section id="center">
    <div class="hero">
      <img :src="LogoImg" class="base" width="170" height="179" alt=""/>
      <img :src="LogoImg" class="framework" alt="Vue logo"/>
      <img :src="LogoImg" class="vite" alt="Vite logo"/>
    </div>
    <div>
      <h1>Get started</h1>
      <p>Edit <code>src/App.vue</code> and save to test <code>HMR</code></p>
    </div>
    <button class="counter" @click="count++">Count is {{ count }}</button>
  </section>

  <div class="ticks"></div>

  <section id="next-steps">
    <div id="docs">
      <h2>Documentation</h2>
      <p>Your questions, answered</p>
      <ul>
        <li>
          <a href="https://vite.dev/" target="_blank">
            <img class="logo" :src="LogoImg" alt=""/>
            Explore Vite
          </a>
        </li>
        <li>
          <a href="https://vuejs.org/" target="_blank">
            <img class="button-icon" :src="LogoImg" alt=""/>
            Learn more
          </a>
        </li>
      </ul>
    </div>
    <div id="social">
      <h2>Connect with us</h2>
      <p>Join the Vite community</p>
      <ul>
        <li>
          <a href="https://github.com/vitejs/vite" target="_blank">
            GitHub
          </a>
        </li>
        <li>
          <a href="https://chat.vite.dev/" target="_blank">
            Discord
          </a>
        </li>
        <li>
          <a href="https://x.com/vite_js" target="_blank">
            X.com
          </a>
        </li>
        <li>
          <a href="https://bsky.app/profile/vite.dev" target="_blank">
            Bluesky
          </a>
        </li>
      </ul>
    </div>
  </section>

  <div class="ticks"></div>
  <section id="spacer"></section>
</template>
<script setup lang="ts">
import {Moon, Sunny} from '@element-plus/icons-vue'
import {isDark} from '@/composables/dark'
import {ref} from 'vue'
import LogoImg from '@/assets/logo.png'

const count = ref(0)
</script>
