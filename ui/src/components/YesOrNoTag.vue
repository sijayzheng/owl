<template>
  <el-tag :type="yesOrNo ? 'success' : 'danger'">
    {{ yesOrNo ? '是' : '否' }}
  </el-tag>
</template>

<script setup lang="ts">
const props = defineProps({
  value: {
    type: [Number, Boolean],
    required: true,
  },
})

const yesOrNo = computed(() => {
  const value = props.value
  if (typeof props.value === 'number') {
    return value === 1
  } else {
    return value
  }
})
</script>

<style scoped lang="scss">

</style>
