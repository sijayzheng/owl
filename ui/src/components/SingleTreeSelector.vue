<template>
  <el-tree-select
    v-model="value"
    :data="data"
    :node-key="nodeKey"
    :placeholder="`请选择${name}`"
    :style="{ minWidth: `${minWidth}px` }"
    accordion
    check-strictly
    clearable
    empty-text="暂无数据"
    highlight-current
  />
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
  nodeKey: {
    type: String,
    default: 'value',
  },
})
const value = defineModel<string | number>()
</script>

<style lang="scss" scoped>

</style>
