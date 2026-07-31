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
          <el-date-picker
            v-model="queryParams.accessTimeRange" clearable format="YYYY-MM-DD hh:mm:ss" placeholder="请选择访问时间" type="datetime"
            value-format="YYYY-MM-DD hh:mm:ss" @change="() => handleQuery()"
          />
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
      </el-table>
      <template #footer>
        <pagination v-show="total || 0 > 0" v-model:limit="queryParams.size" v-model:page="queryParams.page" :total="total" @pagination="listData" />
      </template>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
const queryFormRef = ref()
const queryParams = ref<LogAccessPageQuery>({
  page: 1,
  size: 10,
})

const { data, send: listData, total, loading } = usePagination(() => logAccessApi.page(queryParams.value))

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

async function handleUpload() {
  await appendFiles({ accept: '.xlsx', multiple: false })
  await upload()
}
</script>

<style lang="scss" scoped>

</style>
