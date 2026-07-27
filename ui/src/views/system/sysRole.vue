<template>
  <div>
    <el-card body-style="padding-bottom:8px" class="search-card" shadow="hover">
      <el-form ref="queryFormRef" :inline="true" :model="queryParams">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="queryParams.roleName" clearable placeholder="请输入角色名称" @keyup.enter="handleQuery" />
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
        <el-button v-hasPerm="['system:sysRole:add']" icon="Plus" plain type="primary" @click="dialogVisible = true">
          新增
        </el-button>
        <el-button v-hasPerm="['system:sysRole:remove']" icon="Delete" plain type="danger" @click="handleDelete()">
          删除
        </el-button>
        <el-button v-hasPerm="['system:sysRole:export']" :loading="exportDataLoading" icon="Download" plain type="warning" @click="exportData">
          导出
        </el-button>
        <el-button v-hasPerm="['system:sysRole:export']" :loading="templateLoading" icon="Download" plain type="warning" @click="downloadTemplate">
          下载模板
        </el-button>
        <el-button v-hasPerm="['system:sysRole:export']" :loading="uploading" icon="Upload" plain type="warning" @click="handleUpload">
          导入{{ progress }}
        </el-button>
      </template>
      <DataTable ref="tableRef" :data="data" :loading="loading" :columns="columns">
        <template #action="{ row }">
          <el-tooltip content="修改" placement="top">
            <el-button v-hasPerm="['system:sysRole:edit']" icon="Edit" link type="primary" @click="handleUpdate(row.id)" />
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <el-button v-hasPerm="['system:sysRole:remove']" icon="Delete" link type="primary" @click="handleDelete(row.id)" />
          </el-tooltip>
        </template>
      </DataTable>
      <template #footer>
        <pagination v-show="total || 0 > 0" v-model:limit="queryParams.size" v-model:page="queryParams.page" :total="total" @pagination="listData" />
      </template>
    </el-card>
    <!-- 添加或修改系统角色对话框 -->
    <el-dialog v-model="dialogVisible" append-to-body title="保存系统角色" width="500px" @closed="() => resetForm()">
      <el-form :model="form" :rules="rules" label-width="80px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" clearable placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色权限字符串" prop="roleCode">
          <el-input v-model="form.roleCode" clearable placeholder="请输入角色权限字符串" />
        </el-form-item>
        <el-form-item label="显示顺序" prop="sort">
          <el-input-number v-model="form.sort" controls-position="right" clearable placeholder="请输入显示顺序" />
        </el-form-item>
        <el-form-item label="数据权限" prop="dataScope">
          <el-input v-model="form.dataScope" clearable placeholder="请输入数据权限" />
        </el-form-item>
        <el-form-item label="菜单树选择项关联显示" prop="menuCheckStrictly">
          <el-radio-group v-model="form.menuCheckStrictly">
            <el-radio-button v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="部门树选择项关联显示" prop="deptCheckStrictly">
          <el-radio-group v-model="form.deptCheckStrictly">
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
import DataTable from '@/components/DataTable.vue'

const columns = [
  { prop: 'roleName', label: '角色名称', showOverflowTooltip: true },
  { prop: 'roleCode', label: '角色权限字符串', showOverflowTooltip: true },
  { prop: 'sort', label: '显示顺序', showOverflowTooltip: true },
  { prop: 'dataScope', label: '数据权限', showOverflowTooltip: true },
  { prop: 'menuCheckStrictly', label: '菜单树选择项关联显示', showOverflowTooltip: true },
  { prop: 'deptCheckStrictly', label: '部门树选择项关联显示', showOverflowTooltip: true },
  { prop: 'enabled', label: '启用', showOverflowTooltip: true },
]

const rules = {
  roleName: [{ required: true, message: '角色名称不能为空', trigger: 'blur' }],
  roleCode: [{ required: true, message: '角色权限字符串不能为空', trigger: 'blur' }],
  sort: [{ required: true, message: '显示顺序不能为空', trigger: 'blur' }],
}

const queryFormRef = ref()
const tableRef = ref()
const queryParams = ref<SysRolePageQuery>({
  page: 1,
  size: 10,
})
const dialogVisible = ref(false)

const { data, send: listData, total, loading } = usePagination(() => sysRoleApi.page(queryParams.value))
const { loading: submitting, form, send: submit, reset: resetForm, updateForm } = useForm(
  formData => sysRoleApi.save(formData),
  {
    initialForm: sysRoleInitData,
    resetAfterSubmiting: true,
  },
).onComplete(() => dialogVisible.value = false).onSuccess(() => listData())

const { loading: templateLoading, send: downloadTemplate } = sysRoleApi.downloadTemplate()
const { loading: exportDataLoading, send: exportData } = sysRoleApi.exportData(queryParams.value)
const { uploading, file, appendFiles, removeFiles, upload } = useUploader(({ file }) => sysRoleApi.importData(file), { limit: 1 }).onComplete(() => removeFiles())
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
  sysRoleApi.getById(id).then((res) => {
    updateForm(res)
    dialogVisible.value = true
  })
}

function handleDelete(id?: number) {
  const ids = [...tableRef.value?.getSelectionRows().map((row: { id: number }) => row.id), id].filter(id => id)
  if (ids.length > 0)
    modal.confirm(`是否确认删除系统角色编号为"${ids}"的数据项？`).then(() => sysRoleApi.remove(ids).then(() => listData()))
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
