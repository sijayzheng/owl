<template>
  <div>
    <el-card body-style="padding-bottom:8px" class="search-card" shadow="hover">
      <el-form ref="queryFormRef" :inline="true" :model="queryParams">
        <el-form-item label="消息标题" prop="messageTitle">
          <el-input v-model="queryParams.messageTitle" clearable placeholder="请输入消息标题" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="消息类型" prop="messageType">
          <el-input v-model="queryParams.messageType" clearable placeholder="请输入消息类型" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="已读" prop="hasRead">
          <el-radio-group v-model="queryParams.hasRead" @change="() => handleQuery()">
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
        <el-button v-hasPerm="['system:sysMessage:add']" icon="Plus" plain type="primary" @click="dialogVisible = true">
          新增
        </el-button>
        <el-button v-hasPerm="['system:sysMessage:remove']" icon="Delete" plain type="danger" @click="handleDelete()">
          删除
        </el-button>
        <el-button v-hasPerm="['system:sysMessage:export']" :loading="exportDataLoading" icon="Download" plain type="warning" @click="exportData">
          导出
        </el-button>
        <el-button v-hasPerm="['system:sysMessage:export']" :loading="templateLoading" icon="Download" plain type="warning" @click="downloadTemplate">
          下载模板
        </el-button>
        <el-button v-hasPerm="['system:sysMessage:export']" :loading="uploading" icon="Upload" plain type="warning" @click="handleUpload">
          导入{{ progress }}
        </el-button>
      </template>
      <el-table ref="tableRef" v-loading="loading" :data="data" class="data-table" stripe border>
        <el-table-column fixed type="selection" width="50" />
        <el-table-column align="center" fixed label="主键" prop="id" width="100" />
        <el-table-column align="center" label="消息标题" prop="messageTitle" show-overflow-tooltip />
        <el-table-column align="center" label="消息类型" prop="messageType" show-overflow-tooltip />
        <el-table-column align="center" label="发送者" prop="sender" show-overflow-tooltip />
        <el-table-column align="center" label="接收者" prop="recipient" show-overflow-tooltip />
        <el-table-column align="center" label="已读" prop="hasRead" show-overflow-tooltip />
        <el-table-column align="center" fixed="right" label="操作" width="150">
          <template #default="{ row }">
            <el-tooltip content="修改" placement="top">
              <el-button v-hasPerm="['system:sysMessage:edit']" icon="Edit" link type="primary" @click="handleUpdate(row.id)" />
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button v-hasPerm="['system:sysMessage:remove']" icon="Delete" link type="primary" @click="handleDelete(row.id)" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <pagination v-show="total || 0 > 0" v-model:limit="queryParams.size" v-model:page="queryParams.page" :total="total" @pagination="listData" />
      </template>
    </el-card>
    <!-- 添加或修改系统消息对话框 -->
    <el-dialog v-model="dialogVisible" append-to-body title="保存系统消息" width="500px" @closed="() => resetForm()">
      <el-form :model="form" :rules="rules" label-width="80px">
        <el-form-item label="消息标题" prop="messageTitle">
          <el-input v-model="form.messageTitle" clearable placeholder="请输入消息标题" />
        </el-form-item>
        <el-form-item label="消息内容" prop="messageContent">
          <el-input v-model="form.messageContent" :autosize="{ minRows: 2 }" clearable placeholder="请输入消息内容" type="textarea" />
        </el-form-item>
        <el-form-item label="消息类型" prop="messageType">
          <el-input v-model="form.messageType" clearable placeholder="请输入消息类型" />
        </el-form-item>
        <el-form-item label="发送者" prop="sender">
          <el-input-number v-model="form.sender" controls-position="right" clearable placeholder="请输入发送者" />
        </el-form-item>
        <el-form-item label="接收者" prop="recipient">
          <el-input-number v-model="form.recipient" controls-position="right" clearable placeholder="请输入接收者" />
        </el-form-item>
        <el-form-item label="已读" prop="hasRead">
          <el-radio-group v-model="form.hasRead">
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
  messageTitle: [{ required: true, message: '消息标题不能为空', trigger: 'blur' }],
  sender: [{ required: true, message: '发送者不能为空', trigger: 'blur' }],
  recipient: [{ required: true, message: '接收者不能为空', trigger: 'blur' }],
}

const queryFormRef = ref()
const tableRef = ref()
const queryParams = ref<SysMessagePageQuery>({
  page: 1,
  size: 10,
})
const dialogVisible = ref(false)

const { data, send: listData, total, loading } = usePagination(() => sysMessageApi.page(queryParams.value))
const { loading: submitting, form, send: submit, reset: resetForm, updateForm } = useForm(
  formData => sysMessageApi.save(formData),
  {
    initialForm: sysMessageInitData,
    resetAfterSubmiting: true,
  },
).onComplete(() => dialogVisible.value = false).onSuccess(() => listData())

const { loading: templateLoading, send: downloadTemplate } = sysMessageApi.downloadTemplate()
const { loading: exportDataLoading, send: exportData } = sysMessageApi.exportData(queryParams.value)
const { uploading, file, appendFiles, removeFiles, upload } = useUploader(({ file }) => sysMessageApi.importData(file), { limit: 1 }).onComplete(() => removeFiles())
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
  sysMessageApi.getById(id).then((res) => {
    updateForm(res)
    dialogVisible.value = true
  })
}

function handleDelete(id?: number) {
  const ids = [...tableRef.value?.getSelectionRows().map((row: { id: number }) => row.id), id].filter(id => id)
  if (ids.length > 0)
    modal.confirm(`是否确认删除系统消息编号为"${ids}"的数据项？`).then(() => sysMessageApi.remove(ids).then(() => listData()))
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
