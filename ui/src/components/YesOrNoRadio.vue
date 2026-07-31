<template>
  <el-radio-group v-model="value">
    <el-radio-button label="是" :value="true" />
    <el-radio-button label="否" :value="false" />
  </el-radio-group>
</template>

<script setup lang="ts">
const value = defineModel<boolean>()
</script>

<style scoped lang="scss">

</style>
