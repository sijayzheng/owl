export const ${className}Api = {
<#if isTree>
  // 查询${classComment}树形结构数据
  getTree() {
    return request.get<Array<${className}>>("/${moduleName}/${path}/tree")
  },
<#else>
  // 分页查询${classComment}列表
  page(data: ${className}Query) {
    return request.page<Result<Array<${className}>>>("/${moduleName}/${path}/page", data)
  },
</#if>
  // 查询${classComment}列表
  list(data: ${className}Query) {
    return request.get<Array<${className}>>("/${moduleName}/${path}/list", data)
  },
  // 根据ID查询${classComment}详情
  getById(id: number) {
    return request.get<${className}>("/${moduleName}/${path}/{id}")
  },
  // 新增${classComment}
  add(data: ${className}Form) {
    return request.post<boolean>("/${moduleName}/${path}/add", data)
  },
  // 修改${classComment}
  update(data: ${className}Form) {
    return request.post<boolean>("/${moduleName}/${path}/update", data)
  },
  // 删除${classComment}
  remove(ids: number[]) {
    return request.post<boolean>("/${moduleName}/${path}/remove", ids)
  },
  // 下载${classComment}导入模板
  //template() {
  //  return request.get<>("/${moduleName}/${path}/template")
  //},
  // 导入${classComment}数据
  //importData() {
  //  return request.post<>("/${moduleName}/${path}/import")
  //},
  // 导出${classComment}数据
  //exportData(data: ${className}Query) {
  //  return request.get<>("/${moduleName}/${path}/export", data)
  //},
}
