<template>
  <el-form ref="formRef" :model="form" inline :rules="rules" label-width="100px">
    <el-card>
      <el-row :gutter="20">
        <el-col :span="4">
          <el-form-item label="物理表名" prop="tableName">
            <el-input v-model="form.tableName" readonly placeholder="请输入物理表名" />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="表注释" prop="tableComment">
            <el-input v-model="form.tableComment" clearable placeholder="请输入表注释" />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="模块名" prop="moduleName">
            <el-input v-model="form.moduleName" clearable placeholder="请输入模块名" />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="实体类名" prop="className">
            <el-input v-model="form.className" clearable placeholder="请输入实体类名" />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="实体类注释" prop="classComment">
            <el-input v-model="form.classComment" clearable placeholder="请输入实体类注释" />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="功能名" prop="functionName">
            <el-input v-model="form.functionName" clearable placeholder="请输入功能名" />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="是否树表" prop="treeTable">
            <YesOrNoRadio v-model="form.treeTable" />
          </el-form-item>
        </el-col>
        <el-col v-if="form.treeTable" :span="4">
          <el-form-item label="树编码字段" prop="treeKey">
            <el-input v-model="form.treeKey" clearable placeholder="请输入树编码字段" />
          </el-form-item>
        </el-col>
        <el-col v-if="form.treeTable" :span="4">
          <el-form-item label="树父编码字段" prop="treeParentKey">
            <el-input v-model="form.treeParentKey" clearable placeholder="请输入树父编码字段" />
          </el-form-item>
        </el-col>
        <el-col v-if="form.treeTable" :span="4">
          <el-form-item label="树名称字段" prop="treeLabel">
            <el-input v-model="form.treeLabel" clearable placeholder="请输入树名称字段" />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="所属菜单" prop="menuId">
            <single-tree-selector v-model="form.menuId" :data="menuTree" name="所属菜单" />
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="仅生成实体类" prop="entityOnly">
            <YesOrNoRadio v-model="form.entityOnly" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-table :data="form.columns" stripe border>
        <el-table-column prop="id" label="主键" width="80" />
        <el-table-column prop="columnName" label="列名">
          <template #default="{ row }">
            {{ row.columnName }}
            <el-tag v-if="row.primaryKey" type="primary">
              主键
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="columnComment" label="列注释" />
        <el-table-column prop="columnType" label="数据库类型" width="100" />
        <el-table-column prop="javaType" label="Java类型" width="155">
          <template #default="scope">
            <enum-selector v-model="scope.row.javaType" enum-type="JavaType" :min-width="130" name="Java类型" />
          </template>
        </el-table-column>
        <el-table-column prop="javaField" label="Java字段名">
          <template #default="scope">
            <el-input v-model="scope.row.javaField" clearable placeholder="请输入Java字段名" />
          </template>
        </el-table-column>
        <el-table-column prop="required" label="是否必填" width="120">
          <template #default="scope">
            <YesOrNoRadio v-model="scope.row.required" />
          </template>
        </el-table-column>
        <el-table-column prop="editable" label="是否编辑" width="120">
          <template #default="scope">
            <YesOrNoRadio v-model="scope.row.editable" />
          </template>
        </el-table-column>
        <el-table-column prop="visible" label="是否展示" width="120">
          <template #default="scope">
            <YesOrNoRadio v-model="scope.row.listable" />
          </template>
        </el-table-column>
        <el-table-column prop="queryable" label="是否查询" width="120">
          <template #default="scope">
            <YesOrNoRadio v-model="scope.row.queryable" />
          </template>
        </el-table-column>
        <el-table-column prop="queryType" label="查询方式" width="155">
          <template #default="scope">
            <enum-selector v-if="scope.row.queryable" v-model="scope.row.queryType" enum-type="QueryType" :min-width="130" name="查询方式" />
            <span v-else />
          </template>
        </el-table-column>
        <el-table-column prop="htmlType" label="显示类型" width="135">
          <template #default="scope">
            <enum-selector v-model="scope.row.htmlType" enum-type="HtmlType" name="显示类型" :min-width="110" />
          </template>
        </el-table-column>
        <el-table-column prop="dictType" label="字典类型">
          <template #default="scope">
            <el-input v-model="scope.row.dictType" clearable placeholder="请输入字典类型" />
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" width="110px">
          <template #default="scope">
            <el-input-number v-model="scope.row.sort" style="width: 80px" controls-position="right" clearable placeholder="请输入排序" />
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <div style="display: flex;justify-content: center;">
          <el-button :loading="submitting" type="primary" @click="submitForm">
            确定
          </el-button>
          <el-button @click="back">
            取消
          </el-button>
        </div>
      </template>
    </el-card>
  </el-form>
</template>

<script setup lang="ts">
import { genApi } from '@/api/gen/genApi'

const rules = reactive({
  configName: [{ required: true, message: '参数名称不能为空', trigger: 'blur' }],
  configKey: [{ required: true, message: '参数键名不能为空', trigger: 'blur' }],
  configValue: [{ required: true, message: '参数键值不能为空', trigger: 'blur' }],
})
const route = useRoute()
const formRef = ref()

const { loading: submitting, form, send: submit, updateForm } = useForm(
  formData => genApi.update(formData),
  {
    initialForm: {} as GenTable,
    resetAfterSubmiting: true,
  },
).onSuccess(() => back())

const { data: menuTree } = useRequest(() => sysMenuApi.getMenuSelect())

onMounted(() => {
  const tableId = route.params?.tableId
  genApi.getById(tableId).then((data) => {
    updateForm(data)
  })
})

function submitForm() {
  formRef.value.validate((valid) => {
    if (valid) {
      submit()
    }
  })
}

function back() {
  tab.closePage().then(() => {
    router.push(`/tool/gen`)
  })
}
</script>

<style scoped lang="scss">

</style>
