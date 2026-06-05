<template>
  <div>
    <el-card body-style="padding-bottom:8px" class="search-card" shadow="hover">
      <el-form ref="queryFormRef" :inline="true" :model="queryParams">
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
    <el-card class="table-card" shadow="hover">
      <template #header>
        <el-button v-hasPerm="['system:sysConfig:add']" icon="Plus" plain type="primary" @click="dialogVisible = true">
          新增
        </el-button>
        <el-button v-hasPerm="['system:sysConfig:remove']" icon="Delete" plain type="danger" @click="handleDelete()">
          删除
        </el-button>
        <el-button v-hasPerm="['system:sysConfig:export']" :loading="exportDataLoading" icon="Download" plain type="warning" @click="exportData">
          导出
        </el-button>
        <el-button v-hasPerm="['system:sysConfig:export']" :loading="templateLoading" icon="Download" plain type="warning" @click="downloadTemplate">
          下载模板
        </el-button>
        <el-button v-hasPerm="['system:sysConfig:export']" :loading="uploading" icon="Upload" plain type="warning" @click="handleUpload">
          导入{{ progress }}
        </el-button>
      </template>
      <el-table ref="tableRef" v-loading="loading" :data="data" class="data-table" stripe border>
        <el-table-column fixed type="selection" width="50" />
        <el-table-column align="center" fixed label="主键" prop="id" width="100" />
        <el-table-column align="center" label="参数名称" prop="configName" show-overflow-tooltip />
        <el-table-column align="center" label="参数键名" prop="configKey" show-overflow-tooltip />
        <el-table-column align="center" label="参数键值" prop="configValue" show-overflow-tooltip />
        <el-table-column align="center" fixed="right" label="操作" width="150">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button v-hasPerm="['system:sysConfig:edit']" icon="Edit" link type="primary" @click="handleUpdate(scope.row.id)" />
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button v-hasPerm="['system:sysConfig:remove']" icon="Delete" link type="primary" @click="handleDelete(scope.row.id)" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <pagination v-show="total || 0 > 0" v-model:limit="queryParams.size" v-model:page="queryParams.page" :total="total" @pagination="listData" />
      </template>
    </el-card>
    <!-- 添加或修改参数配置对话框 -->
    <el-dialog v-model="dialogVisible" append-to-body title="保存参数配置" width="500px" @closed="() => resetForm()">
      <el-form :model="form" :rules="rules" label-width="80px">
        <el-form-item label="参数名称" prop="configName">
          <el-input v-model="form.configName" clearable placeholder="请输入参数名称" />
        </el-form-item>
        <el-form-item label="参数键名" prop="configKey">
          <el-input v-model="form.configKey" clearable placeholder="请输入参数键名" />
        </el-form-item>
        <el-form-item label="参数键值" prop="configValue">
          <el-input v-model="form.configValue" :autosize="{ minRows: 2 }" clearable placeholder="请输入参数键值" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button :loading="submitting" type="primary" @click="submit">
            确定
          </el-button>
          <el-button @click="dialogVisible = false">
            取消
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
const rules = {
  configName: [{ required: true, message: '参数名称不能为空', trigger: 'blur' }],
  configKey: [{ required: true, message: '参数键名不能为空', trigger: 'blur' }],
  configValue: [{ required: true, message: '参数键值不能为空', trigger: 'blur' }],
}

const queryFormRef = ref()
const tableRef = ref()
const queryParams = ref<SysConfigPageQuery>({
  page: 1,
  size: 10,
})
const dialogVisible = ref(false)

const { data, send: listData, total, loading } = usePagination(() => sysConfigApi.page(queryParams.value))
const { loading: submitting, form, send: submit, reset: resetForm, updateForm } = useForm(
  formData => sysConfigApi.save(formData),
  {
    initialForm: sysConfigInitData,
    resetAfterSubmiting: true,
  },
).onComplete(() => dialogVisible.value = false).onSuccess(() => listData())

const { loading: templateLoading, send: downloadTemplate } = sysConfigApi.downloadTemplate()
const { loading: exportDataLoading, send: exportData } = sysConfigApi.exportData(queryParams.value)
const { uploading, file, appendFiles, removeFiles, upload } = useUploader(({ file }) => sysConfigApi.importData(file), { limit: 1 }).onComplete(() => removeFiles())
const progress = computed(() => file.value?.progress ? `${Math.floor(Number(file.value?.progress.uploaded || 0) / Number(file.value?.progress.total || 1) * 100)}%` : '')

function handleQuery() {
  queryParams.value.page = 1
  listData()
}

function resetQuery() {
  queryParams.value.page = 1
  queryFormRef.value?.resetFields()
  handleQuery()
}

function handleUpdate(id: number) {
  sysConfigApi.getById(id).then((res) => {
    updateForm(res)
    dialogVisible.value = true
  })
}

function handleDelete(id?: number) {
  const ids = [...tableRef.value?.getSelectionRows().map((row: { id: number }) => row.id), id].filter(id => id)
  if (ids.length > 0)
    modal.confirm(`是否确认删除参数配置编号为"${ids}"的数据项？`).then(() => sysConfigApi.remove(ids).then(() => listData()))
  else
    modal.msgWarning('未选择需要删除的数据')
}

async function handleUpload() {
  await appendFiles({ accept: '.xlsx', multiple: false })
  await upload()
}
</script>

<style lang="scss" scoped>

</style>
