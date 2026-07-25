<template>
  <div>
    <el-card body-style="padding-bottom:8px" class="search-card" shadow="hover">
      <el-form ref="queryFormRef" :inline="true" :model="queryParams">
        <el-form-item label="用户账号" prop="username">
          <el-input v-model="queryParams.username" clearable placeholder="请输入用户账号" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="登录ip" prop="loginIp">
          <el-input v-model="queryParams.loginIp" clearable placeholder="请输入登录ip" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="登录地点" prop="loginLocation">
          <el-input v-model="queryParams.loginLocation" clearable placeholder="请输入登录地点" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="登录时间" prop="loginTimeRange">
          <el-date-picker v-model="queryParams.loginTimeRange" clearable format="YYYY-MM-DD hh:mm:ss" placeholder="请选择登录时间" type="datetime" value-format="YYYY-MM-DD hh:mm:ss"  @change="() => handleQuery()" />
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
        <el-button v-hasPerm="['system:sysUserOnline:add']" icon="Plus" plain type="primary" @click="dialogVisible = true">
          新增
        </el-button>
        <el-button v-hasPerm="['system:sysUserOnline:remove']" icon="Delete" plain type="danger" @click="handleDelete()">
          删除
        </el-button>
        <el-button v-hasPerm="['system:sysUserOnline:export']" :loading="exportDataLoading" icon="Download" plain type="warning" @click="exportData">
          导出
        </el-button>
        <el-button v-hasPerm="['system:sysUserOnline:export']" :loading="templateLoading" icon="Download" plain type="warning" @click="downloadTemplate">
          下载模板
        </el-button>
        <el-button v-hasPerm="['system:sysUserOnline:export']" :loading="uploading" icon="Upload" plain type="warning" @click="handleUpload">
          导入{{ progress }}
        </el-button>
      </template>
      <el-table ref="tableRef" v-loading="loading" :data="data" class="data-table" stripe border>
        <el-table-column fixed type="selection" width="50" />
        <el-table-column align="center" fixed label="主键" prop="id" width="100" />
        <el-table-column align="center" label="用户id" prop="userId" show-overflow-tooltip />
        <el-table-column align="center" label="用户账号" prop="username" show-overflow-tooltip />
        <el-table-column align="center" label="部门名称" prop="deptName" show-overflow-tooltip />
        <el-table-column align="center" label="登录ip" prop="loginIp" show-overflow-tooltip />
        <el-table-column align="center" label="登录地点" prop="loginLocation" show-overflow-tooltip />
        <el-table-column align="center" label="浏览器" prop="browser" show-overflow-tooltip />
        <el-table-column align="center" label="操作系统" prop="os" show-overflow-tooltip />
        <el-table-column align="center" label="登录时间" prop="loginTime" show-overflow-tooltip />
        <el-table-column align="center" label="最后访问时间" prop="lastAccessTime" show-overflow-tooltip />
        <el-table-column align="center" label="过期时间" prop="expireTime" show-overflow-tooltip />
        <el-table-column align="center" fixed="right" label="操作" width="150">
          <template #default="{ row }">
            <el-tooltip content="修改" placement="top">
              <el-button v-hasPerm="['system:sysUserOnline:edit']" icon="Edit" link type="primary" @click="handleUpdate(row.id)" />
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button v-hasPerm="['system:sysUserOnline:remove']" icon="Delete" link type="primary" @click="handleDelete(row.id)" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <pagination v-show="total || 0 > 0" v-model:limit="queryParams.size" v-model:page="queryParams.page" :total="total" @pagination="listData" />
      </template>
    </el-card>
    <!-- 添加或修改在线用户对话框 -->
    <el-dialog v-model="dialogVisible" append-to-body title="保存在线用户" width="500px" @closed="() => resetForm()">
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
  userId: [{ required: true, message: '用户id不能为空', trigger: 'blur' }],
  loginTime: [{ required: true, message: '登录时间不能为空', trigger: 'blur' }],
  lastAccessTime: [{ required: true, message: '最后访问时间不能为空', trigger: 'blur' }],
  expireTime: [{ required: true, message: '过期时间不能为空', trigger: 'blur' }],
}

const queryFormRef = ref()
const tableRef = ref()
const queryParams = ref<SysUserOnlinePageQuery>({
  page: 1,
  size: 10,
})
const dialogVisible = ref(false)

const { data, send: listData, total, loading } = usePagination(() => sysUserOnlineApi.page(queryParams.value))
const { loading: submitting, form, send: submit, reset: resetForm, updateForm } = useForm(
  formData => sysUserOnlineApi.save(formData),
  {
    initialForm: sysUserOnlineInitData,
    resetAfterSubmiting: true,
  },
).onComplete(() => dialogVisible.value = false).onSuccess(() => listData())

const { loading: templateLoading, send: downloadTemplate } = sysUserOnlineApi.downloadTemplate()
const { loading: exportDataLoading, send: exportData } = sysUserOnlineApi.exportData(queryParams.value)
const { uploading, file, appendFiles, removeFiles, upload } = useUploader(({ file }) => sysUserOnlineApi.importData(file), { limit: 1 }).onComplete(() => removeFiles())
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
  sysUserOnlineApi.getById(id).then((res) => {
    updateForm(res)
    dialogVisible.value = true
  })
}

function handleDelete(id?: number) {
  const ids = [...tableRef.value?.getSelectionRows().map((row: { id: number }) => row.id), id].filter(id => id)
  if (ids.length > 0)
    modal.confirm(`是否确认删除在线用户编号为"${ids}"的数据项？`).then(() => sysUserOnlineApi.remove(ids).then(() => listData()))
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
