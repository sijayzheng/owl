<template>
  <el-tree-select v-model="value" node-key="value" :data="data" clearable multiple :style="{ minWidth: `${minWidth}px` }" :placeholder="`请选择${name}`" />
</template>

<script lang="ts" setup>
defineProps({
  data: {
    type: Array<TreeNode>,
    default: [],
  },
  name: {
    type: String,
    default: '',
  },
  minWidth: {
    type: Number,
    default: 200,
  },
})
const value = defineModel<string[]>()
</script>

<style lang="scss" scoped>

</style>
