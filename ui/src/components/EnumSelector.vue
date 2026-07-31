<template>
  <el-select
    v-model="value"
    :placeholder="`请选择${name}`"
    :style="{ minWidth: `${minWidth}px` }"
    clearable
  >
    <el-option v-for="item in data" :key="item.value" :label="item.label" :value="item.value" />
  </el-select>
</template>

<script lang="ts" setup>
const {
  enumType,
  name = '',
  minWidth = 200,
} = defineProps<{
  enumType: EnumType
  name?: string
  minWidth?: number
}>()

const value = defineModel<string>()

const { data } = useRequest(() => commonApi.getEnumSelect(enumType))
</script>

<style lang="scss" scoped>

</style>
