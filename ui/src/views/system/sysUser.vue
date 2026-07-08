<template>
  <div>
    <el-card body-style="padding-bottom:8px" class="search-card" shadow="hover">
      <el-form ref="queryFormRef" :inline="true" :model="queryParams">
        <el-form-item label="部门id" prop="deptId">
          <el-select v-model="queryParams.deptId" clearable placeholder="请选择部门id" @change="() => handleQuery()">
            <el-option v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="用户账号" prop="username">
          <el-input v-model="queryParams.username" clearable placeholder="请输入用户账号" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="用户姓名" prop="realName">
          <el-input v-model="queryParams.realName" clearable placeholder="请输入用户姓名" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="queryParams.email" clearable placeholder="请输入邮箱" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="queryParams.phone" clearable placeholder="请输入手机号" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="启用" prop="enabled">
          <el-radio-group v-model="queryParams.enabled" @change="() => handleQuery()">
            <el-radio-button v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-radio-group>
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
        <el-button v-hasPerm="['system:sysUser:add']" icon="Plus" plain type="primary" @click="dialogVisible = true">
          新增
        </el-button>
        <el-button v-hasPerm="['system:sysUser:remove']" icon="Delete" plain type="danger" @click="handleDelete()">
          删除
        </el-button>
        <el-button v-hasPerm="['system:sysUser:export']" :loading="exportDataLoading" icon="Download" plain type="warning" @click="exportData">
          导出
        </el-button>
        <el-button v-hasPerm="['system:sysUser:export']" :loading="templateLoading" icon="Download" plain type="warning" @click="downloadTemplate">
          下载模板
        </el-button>
        <el-button v-hasPerm="['system:sysUser:export']" :loading="uploading" icon="Upload" plain type="warning" @click="handleUpload">
          导入{{ progress }}
        </el-button>
      </template>
      <el-table ref="tableRef" v-loading="loading" :data="data" class="data-table" stripe border>
        <el-table-column fixed type="selection" width="50" />
        <el-table-column align="center" fixed label="主键" prop="id" width="100" />
        <el-table-column align="center" label="部门id" prop="deptId" show-overflow-tooltip />
        <el-table-column align="center" label="用户账号" prop="username" show-overflow-tooltip />
        <el-table-column align="center" label="用户姓名" prop="realName" show-overflow-tooltip />
        <el-table-column align="center" label="邮箱" prop="email" show-overflow-tooltip />
        <el-table-column align="center" label="手机号" prop="phone" show-overflow-tooltip />
        <el-table-column align="center" label="性别" prop="gender" show-overflow-tooltip />
        <el-table-column align="center" label="头像" prop="avatar" show-overflow-tooltip />
        <el-table-column align="center" label="是否启用MFA" prop="mfaEnabled" show-overflow-tooltip />
        <el-table-column align="center" label="启用" prop="enabled" show-overflow-tooltip />
        <el-table-column align="center" fixed="right" label="操作" width="150">
          <template #default="{ row }">
            <el-tooltip content="修改" placement="top">
              <el-button v-hasPerm="['system:sysUser:edit']" icon="Edit" link type="primary" @click="handleUpdate(row.id)" />
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button v-hasPerm="['system:sysUser:remove']" icon="Delete" link type="primary" @click="handleDelete(row.id)" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <pagination v-show="total || 0 > 0" v-model:limit="queryParams.size" v-model:page="queryParams.page" :total="total" @pagination="listData" />
      </template>
    </el-card>
    <!-- 添加或修改系统用户对话框 -->
    <el-dialog v-model="dialogVisible" append-to-body title="保存系统用户" width="500px" @closed="() => resetForm()">
      <el-form :model="form" :rules="rules" label-width="80px">
        <el-form-item label="部门id" prop="deptId">
          <el-select v-model="form.deptId" clearable placeholder="请选择部门id">
            <el-option v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="用户账号" prop="username">
          <el-input v-model="form.username" clearable placeholder="请输入用户账号" />
        </el-form-item>
        <el-form-item label="用户姓名" prop="realName">
          <el-input v-model="form.realName" clearable placeholder="请输入用户姓名" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" clearable placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" clearable placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-input v-model="form.gender" clearable placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="是否启用MFA" prop="mfaEnabled">
          <el-radio-group v-model="form.mfaEnabled">
            <el-radio-button v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="启用" prop="enabled">
          <el-radio-group v-model="form.enabled">
            <el-radio-button v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-radio-group>
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
const rules = {
  username: [{ required: true, message: '用户账号不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
}

const queryFormRef = ref()
const tableRef = ref()
const queryParams = ref<SysUserPageQuery>({
  page: 1,
  size: 10,
})
const dialogVisible = ref(false)

const { data, send: listData, total, loading } = usePagination(() => sysUserApi.page(queryParams.value))
const { loading: submitting, form, send: submit, reset: resetForm, updateForm } = useForm(
  formData => sysUserApi.save(formData),
  {
    initialForm: sysUserInitData,
    resetAfterSubmiting: true,
  },
).onComplete(() => dialogVisible.value = false).onSuccess(() => listData())

const { loading: templateLoading, send: downloadTemplate } = sysUserApi.downloadTemplate()
const { loading: exportDataLoading, send: exportData } = sysUserApi.exportData(queryParams.value)
const { uploading, file, appendFiles, removeFiles, upload } = useUploader(({ file }) => sysUserApi.importData(file), { limit: 1 }).onComplete(() => removeFiles())
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
  sysUserApi.getById(id).then((res) => {
    updateForm(res)
    dialogVisible.value = true
  })
}

function handleDelete(id?: number) {
  const ids = [...tableRef.value?.getSelectionRows().map((row: { id: number }) => row.id), id].filter(id => id)
  if (ids.length > 0)
    modal.confirm(`是否确认删除系统用户编号为"${ids}"的数据项？`).then(() => sysUserApi.remove(ids).then(() => listData()))
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
