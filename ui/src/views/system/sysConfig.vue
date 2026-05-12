<template>
  <div class="p-2">
    <div class="mb-[10px]">
      <el-card shadow="hover">
        <el-form ref="queryFormRef" :inline="true" :model="queryParams" @submit.prevent>
          <el-form-item label="参数名称" prop="configName">
            <el-input v-model="queryParams.configName" clearable placeholder="请输入参数名称" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="参数键名" prop="configKey">
            <el-input v-model="queryParams.configKey" clearable placeholder="请输入参数键名" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item>
            <el-button icon="Search" type="primary" @click="handleQuery">
              搜索
            </el-button>
            <el-button icon="Refresh" @click="resetQuery">
              重置
            </el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
    <el-card shadow="hover">
      <template #header>
        <el-row :gutter="10">
          <el-col :span="1.5">
            <el-button icon="Plus" plain type="primary" @click="dialogVisible = true">
              新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button :disabled="!ids.length" icon="Delete" plain type="danger" @click="handleDelete()">
              删除
            </el-button>
          </el-col>
        </el-row>
      </template>
      <el-table ref="tableRef" v-loading="loading" :data="data" :row-key="row => row.id" highlight-current-row border @selection-change="handleSelectionChange">
        <el-table-column reserve-selection type="selection" width="55" />
        <el-table-column fixed label="主键" prop="id" />
        <el-table-column label="参数名称" prop="configName" />
        <el-table-column label="参数键名" prop="configKey" />
        <el-table-column label="参数键值" prop="configValue" />
        <el-table-column fixed="right" label="操作" width="150">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button icon="Edit" link type="primary" @click="handleUpdate(scope.row.id)" />
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button icon="Delete" link type="primary" @click="handleDelete(scope.row.id)" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <Pagination v-show="total && total > 0" v-model:limit="queryParams.size" v-model:page="queryParams.page" :total="total" @pagination="getList" />
    </el-card>
    <!-- 添加或修改参数配置对话框 -->
    <el-dialog v-model="dialogVisible" :title="`${form.id ? '修改' : '添加'}人员信息`" append-to-body width="500px" @closed="reset()">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button :loading="submitting" type="primary" @click="submitForm">
            确 定
          </el-button>
          <el-button @click="dialogVisible = false">
            取 消
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
const queryParams = ref<SysConfigQuery>({
  page: 1,
  size: 10,
  configName: '',
  configKey: '',
})

const rules = ref({
  configName: [{ required: true, message: '参数名称不能为空', trigger: 'blur' }],
  configKey: [{ required: true, message: '参数键名不能为空', trigger: 'blur' }],
})
const queryFormRef = ref()
const formRef = ref()
const tableRef = ref()
const ids = ref<number[]>([])
const dialogVisible = ref(false)

const { data, loading, send: getList, total } = usePagination(() => sysConfigApi.page(queryParams.value))
const { loading: submitting, form, send: submit, reset, updateForm } = useForm(
  formData => formData.id ? sysConfigApi.update(form.value) : sysConfigApi.add(form.value),
  {
    initialForm: sysConfigFormInitData,
    resetAfterSubmiting: true,
  },
).onComplete(() => {
  reset()
  dialogVisible.value = false
}).onSuccess(() => getList())

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.page = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  queryFormRef.value?.resetFields()
  handleQuery()
}

/** 多选框选中数据 */
function handleSelectionChange(selection: SysConfig[]) {
  ids.value = selection.map(item => item.id)
}

/** 提交按钮 */
function submitForm() {
  formRef.value?.validate((valid: boolean) => {
    if (valid) {
      submit()
    }
  })
}

/** 修改按钮操作 */
async function handleUpdate(id: number) {
  updateForm(await sysConfigApi.getById(id))
  dialogVisible.value = true
}

/** 删除按钮操作 */
function handleDelete(id?: number) {
  const _ids: number[] = id ? [id] : ids.value
  ElMessageBox.confirm(`是否确认删除id为"${_ids}"的数据项？`)
    .then(() => {
      sysConfigApi.remove(_ids).then(() => {
        getList()
      })
    })
}
</script>

<style scoped lang="scss">
</style>
