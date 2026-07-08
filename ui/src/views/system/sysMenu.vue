<template>
  <div>
    <el-card body-style="padding-bottom:8px" class="search-card" shadow="hover">
      <el-form ref="queryFormRef" :inline="true" :model="queryParams" @submit.prevent>
        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="queryParams.menuName" clearable placeholder="请输入菜单名称" @keyup.enter="listData" />
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
        <el-button v-hasPerm="['system:sysMenu:add']" icon="Plus" plain type="primary" @click="dialogVisible = true">
          新增
        </el-button>
        <el-button v-hasPerm="['system:sysMenu:remove']" icon="Delete" plain type="danger" @click="handleDelete()">
          删除
        </el-button>
        <el-button v-hasPerm="['system:sysMenu:export']" :loading="exportDataLoading" icon="Download" plain type="warning" @click="exportData">
          导出
        </el-button>
        <el-button v-hasPerm="['system:sysMenu:export']" :loading="templateLoading" icon="Download" plain type="warning" @click="downloadTemplate">
          下载模板
        </el-button>
        <el-button v-hasPerm="['system:sysMenu:export']" :loading="uploading" icon="Upload" plain type="warning" @click="handleUpload">
          导入{{ progress }}
        </el-button>
      </template>
      <el-table ref="tableRef" v-loading="loading" :data="data" class="data-table" row-key="id" stripe border>
        <el-table-column fixed type="selection" width="50" />
        <el-table-column align="center" fixed label="主键" prop="id" width="100" />
        <el-table-column align="center" label="菜单名称" prop="menuName" show-overflow-tooltip />
        <el-table-column align="center" label="父菜单id" prop="parentId" show-overflow-tooltip />
        <el-table-column align="center" label="显示顺序" prop="sort" show-overflow-tooltip />
        <el-table-column align="center" label="路由地址" prop="path" show-overflow-tooltip />
        <el-table-column align="center" label="组件路径" prop="component" show-overflow-tooltip />
        <el-table-column align="center" label="路由参数" prop="queryParam" show-overflow-tooltip />
        <el-table-column align="center" label="是否为外链" prop="foreignLink" show-overflow-tooltip />
        <el-table-column align="center" label="是否缓存" prop="cached" show-overflow-tooltip />
        <el-table-column align="center" label="菜单类型" prop="menuType" show-overflow-tooltip />
        <el-table-column align="center" label="显示" prop="visible" show-overflow-tooltip />
        <el-table-column align="center" label="启用" prop="enabled" show-overflow-tooltip />
        <el-table-column align="center" label="权限标识" prop="perms" show-overflow-tooltip />
        <el-table-column align="center" label="菜单图标" prop="icon" show-overflow-tooltip />
        <el-table-column align="center" label="高亮菜单" prop="activeMenu" show-overflow-tooltip />
        <el-table-column align="center" fixed="right" label="操作" width="150">
          <template #default="{ row }">
            <el-tooltip content="修改" placement="top">
              <el-button v-hasPerm="['system:sysMenu:edit']" icon="Edit" link type="primary" @click="handleUpdate(row.id)" />
            </el-tooltip>
            <el-tooltip v-if="!row.children" content="删除" placement="top">
              <el-button v-hasPerm="['system:sysMenu:remove']" icon="Delete" link type="primary" @click="handleDelete(row.id)" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 添加或修改系统菜单对话框 -->
    <el-dialog v-model="dialogVisible" append-to-body title="保存系统菜单" width="500px" @closed="() => resetForm()">
      <el-form :model="form" :rules="rules" label-width="80px">
        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="form.menuName" clearable placeholder="请输入菜单名称" />
        </el-form-item>
        <el-form-item label="父菜单id" prop="parentId">
          <el-select v-model="form.parentId" clearable placeholder="请选择父菜单id">
            <el-option v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="显示顺序" prop="sort">
          <el-input-number v-model="form.sort" controls-position="right" clearable placeholder="请输入显示顺序" />
        </el-form-item>
        <el-form-item label="路由地址" prop="path">
          <el-input v-model="form.path" clearable placeholder="请输入路由地址" />
        </el-form-item>
        <el-form-item label="组件路径" prop="component">
          <el-input v-model="form.component" clearable placeholder="请输入组件路径" />
        </el-form-item>
        <el-form-item label="路由参数" prop="queryParam">
          <el-input v-model="form.queryParam" clearable placeholder="请输入路由参数" />
        </el-form-item>
        <el-form-item label="是否为外链" prop="foreignLink">
          <el-radio-group v-model="form.foreignLink">
            <el-radio-button v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否缓存" prop="cached">
          <el-radio-group v-model="form.cached">
            <el-radio-button v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单类型" prop="menuType">
          <el-input v-model="form.menuType" clearable placeholder="请输入菜单类型" />
        </el-form-item>
        <el-form-item label="显示" prop="visible">
          <el-radio-group v-model="form.visible">
            <el-radio-button v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="启用" prop="enabled">
          <el-radio-group v-model="form.enabled">
            <el-radio-button v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="权限标识" prop="perms">
          <el-input v-model="form.perms" clearable placeholder="请输入权限标识" />
        </el-form-item>
        <el-form-item label="菜单图标" prop="icon">
          <el-input v-model="form.icon" clearable placeholder="请输入菜单图标" />
        </el-form-item>
        <el-form-item label="高亮菜单" prop="activeMenu">
          <el-input v-model="form.activeMenu" clearable placeholder="请输入高亮菜单" />
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
  menuName: [{ required: true, message: '菜单名称不能为空', trigger: 'blur' }],
}

const queryFormRef = ref()
const tableRef = ref()
const queryParams = ref<SysDeptQuery>({})
const dialogVisible = ref(false)

const { data, send: listData, loading } = useRequest(() => sysMenuApi.getTree(queryParams.value))
const { loading: submitting, form, send: submit, reset: resetForm, updateForm } = useForm(
  formData => sysMenuApi.save(formData),
  {
    initialForm: sysMenuInitData,
    resetAfterSubmiting: true,
  },
).onComplete(() => dialogVisible.value = false).onSuccess(() => listData())

const { loading: templateLoading, send: downloadTemplate } = sysMenuApi.downloadTemplate()
const { loading: exportDataLoading, send: exportData } = sysMenuApi.exportData(queryParams.value)
const { uploading, file, appendFiles, removeFiles, upload } = useUploader(({ file }) => sysMenuApi.importData(file), { limit: 1 }).onComplete(() => removeFiles())
const progress = computed(() => file.value?.progress ? `${Math.floor(Number(file.value?.progress.uploaded || 0) / Number(file.value?.progress.total || 1) * 100)}%` : '')

function resetQuery() {
  queryFormRef.value?.resetFields()
  listData()
}

function handleUpdate(id: number) {
  sysMenuApi.getById(id).then((res) => {
    updateForm(res)
    dialogVisible.value = true
  })
}

function handleDelete(id?: number) {
  const ids = [...tableRef.value?.getSelectionRows().map((row: { id: number }) => row.id), id].filter(id => id)
  if (ids.length > 0)
    modal.confirm(`是否确认删除系统菜单编号为"${ids}"的数据项？`).then(() => sysMenuApi.remove(ids).then(() => listData()))
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
