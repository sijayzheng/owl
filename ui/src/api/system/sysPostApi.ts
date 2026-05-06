export const SysPostApi = {
  // 分页查询系统岗位列表
  page(data: SysPostQuery) {
    return request.page<Result<Array<SysPost>>>("/system/sys-post/page", data)
  },
  // 查询系统岗位列表
  list(data: SysPostQuery) {
    return request.get<Array<SysPost>>("/system/sys-post/list", data)
  },
  // 根据ID查询系统岗位详情
  getById(id: number) {
    return request.get<SysPost>("/system/sys-post/{id}")
  },
  // 新增系统岗位
  add(data: SysPostForm) {
    return request.post<boolean>("/system/sys-post/add", data)
  },
  // 修改系统岗位
  update(data: SysPostForm) {
    return request.post<boolean>("/system/sys-post/update", data)
  },
  // 删除系统岗位
  remove(ids: number[]) {
    return request.post<boolean>("/system/sys-post/remove", ids)
  },
  // 下载系统岗位导入模板
  //template() {
  //  return request.get<>("/system/sys-post/template")
  //},
  // 导入系统岗位数据
  //importData() {
  //  return request.post<>("/system/sys-post/import")
  //},
  // 导出系统岗位数据
  //exportData(data: SysPostQuery) {
  //  return request.get<>("/system/sys-post/export", data)
  //},
}
