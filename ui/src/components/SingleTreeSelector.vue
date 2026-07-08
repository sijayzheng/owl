<template>
  <el-tree-select
    v-model="value"
    accordion
    empty-text="暂无数据"
    highlight-current
    :node-key="nodeKey"
    :data="data"
    clearable
    :style="{ minWidth: `${minWidth}px` }"
    :placeholder="`请选择${name}`"
  />
</template>

<script setup lang="ts">
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
  nodeKey: {
    type: String,
    default: 'value',
  },
})
const value = defineModel()
</script>

<style scoped lang="scss">

</style>
