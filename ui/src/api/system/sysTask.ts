export const SysTaskApi = {
  // 分页查询任务配置列表
  page(data: SysTaskQuery) {
    return request.page<Result<Array<SysTask>>>("/system/sys-task/page", data)
  },
  // 查询任务配置列表
  list(data: SysTaskQuery) {
    return request.get<Array<SysTask>>("/system/sys-task/list", data)
  },
  // 根据ID查询任务配置详情
  getById(id: number) {
    return request.get<SysTask>("/system/sys-task/{id}")
  },
  // 新增任务配置
  add(data: SysTaskForm) {
    return request.post<boolean>("/system/sys-task/add", data)
  },
  // 修改任务配置
  update(data: SysTaskForm) {
    return request.post<boolean>("/system/sys-task/update", data)
  },
  // 删除任务配置
  remove(ids: number[]) {
    return request.post<boolean>("/system/sys-task/remove", ids)
  },
  // 下载任务配置导入模板
  //template() {
  //  return request.get<>("/system/sys-task/template")
  //},
  // 导入任务配置数据
  //importData() {
  //  return request.post<>("/system/sys-task/import")
  //},
  // 导出任务配置数据
  //exportData(data: SysTaskQuery) {
  //  return request.get<>("/system/sys-task/export", data)
  //},
}
