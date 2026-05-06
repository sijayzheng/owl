export const LogTaskApi = {
  // 分页查询任务日志列表
  page(data: LogTaskQuery) {
    return request.page<Result<Array<LogTask>>>("/log/log-task/page", data)
  },
  // 查询任务日志列表
  list(data: LogTaskQuery) {
    return request.get<Array<LogTask>>("/log/log-task/list", data)
  },
  // 根据ID查询任务日志详情
  getById(id: number) {
    return request.get<LogTask>("/log/log-task/{id}")
  },
  // 新增任务日志
  add(data: LogTaskForm) {
    return request.post<boolean>("/log/log-task/add", data)
  },
  // 修改任务日志
  update(data: LogTaskForm) {
    return request.post<boolean>("/log/log-task/update", data)
  },
  // 删除任务日志
  remove(ids: number[]) {
    return request.post<boolean>("/log/log-task/remove", ids)
  },
  // 下载任务日志导入模板
  //template() {
  //  return request.get<>("/log/log-task/template")
  //},
  // 导入任务日志数据
  //importData() {
  //  return request.post<>("/log/log-task/import")
  //},
  // 导出任务日志数据
  //exportData(data: LogTaskQuery) {
  //  return request.get<>("/log/log-task/export", data)
  //},
}
