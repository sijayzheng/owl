<template>
  <div>
    <el-card body-style="padding-bottom:8px" class="search-card" shadow="hover">
      <el-form ref="queryFormRef" :inline="true" :model="queryParams" @submit.prevent>
        <el-form-item label="部门名称" prop="deptName">
          <el-input v-model="queryParams.deptName" clearable placeholder="请输入部门名称" @keyup.enter="listData" />
        </el-form-item>
        <el-form-item label="启用" prop="enabled">
          <el-radio-group v-model="queryParams.enabled" @change="() => listData()">
            <el-radio-button v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button icon="Search" type="primary" @click="listData">
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
        <el-button v-hasPerm="['system:sysDept:add']" icon="Plus" plain type="primary" @click="dialogVisible = true">
          新增
        </el-button>
        <el-button v-hasPerm="['system:sysDept:remove']" icon="Delete" plain type="danger" @click="handleDelete()">
          删除
        </el-button>
        <el-button v-hasPerm="['system:sysDept:export']" :loading="exportDataLoading" icon="Download" plain type="warning" @click="exportData">
          导出
        </el-button>
        <el-button v-hasPerm="['system:sysDept:export']" :loading="templateLoading" icon="Download" plain type="warning" @click="downloadTemplate">
          下载模板
        </el-button>
        <el-button v-hasPerm="['system:sysDept:export']" :loading="uploading" icon="Upload" plain type="warning" @click="handleUpload">
          导入{{ progress }}
        </el-button>
      </template>
      <DataTable ref="tableRef" :data="data" :loading="loading" :columns="columns" row-key="id">
        <template #action="{ row }">
          <el-tooltip content="修改" placement="top">
            <el-button v-hasPerm="['system:sysDept:edit']" icon="Edit" link type="primary" @click="handleUpdate(row.id)" />
          </el-tooltip>
          <el-tooltip v-if="!row.children" content="删除" placement="top">
            <el-button v-hasPerm="['system:sysDept:remove']" icon="Delete" link type="primary" @click="handleDelete(row.id)" />
          </el-tooltip>
        </template>
      </DataTable>
    </el-card>
    <!-- 添加或修改系统部门对话框 -->
    <el-dialog v-model="dialogVisible" append-to-body title="保存系统部门" width="500px" @closed="() => resetForm()">
      <el-form :model="form" :rules="rules" label-width="80px">
        <el-form-item label="父部门id" prop="parentId">
          <el-select v-model="form.parentId" clearable placeholder="请选择父部门id">
            <el-option v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="祖级列表" prop="ancestors">
          <el-input v-model="form.ancestors" :autosize="{ minRows: 2 }" clearable placeholder="请输入祖级列表" type="textarea" />
        </el-form-item>
        <el-form-item label="部门名称" prop="deptName">
          <el-input v-model="form.deptName" clearable placeholder="请输入部门名称" />
        </el-form-item>
        <el-form-item label="部门类别" prop="deptCategory">
          <el-input-number v-model="form.deptCategory" controls-position="right" clearable placeholder="请输入部门类别" />
        </el-form-item>
        <el-form-item label="显示顺序" prop="sort">
          <el-input-number v-model="form.sort" controls-position="right" clearable placeholder="请输入显示顺序" />
        </el-form-item>
        <el-form-item label="负责人" prop="leader">
          <el-input-number v-model="form.leader" controls-position="right" clearable placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" clearable placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" clearable placeholder="请输入邮箱" />
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
  { prop: 'deptName', label: '部门名称', showOverflowTooltip: true },
  { prop: 'deptCategory', label: '部门类别', showOverflowTooltip: true },
  { prop: 'sort', label: '显示顺序', showOverflowTooltip: true },
  { prop: 'leader', label: '负责人', showOverflowTooltip: true },
  { prop: 'phone', label: '联系电话', showOverflowTooltip: true },
  { prop: 'email', label: '邮箱', showOverflowTooltip: true },
  { prop: 'enabled', label: '启用', showOverflowTooltip: true },
]

const rules = {}

const queryFormRef = ref()
const tableRef = ref()
const queryParams = ref<SysDeptQuery>({})
const dialogVisible = ref(false)

const { data, send: listData, loading } = useRequest(() => sysDeptApi.getTree(queryParams.value))
const { loading: submitting, form, send: submit, reset: resetForm, updateForm } = useForm(
  formData => sysDeptApi.save(formData),
  {
    initialForm: sysDeptInitData,
    resetAfterSubmiting: true,
  },
).onComplete(() => dialogVisible.value = false).onSuccess(() => listData())

const { loading: templateLoading, send: downloadTemplate } = sysDeptApi.downloadTemplate()
const { loading: exportDataLoading, send: exportData } = sysDeptApi.exportData(queryParams.value)
const { uploading, file, appendFiles, removeFiles, upload } = useUploader(({ file }) => sysDeptApi.importData(file), { limit: 1 }).onComplete(() => removeFiles())
const progress = computed(() => file.value?.progress ? `${Math.floor(Number(file.value?.progress.uploaded || 0) / Number(file.value?.progress.total || 1) * 100)}%` : '')

function resetQuery() {
  queryFormRef.value?.resetFields()
  listData()
}

function handleUpdate(id: number) {
  sysDeptApi.getById(id).then((res) => {
    updateForm(res)
    dialogVisible.value = true
  })
}

function handleDelete(id?: number) {
  const ids = [...tableRef.value?.getSelectionRows().map((row: { id: number }) => row.id), id].filter(id => id)
  if (ids.length > 0)
    modal.confirm(`是否确认删除系统部门编号为"${ids}"的数据项？`).then(() => sysDeptApi.remove(ids).then(() => listData()))
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
