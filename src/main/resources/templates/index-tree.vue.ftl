<template>
  <div>
    <el-card body-style="padding-bottom:8px" class="search-card" shadow="hover">
      <el-form ref="queryFormRef" :inline="true" :model="queryParams" @submit.prevent>
<#list columns?filter(item -> item.queryable) as column>
  <#if column.queryType=='BETWEEN'>
    <#if column.htmlType=='DATETIME'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}Range">
          <el-date-picker v-model="queryParams.${column.javaField}Range" clearable format="YYYY-MM-DD hh:mm:ss" placeholder="请选择${column.columnComment}" type="datetime" value-format="YYYY-MM-DD hh:mm:ss"  @change="() => listData()" />
        </el-form-item>
    <#elseif column.htmlType=='DATE'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}Range">
          <el-date-picker v-model="queryParams.${column.javaField}Range" clearable placeholder="请输入${column.columnComment}" type="date" @change="() => listData()" />
        </el-form-item>
    <#elseif column.htmlType=='TIME'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}Range">
          <el-time-picker v-model="queryParams.${column.javaField}Range" clearable placeholder="请输入${column.columnComment}" @change="() => listData()" />
        </el-form-item>
    </#if>
  <#elseif column.queryType=='IN'>
    <#if column.htmlType=='SELECT'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}s">
          <el-select v-model="queryParams.${column.javaField}s" clearable placeholder="请选择${column.columnComment}" @change="() => listData()">
            <el-option v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
    <#elseif column.htmlType=='DATETIME'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}s">
          <el-date-picker v-model="queryParams.${column.javaField}s" clearable format="YYYY-MM-DD hh:mm:ss" placeholder="请选择${column.columnComment}" type="datetime" value-format="YYYY-MM-DD hh:mm:ss"  @change="() => listData()" />
        </el-form-item>
    <#elseif column.htmlType=='DATE'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}s">
          <el-date-picker v-model="queryParams.${column.javaField}s" clearable placeholder="请输入${column.columnComment}" type="date" @change="() => listData()" />
        </el-form-item>
    <#elseif column.htmlType=='TIME'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}s">
          <el-time-picker v-model="queryParams.${column.javaField}s" clearable placeholder="请输入${column.columnComment}" @change="() => listData()" />
        </el-form-item>
    </#if>
  <#else>
        <#if column.htmlType=='INPUT'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-input v-model="queryParams.${column.javaField}" clearable placeholder="请输入${column.columnComment}" @keyup.enter="listData" />
        </el-form-item>
    <#elseif column.htmlType=='SELECT'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-select v-model="queryParams.${column.javaField}" clearable placeholder="请选择${column.columnComment}" @change="() => listData()">
            <el-option v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
    <#elseif column.htmlType=='RADIO'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-radio-group v-model="queryParams.${column.javaField}" @change="() => listData()">
            <el-radio-button v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-radio-group>
        </el-form-item>
    <#elseif column.htmlType=='DATETIME'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-date-picker v-model="queryParams.${column.javaField}" clearable format="YYYY-MM-DD hh:mm:ss" placeholder="请选择${column.columnComment}" type="datetime" value-format="YYYY-MM-DD hh:mm:ss"  @change="() => listData()" />
        </el-form-item>
    <#elseif column.htmlType=='DATE'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-date-picker v-model="queryParams.${column.javaField}" clearable placeholder="请输入${column.columnComment}" type="date" @change="() => listData()" />
        </el-form-item>
    <#elseif column.htmlType=='TIME'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-time-picker v-model="queryParams.${column.javaField}" clearable placeholder="请输入${column.columnComment}" @change="() => listData()" />
        </el-form-item>
    </#if>
  </#if>
</#list>
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
        <el-button v-hasPerm="['${moduleName}:${functionName}:add']" icon="Plus" plain type="primary" @click="dialogVisible = true">
          新增
        </el-button>
        <el-button v-hasPerm="['${moduleName}:${functionName}:remove']" icon="Delete" plain type="danger" @click="handleDelete()">
          删除
        </el-button>
        <el-button v-hasPerm="['${moduleName}:${functionName}:export']" :loading="exportDataLoading" icon="Download" plain type="warning" @click="exportData">
          导出
        </el-button>
        <el-button v-hasPerm="['${moduleName}:${functionName}:export']" :loading="templateLoading" icon="Download" plain type="warning" @click="downloadTemplate">
          下载模板
        </el-button>
        <el-button v-hasPerm="['${moduleName}:${functionName}:export']" :loading="uploading" icon="Upload" plain type="warning" @click="handleUpload">
          导入{{ progress }}
        </el-button>
      </template>
      <el-table ref="tableRef" v-loading="loading" :data="data" class="data-table" row-key="id" stripe border>
        <el-table-column fixed type="selection" width="50" />
        <el-table-column align="center" fixed label="${primaryKey.columnComment}" prop="${primaryKey.javaField}" width="100" />
<#list columns?filter(item -> !item.based(hasBase)&&item.visible&&!item.primaryKey) as column>
        <el-table-column align="center" label="${column.columnComment}" prop="${column.javaField}" show-overflow-tooltip />
</#list>
        <el-table-column align="center" fixed="right" label="操作" width="150">
          <template #default="{ row }">
            <el-tooltip content="修改" placement="top">
              <el-button v-hasPerm="['${moduleName}:${functionName}:edit']" icon="Edit" link type="primary" @click="handleUpdate(row.id)" />
            </el-tooltip>
            <el-tooltip v-if="!row.children" content="删除" placement="top">
              <el-button v-hasPerm="['${moduleName}:${functionName}:remove']" icon="Delete" link type="primary" @click="handleDelete(row.id)" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 添加或修改${classComment}对话框 -->
    <el-dialog v-model="dialogVisible" append-to-body title="保存${classComment}" width="500px" @closed="() => resetForm()">
      <el-form :model="form" :rules="rules" label-width="80px">
<#list columns?filter(item -> item.editable&&!item.primaryKey) as column>
    <#if column.htmlType=='INPUT'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-input v-model="form.${column.javaField}" clearable placeholder="请输入${column.columnComment}" />
        </el-form-item>
    <#elseif column.htmlType=='TEXTAREA'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-input v-model="form.${column.javaField}" :autosize="{ minRows: 2 }" clearable placeholder="请输入${column.columnComment}" type="textarea" />
        </el-form-item>
    <#elseif column.htmlType=='NUMBER'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-input-number v-model="form.${column.javaField}" controls-position="right" clearable placeholder="请输入${column.columnComment}" />
        </el-form-item>
    <#elseif column.htmlType=='SELECT'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-select v-model="form.${column.javaField}" clearable placeholder="请选择${column.columnComment}">
            <el-option v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
    <#elseif column.htmlType=='RADIO'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-radio-group v-model="form.${column.javaField}">
            <el-radio-button v-for="item in emptySelectArray" :key="item.value" :label="item.label" :value="item.value" />
          </el-radio-group>
        </el-form-item>
    <#elseif column.htmlType=='DATETIME'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-date-picker v-model="form.${column.javaField}" clearable type="datetime" placeholder="请选择${column.columnComment}" format="YYYY-MM-DD hh:mm:ss" value-format="YYYY-MM-DD hh:mm:ss" />
        </el-form-item>
    <#elseif column.htmlType=='DATE'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-date-picker v-model="form.${column.javaField}" clearable type="date" placeholder="请输入${column.columnComment}" />
        </el-form-item>
    <#elseif column.htmlType=='TIME'>
        <el-form-item label="${column.columnComment}" prop="${column.javaField}">
          <el-time-picker v-model="form.${column.javaField}" clearable placeholder="请输入${column.columnComment}" />
        </el-form-item>
    </#if>
</#list>
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
<#list columns?filter(item -> item.required&&!item.primaryKey) as column>
  ${column.javaField}: [{ required: true, message: '${column.columnComment}不能为空', trigger: 'blur' }],
</#list>
}

const queryFormRef = ref()
const tableRef = ref()
const queryParams = ref<${className}Query>({})
const dialogVisible = ref(false)

const { data, send: listData, loading } = useRequest(() => ${functionName}Api.getTree(queryParams.value))
const { loading: submitting, form, send: submit, reset: resetForm, updateForm } = useForm(
  formData => ${functionName}Api.save(formData),
  {
    initialForm: ${functionName}InitData,
    resetAfterSubmiting: true,
  },
).onComplete(() => dialogVisible.value = false).onSuccess(() => listData())

const { loading: templateLoading, send: downloadTemplate } = ${functionName}Api.downloadTemplate()
const { loading: exportDataLoading, send: exportData } = ${functionName}Api.exportData(queryParams.value)
const { uploading, file, appendFiles, removeFiles, upload } = useUploader(({ file }) => ${functionName}Api.importData(file), { limit: 1 }).onComplete(() => removeFiles())
const progress = computed(() => file.value?.progress ? `${r'$'}{Math.floor(Number(file.value?.progress.uploaded || 0) / Number(file.value?.progress.total || 1) * 100)}%` : '')

function resetQuery() {
  queryFormRef.value?.resetFields()
  listData()
}

function handleUpdate(id: number) {
  ${functionName}Api.getById(id).then((res) => {
    updateForm(res)
    dialogVisible.value = true
  })
}

function handleDelete(id?: number) {
  const ids = [...tableRef.value?.getSelectionRows().map((row: { id: number }) => row.id), id].filter(id => id)
  if (ids.length > 0)
    modal.confirm(`是否确认删除${classComment}编号为"${r'$'}{ids}"的数据项？`).then(() => ${functionName}Api.remove(ids).then(() => listData()))
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
