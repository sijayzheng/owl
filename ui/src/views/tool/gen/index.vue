<template>
  <div>
    <el-card body-style="padding-bottom:8px" class="search-card" shadow="hover">
      <el-form ref="queryFormRef" :inline="true" :model="queryParams">
        <el-form-item label="物理表名" prop="tableName">
          <el-input v-model="queryParams.tableName" clearable placeholder="请输入物理表名" @keyup.enter="handleQuery" />
        </el-form-item>
        <el-form-item label="表注释" prop="tableComment">
          <el-input v-model="queryParams.tableComment" clearable placeholder="请输入表注释" @keyup.enter="handleQuery" />
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
        <el-button icon="Plus" plain type="primary" @click="openImportDialog">
          新增
        </el-button>
        <el-button icon="Delete" plain type="danger" @click="handleDelete()">
          删除
        </el-button>
      </template>
      <el-table ref="tableRef" v-loading="loading" :data="data" class="data-table" stripe border>
        <el-table-column fixed type="selection" width="50" />
        <el-table-column align="center" fixed label="主键" prop="id" width="100" />
        <el-table-column align="center" label="物理表名" prop="tableName" show-overflow-tooltip />
        <el-table-column align="center" label="表注释" prop="tableComment" show-overflow-tooltip />
        <el-table-column align="center" label="模块名" prop="moduleName" show-overflow-tooltip />
        <el-table-column align="center" label="实体类名" prop="className" show-overflow-tooltip />
        <el-table-column align="center" label="实体类注释" prop="classComment" show-overflow-tooltip />
        <el-table-column align="center" label="功能名" prop="functionName" show-overflow-tooltip />
        <el-table-column align="center" label="是否树表" prop="treeTable" show-overflow-tooltip>
          <template #default="{ row }">
            <yes-or-no-tag :value="row.treeTable" />
          </template>
        </el-table-column>
        <el-table-column align="center" label="仅生成实体类" prop="entityOnly" show-overflow-tooltip>
          <template #default="{ row }">
            <yes-or-no-tag :value="row.entityOnly" />
          </template>
        </el-table-column>
        <el-table-column align="center" fixed="right" label="操作" width="150">
          <template #default="{ row }">
            <el-tooltip content="修改" placement="top">
              <el-button icon="Edit" link type="primary" @click="handleUpdate(row.id)" />
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button icon="Delete" link type="primary" @click="handleDelete(row.id)" />
            </el-tooltip>
            <el-tooltip content="生成" placement="top">
              <el-button icon="Promotion" link type="primary" @click="handleGenerate(row.id)" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <pagination v-show="total || 0 > 0" v-model:limit="queryParams.size" v-model:page="queryParams.page" :total="total" @pagination="listData" />
      </template>
    </el-card>
    <el-dialog v-model="dialogVisible" append-to-body title="导入表结构">
      <el-card body-style="padding-bottom:8px" class="search-card" shadow="hover">
        <template #header>
          <el-form ref="tableQueryFormRef" :inline="true" :model="tableQueryParams">
            <el-form-item label="物理表名" prop="tableName">
              <el-input v-model="tableQueryParams.tableName" clearable placeholder="请输入物理表名" @keyup.enter="listDbTable" />
            </el-form-item>
            <el-form-item label="表注释" prop="tableComment">
              <el-input v-model="tableQueryParams.tableComment" clearable placeholder="请输入表注释" @keyup.enter="listDbTable" />
            </el-form-item>
            <el-form-item>
              <el-button icon="Search" type="primary" @click="listDbTable">
                搜索
              </el-button>
              <el-button icon="Refresh" @click="resetTableQuery">
                重置
              </el-button>
            </el-form-item>
          </el-form>
        </template>
        <el-table v-loading="tableLoading" :data="tables" stripe border max-height="360">
          <el-table-column type="index" align="center" fixed="left" label="序号" width="100" />
          <el-table-column align="center" label="物理表名" prop="tableName" show-overflow-tooltip />
          <el-table-column align="center" label="表注释" prop="tableComment" show-overflow-tooltip />
          <el-table-column align="center" fixed="right" label="操作" width="150">
            <template #default="{ row }">
              <el-button link type="primary" @click="importTable(row.tableName)">
                导入
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
      <template #footer>
        <el-button @click="dialogVisible = false">
          关闭
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import type { GenTablePageQuery } from '@/types/gen/GenTable'
import { genApi } from '@/api/gen/genApi'

const queryFormRef = ref()
const tableQueryFormRef = ref()
const tableRef = ref()
const queryParams = ref<GenTablePageQuery>({
  page: 1,
  size: 10,
})
const tableQueryParams = ref<GenTableQuery>({})
const dialogVisible = ref(false)

const { data, send: listData, total, loading } = usePagination(() => genApi.page(queryParams.value))

function handleQuery() {
  queryParams.value.page = 1
  listData()
}

function resetQuery() {
  queryFormRef.value?.resetFields()
  handleQuery()
}

function handleUpdate(id: number) {
  router.push(`/tool/genEdit/${id}`)
}

function handleDelete(id?: number) {
  const ids = [...tableRef.value?.getSelectionRows().map((row: { id: number }) => row.id), id].filter(id => id)
  if (ids.length > 0)
    modal.confirm(`是否确认删除主键为"${ids}"的数据项？`).then(() => genApi.remove(ids).then(() => listData()))
  else
    modal.msgWarning('未选择需要删除的数据')
}

function handleGenerate(id: number) {
  genApi.generateCode(id).then((response) => {

  })
}

const { data: tables, send: listDbTable, loading: tableLoading } = useRequest(() => genApi.listDbTable(tableQueryParams.value), { immediate: false })

function openImportDialog() {
  listDbTable()
  dialogVisible.value = true
}

function importTable(tableName: string) {
  genApi.importTable(tableName).then((res) => {
    listDbTable()
    listData()
  })
}

function resetTableQuery() {
  tableQueryFormRef?.value.resetFields()
  listDbTable()
}
</script>

<style lang="scss" scoped>

</style>
