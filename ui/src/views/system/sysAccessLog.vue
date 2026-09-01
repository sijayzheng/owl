<template>
  <div>
    <el-card body-style="padding-bottom:8px" class="search-card" shadow="hover">
      <el-form ref="queryFormRef" :inline="true" :model="queryParams">
        <el-form-item label="用户id" prop="userId">
          <el-select v-model="queryParams.userId" clearable placeholder="请选择用户id" @change="() => handleQuery()">
            <el-option v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="模块标题" prop="title">
          <el-input v-model="queryParams.title" clearable placeholder="请输入模块标题" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="业务类型" prop="operateType">
          <el-input v-model="queryParams.operateType" clearable placeholder="请输入业务类型" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="方法名称" prop="method">
          <el-input v-model="queryParams.method" clearable placeholder="请输入方法名称" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="请求方式" prop="requestMethod">
          <el-input v-model="queryParams.requestMethod" clearable placeholder="请输入请求方式" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="访问人员" prop="accessUsername">
          <el-input v-model="queryParams.accessUsername" clearable placeholder="请输入访问人员" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="请求url" prop="accessUrl">
          <el-input v-model="queryParams.accessUrl" clearable placeholder="请输入请求url" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="主机地址" prop="accessIp">
          <el-input v-model="queryParams.accessIp" clearable placeholder="请输入主机地址" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="访问地点" prop="accessLocation">
          <el-input v-model="queryParams.accessLocation" clearable placeholder="请输入访问地点" @keyup.enter="handleQuery" />
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
        <el-button v-hasPerm="['system:sysAccessLog:add']" icon="Plus" plain type="primary" @click="dialogVisible = true">
          新增
        </el-button>
        <el-button v-hasPerm="['system:sysAccessLog:remove']" icon="Delete" plain type="danger" @click="handleDelete()">
          删除
        </el-button>
        <el-button v-hasPerm="['system:sysAccessLog:export']" :loading="exportDataLoading" icon="Download" plain type="warning" @click="exportData">
          导出
        </el-button>
        <el-button v-hasPerm="['system:sysAccessLog:export']" :loading="templateLoading" icon="Download" plain type="warning" @click="downloadTemplate">
          下载模板
        </el-button>
        <el-button v-hasPerm="['system:sysAccessLog:export']" :loading="uploading" icon="Upload" plain type="warning" @click="handleUpload">
          导入{{ progress }}
        </el-button>
      </template>
      <el-table ref="tableRef" v-loading="loading" :data="data" class="data-table" stripe border>
        <el-table-column fixed type="selection" width="50" />
        <el-table-column align="center" fixed label="主键" prop="id" width="100" />
        <el-table-column align="center" label="用户id" prop="userId" show-overflow-tooltip />
        <el-table-column align="center" label="模块标题" prop="title" show-overflow-tooltip />
        <el-table-column align="center" label="业务类型" prop="operateType" show-overflow-tooltip />
        <el-table-column align="center" label="方法名称" prop="method" show-overflow-tooltip />
        <el-table-column align="center" label="请求方式" prop="requestMethod" show-overflow-tooltip />
        <el-table-column align="center" label="访问人员" prop="accessUsername" show-overflow-tooltip />
        <el-table-column align="center" label="请求url" prop="accessUrl" show-overflow-tooltip />
        <el-table-column align="center" label="主机地址" prop="accessIp" show-overflow-tooltip />
        <el-table-column align="center" label="访问地点" prop="accessLocation" show-overflow-tooltip />
        <el-table-column align="center" label="访问状态" prop="status" show-overflow-tooltip />
        <el-table-column align="center" label="访问时间" prop="accessTime" show-overflow-tooltip />
        <el-table-column align="center" label="消耗时间" prop="costTime" show-overflow-tooltip />
        <el-table-column align="center" fixed="right" label="操作" width="150">
          <template #default="{ row }">
            <el-tooltip content="修改" placement="top">
              <el-button v-hasPerm="['system:sysAccessLog:edit']" icon="Edit" link type="primary" @click="handleUpdate(row.id)" />
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button v-hasPerm="['system:sysAccessLog:remove']" icon="Delete" link type="primary" @click="handleDelete(row.id)" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <pagination v-show="total || 0 > 0" v-model:limit="queryParams.size" v-model:page="queryParams.page" :total="total" @pagination="listData" />
      </template>
    </el-card>
    <!-- 添加或修改访问日志对话框 -->
    <el-dialog v-model="dialogVisible" append-to-body title="保存访问日志" width="500px" @closed="() => resetForm()">
      <el-form :model="form" :rules="rules" label-width="80px">
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
const rules = {
}

const queryFormRef = ref()
const tableRef = ref()
const queryParams = ref<SysAccessLogPageQuery>({
  page: 1,
  size: 10,
})
const dialogVisible = ref(false)

const { data, send: listData, total, loading } = usePagination(() => sysAccessLogApi.page(queryParams.value))
const { loading: submitting, form, send: submit, reset: resetForm, updateForm } = useForm(
  formData => sysAccessLogApi.save(formData),
  {
    initialForm: sysAccessLogInitData,
    resetAfterSubmiting: true,
  },
).onComplete(() => dialogVisible.value = false).onSuccess(() => listData())

const { loading: templateLoading, send: downloadTemplate } = sysAccessLogApi.downloadTemplate()
const { loading: exportDataLoading, send: exportData } = sysAccessLogApi.exportData(queryParams.value)
const { uploading, file, appendFiles, removeFiles, upload } = useUploader(({ file }) => sysAccessLogApi.importData(file), { limit: 1 }).onComplete(() => removeFiles())
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
  sysAccessLogApi.getById(id).then((res) => {
    updateForm(res)
    dialogVisible.value = true
  })
}

function handleDelete(id?: number) {
  const ids = [...tableRef.value?.getSelectionRows().map((row: { id: number }) => row.id), id].filter(id => id)
  if (ids.length > 0)
    modal.confirm(`是否确认删除访问日志编号为"${ids}"的数据项？`).then(() => sysAccessLogApi.remove(ids).then(() => listData()))
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
