export const ${functionName}Api = {
<#if isTree>
  // 查询${classComment}树形结构数据
  getTree(data: ${className}Query) {
    return request.get<${className}[]>('/${moduleName}/${functionName}/tree', data)
  },
<#else>
  // 分页查询${classComment}列表
  page(data: ${className}PageQuery) {
    return request.page<Result<${className}[]>>('/${moduleName}/${functionName}/page', data)
  },
</#if>
  // 查询${classComment}列表
  list(data: ${className}Query) {
    return request.get<${className}[]>('/${moduleName}/${functionName}/list', data)
  },
  // 根据ID查询${classComment}详情
  getById(id: number) {
    return request.get<${className}>(`/${moduleName}/${functionName}/${r'$'}{id}`)
  },
  // 保存${classComment}
  save(data: ${className}Form) {
    return request.post<boolean>('/${moduleName}/${functionName}/save', data)
  },
  // 删除${classComment}
  remove(ids: number[]) {
    return request.post<boolean>('/${moduleName}/${functionName}/remove', ids)
  },
  // 下载${classComment}导入模板
  downloadTemplate() {
    return request.download('/${moduleName}/${functionName}/downloadTemplate')
  },
  // 导入${classComment}数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/${moduleName}/${functionName}/import', formData)
  },
  // 导出${classComment}数据
  exportData(data: ${className}Query) {
    return request.download('/${moduleName}/${functionName}/export', data)
  },
}
