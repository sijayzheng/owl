<template>
  <div>
    <el-card body-style="padding-bottom:8px" class="search-card" shadow="hover">
      <el-form ref="queryFormRef" :inline="true" :model="queryParams">
        <el-form-item label="用户账号" prop="username">
          <el-input v-model="queryParams.username" clearable placeholder="请输入用户账号" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="登录时间" prop="loginTimeRange">
          <el-date-picker
            v-model="queryParams.loginTimeRange" clearable format="YYYY-MM-DD hh:mm:ss" placeholder="请选择登录时间" type="datetime"
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
        <el-button v-hasPerm="['log:logLogin:export']" :loading="exportDataLoading" icon="Download" plain type="warning" @click="exportData">
          导出
        </el-button>
        <el-button v-hasPerm="['log:logLogin:export']" :loading="templateLoading" icon="Download" plain type="warning" @click="downloadTemplate">
          下载模板
        </el-button>
        <el-button v-hasPerm="['log:logLogin:export']" :loading="uploading" icon="Upload" plain type="warning" @click="handleUpload">
          导入{{ progress }}
        </el-button>
      </template>
      <el-table ref="tableRef" v-loading="loading" :data="data" class="data-table" stripe border>
        <el-table-column fixed type="selection" width="50" />
        <el-table-column align="center" fixed label="主键" prop="id" width="100" />
        <el-table-column align="center" label="用户id" prop="userId" show-overflow-tooltip />
        <el-table-column align="center" label="用户账号" prop="username" show-overflow-tooltip />
        <el-table-column align="center" label="登录ip地址" prop="loginIp" show-overflow-tooltip />
        <el-table-column align="center" label="登录地址" prop="location" show-overflow-tooltip />
        <el-table-column align="center" label="浏览器类型" prop="browser" show-overflow-tooltip />
        <el-table-column align="center" label="操作系统" prop="os" show-overflow-tooltip />
        <el-table-column align="center" label="登录状态" prop="succeeded" show-overflow-tooltip />
        <el-table-column align="center" label="提示消息" prop="message" show-overflow-tooltip />
        <el-table-column align="center" label="登录时间" prop="loginTime" show-overflow-tooltip />
      </el-table>
      <template #footer>
        <pagination v-show="total || 0 > 0" v-model:limit="queryParams.size" v-model:page="queryParams.page" :total="total" @pagination="listData" />
      </template>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
const queryFormRef = ref()
const queryParams = ref<LogLoginPageQuery>({
  page: 1,
  size: 10,
})

const { data, send: listData, total, loading } = usePagination(() => logLoginApi.page(queryParams.value))

const { loading: templateLoading, send: downloadTemplate } = logLoginApi.downloadTemplate()
const { loading: exportDataLoading, send: exportData } = logLoginApi.exportData(queryParams.value)
const { uploading, file, appendFiles, removeFiles, upload } = useUploader(({ file }) => logLoginApi.importData(file), { limit: 1 }).onComplete(() => removeFiles())
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
