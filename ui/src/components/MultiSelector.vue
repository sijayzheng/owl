<template>
  <el-select v-model="value" clearable multiple style="min-width: 200px">
    <template #header>
      <el-checkbox
        v-model="checkAll"
        :indeterminate="indeterminate"
        @change="handleCheckAll"
      >
        All
      </el-checkbox>
    </template>
    <el-option v-for="item in data" :key="item.value" :label="item.label" :value="item.value" />
  </el-select>
</template>

<script lang="ts" setup>
import type { CheckboxValueType } from 'element-plus'

const props = defineProps({
  data: {
    type: Array<SelectNode>,
    required: true,
  },
})
const value = defineModel<string[]>()
const checkAll = ref(false)
const indeterminate = ref(false)

watch(value, (val: string[] | undefined) => {
  if (val) {
    if (val.length === 0) {
      checkAll.value = false
      indeterminate.value = false
    } else if (val.length === props.data.length) {
      checkAll.value = true
      indeterminate.value = false
    } else {
      indeterminate.value = true
    }
  }
})

function handleCheckAll(val: CheckboxValueType) {
  indeterminate.value = false
  if (val) {
    value.value = props.data.map(_ => _.value)
  } else {
    value.value = []
  }
}
</script>

<style lang="scss" scoped>

</style>
