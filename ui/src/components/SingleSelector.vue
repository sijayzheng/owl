<template>
  <el-select v-model="value" clearable style="min-width: 200px">
    <el-option v-for="item in data" :key="item.value" :label="item.label" :value="item.value" />
  </el-select>
</template>

<script setup lang="ts">
defineProps({
  data: {
    type: Array<SelectNode>,
    required: true,
  },
})
const value = defineModel<string>()
</script>

<style scoped lang="scss">

</style>
