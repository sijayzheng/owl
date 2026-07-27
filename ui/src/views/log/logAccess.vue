<template>
  <div>
    <el-card body-style="padding-bottom:8px" class="search-card" shadow="hover">
      <el-form ref="queryFormRef" :inline="true" :model="queryParams">
        <el-form-item label="访问人员" prop="accessUsername">
          <el-input v-model="queryParams.accessUsername" clearable placeholder="请输入访问人员" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="请求url" prop="accessUrl">
          <el-input v-model="queryParams.accessUrl" clearable placeholder="请输入请求url" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="访问时间" prop="accessTimeRange">
          <el-date-picker v-model="queryParams.accessTimeRange" clearable format="YYYY-MM-DD hh:mm:ss" placeholder="请选择访问时间" type="datetime" value-format="YYYY-MM-DD hh:mm:ss"  @change="() => handleQuery()" />
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
        <el-button v-hasPerm="['log:logAccess:add']" icon="Plus" plain type="primary" @click="dialogVisible = true">
          新增
        </el-button>
        <el-button v-hasPerm="['log:logAccess:remove']" icon="Delete" plain type="danger" @click="handleDelete()">
          删除
        </el-button>
        <el-button v-hasPerm="['log:logAccess:export']" :loading="exportDataLoading" icon="Download" plain type="warning" @click="exportData">
          导出
        </el-button>
        <el-button v-hasPerm="['log:logAccess:export']" :loading="templateLoading" icon="Download" plain type="warning" @click="downloadTemplate">
          下载模板
        </el-button>
        <el-button v-hasPerm="['log:logAccess:export']" :loading="uploading" icon="Upload" plain type="warning" @click="handleUpload">
          导入{{ progress }}
        </el-button>
      </template>
      <DataTable ref="tableRef" :data="data" :loading="loading" :columns="columns">
        <template #action="{ row }">
          <el-tooltip content="修改" placement="top">
            <el-button v-hasPerm="['log:logAccess:edit']" icon="Edit" link type="primary" @click="handleUpdate(row.id)" />
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <el-button v-hasPerm="['log:logAccess:remove']" icon="Delete" link type="primary" @click="handleDelete(row.id)" />
          </el-tooltip>
        </template>
      </DataTable>
      <template #footer>
        <pagination v-show="total || 0 > 0" v-model:limit="queryParams.size" v-model:page="queryParams.page" :total="total" @pagination="listData" />
      </template>
    </el-card>
    <!-- 添加或修改访问日志对话框 -->
    <el-dialog v-model="dialogVisible" append-to-body title="保存访问日志" width="500px" @closed="() => resetForm()">
      <el-form :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户id" prop="userId">
          <el-select v-model="form.userId" clearable placeholder="请选择用户id">
            <el-option v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="模块标题" prop="title">
          <el-input v-model="form.title" clearable placeholder="请输入模块标题" />
        </el-form-item>
        <el-form-item label="业务类型" prop="operateType">
          <el-input v-model="form.operateType" clearable placeholder="请输入业务类型" />
        </el-form-item>
        <el-form-item label="方法名称" prop="method">
          <el-input v-model="form.method" clearable placeholder="请输入方法名称" />
        </el-form-item>
        <el-form-item label="请求方式" prop="requestMethod">
          <el-input v-model="form.requestMethod" clearable placeholder="请输入请求方式" />
        </el-form-item>
        <el-form-item label="访问人员" prop="accessUsername">
          <el-input v-model="form.accessUsername" clearable placeholder="请输入访问人员" />
        </el-form-item>
        <el-form-item label="请求url" prop="accessUrl">
          <el-input v-model="form.accessUrl" clearable placeholder="请输入请求url" />
        </el-form-item>
        <el-form-item label="主机地址" prop="accessIp">
          <el-input v-model="form.accessIp" clearable placeholder="请输入主机地址" />
        </el-form-item>
        <el-form-item label="访问地点" prop="accessLocation">
          <el-input v-model="form.accessLocation" clearable placeholder="请输入访问地点" />
        </el-form-item>
        <el-form-item label="请求参数" prop="accessParam">
          <el-input v-model="form.accessParam" :autosize="{ minRows: 2 }" clearable placeholder="请输入请求参数" type="textarea" />
        </el-form-item>
        <el-form-item label="返回参数" prop="jsonResult">
          <el-input v-model="form.jsonResult" :autosize="{ minRows: 2 }" clearable placeholder="请输入返回参数" type="textarea" />
        </el-form-item>
        <el-form-item label="访问状态" prop="status">
          <el-input-number v-model="form.status" controls-position="right" clearable placeholder="请输入访问状态" />
        </el-form-item>
        <el-form-item label="错误消息" prop="errorMsg">
          <el-input v-model="form.errorMsg" :autosize="{ minRows: 2 }" clearable placeholder="请输入错误消息" type="textarea" />
        </el-form-item>
        <el-form-item label="访问时间" prop="accessTime">
          <el-date-picker v-model="form.accessTime" clearable type="datetime" placeholder="请选择访问时间" format="YYYY-MM-DD hh:mm:ss" value-format="YYYY-MM-DD hh:mm:ss" />
        </el-form-item>
        <el-form-item label="消耗时间" prop="costTime">
          <el-input-number v-model="form.costTime" controls-position="right" clearable placeholder="请输入消耗时间" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button :loading="submitting" type="primary" @click="submit">
          确定
        </el-button>
        <el-button @click="dialogVisible = false">
          取消
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import DataTable from '@/components/DataTable.vue'

const columns = [
  { prop: 'userId', label: '用户id', showOverflowTooltip: true },
  { prop: 'title', label: '模块标题', showOverflowTooltip: true },
  { prop: 'operateType', label: '业务类型', showOverflowTooltip: true },
  { prop: 'method', label: '方法名称', showOverflowTooltip: true },
  { prop: 'requestMethod', label: '请求方式', showOverflowTooltip: true },
  { prop: 'accessUsername', label: '访问人员', showOverflowTooltip: true },
  { prop: 'accessUrl', label: '请求url', showOverflowTooltip: true },
  { prop: 'accessIp', label: '主机地址', showOverflowTooltip: true },
  { prop: 'accessLocation', label: '访问地点', showOverflowTooltip: true },
  { prop: 'status', label: '访问状态', showOverflowTooltip: true },
  { prop: 'accessTime', label: '访问时间', showOverflowTooltip: true },
  { prop: 'costTime', label: '消耗时间', showOverflowTooltip: true },
]

const rules = {
}

const queryFormRef = ref()
const tableRef = ref()
const queryParams = ref<LogAccessPageQuery>({
  page: 1,
  size: 10,
})
const dialogVisible = ref(false)

const { data, send: listData, total, loading } = usePagination(() => logAccessApi.page(queryParams.value))
const { loading: submitting, form, send: submit, reset: resetForm, updateForm } = useForm(
  formData => logAccessApi.save(formData),
  {
    initialForm: logAccessInitData,
    resetAfterSubmiting: true,
  },
).onComplete(() => dialogVisible.value = false).onSuccess(() => listData())

const { loading: templateLoading, send: downloadTemplate } = logAccessApi.downloadTemplate()
const { loading: exportDataLoading, send: exportData } = logAccessApi.exportData(queryParams.value)
const { uploading, file, appendFiles, removeFiles, upload } = useUploader(({ file }) => logAccessApi.importData(file), { limit: 1 }).onComplete(() => removeFiles())
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
  logAccessApi.getById(id).then((res) => {
    updateForm(res)
    dialogVisible.value = true
  })
}

function handleDelete(id?: number) {
  const ids = [...tableRef.value?.getSelectionRows().map((row: { id: number }) => row.id), id].filter(id => id)
  if (ids.length > 0)
    modal.confirm(`是否确认删除访问日志编号为"${ids}"的数据项？`).then(() => logAccessApi.remove(ids).then(() => listData()))
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
