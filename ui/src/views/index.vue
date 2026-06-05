<template>
  <div>
    <h1>
      {{ msg }}
    </h1>
    <el-form :model="form" :rules="rules" label-position="top" />
    <el-table ref="tableRef" v-loading="loading" class="data-table" :data="data" stripe border>
      <el-table-column type="selection" width="50" fixed />
      <el-table-column label="参数主键" align="center" prop="id" width="100" fixed />
      <el-table-column label="参数名称" align="center" prop="configName" :show-overflow-tooltip="true" />
      <el-table-column label="参数键名" align="center" prop="configKey" :show-overflow-tooltip="true" />
      <el-table-column label="参数键值" align="center" prop="configValue" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" width="150" fixed="right">
        <template #default="scope">
          <el-tooltip content="修改" placement="top">
            <el-button v-hasPerm="['system:config:edit']" link type="primary" icon="Edit" @click="handleUpdate(scope.row.id)" />
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <el-button v-hasPerm="['system:config:remove']" link type="primary" icon="Delete" @click="handleDelete(scope.row.id)" />
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts">
const msg = ref('Hello')
const rules = {
  configName: [{ required: true, message: '参数名称不能为空', trigger: 'blur' }],
  configKey: [{ required: true, message: '参数键名不能为空', trigger: 'blur' }],
  configValue: [{ required: true, message: '参数键值不能为空', trigger: 'blur' }],
}
const array = []
for (let i = 0; i < 10; i++) {
  array.push({
    label: `label${i}`,
    value: i,
  })
}
const form = ref({
  id: 123,
  f_input: undefined,
  f_textarea: undefined,
  f_number: undefined,
  f_select: undefined,
  f_radio: undefined,
  f_datetime: undefined,
  f_date: undefined,
  f_time: undefined,
})
const tableRef = ref()
const loading = ref(false)
const data = ref([{
  id: 123,
  aa: '',
}])

function handleUpdate(id) {
}

function handleDelete(id) {
}
</script>
