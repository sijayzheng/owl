<template>
  <el-table ref="tableRef" v-loading="loading" :data="data" class="data-table" :row-key="rowKey" stripe border>
    <el-table-column v-if="showSelection" type="selection" width="50" />
    <el-table-column v-if="showId" align="center" fixed label="主键" prop="id" width="100" />
    <el-table-column
      v-for="col in columns"
      :key="col.prop"
      :align="col.align || 'center'"
      :fixed="col.fixed"
      :label="col.label"
      :prop="col.prop"
      :show-overflow-tooltip="col.showOverflowTooltip"
      :width="col.width"
    />
    <el-table-column align="center" fixed="right" label="操作" width="150">
      <template #default="{ row }">
        <slot name="action" :row="row" />
      </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts" setup>
withDefaults(defineProps<{
  data: any[]
  columns: { prop: string, label: string, align?: 'center' | 'left' | 'right', fixed?: boolean | 'left' | 'right', showOverflowTooltip?: boolean, width?: string | number }[]
  loading?: boolean
  rowKey?: string
  showId?: boolean
  showSelection?: boolean
}>(), {
  showId: true,
  showSelection: true,
})

const tableRef = ref()
defineExpose({ tableRef })
</script>
