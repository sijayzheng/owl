export const LogAccessApi = {
  // 分页查询访问日志列表
  page(data: LogAccessQuery) {
    return request.page<Result<Array<LogAccess>>>("/log/log-access/page", data)
  },
  // 查询访问日志列表
  list(data: LogAccessQuery) {
    return request.get<Array<LogAccess>>("/log/log-access/list", data)
  },
  // 根据ID查询访问日志详情
  getById(id: number) {
    return request.get<LogAccess>("/log/log-access/{id}")
  },
  // 新增访问日志
  add(data: LogAccessForm) {
    return request.post<boolean>("/log/log-access/add", data)
  },
  // 修改访问日志
  update(data: LogAccessForm) {
    return request.post<boolean>("/log/log-access/update", data)
  },
  // 删除访问日志
  remove(ids: number[]) {
    return request.post<boolean>("/log/log-access/remove", ids)
  },
  // 下载访问日志导入模板
  //template() {
  //  return request.get<>("/log/log-access/template")
  //},
  // 导入访问日志数据
  //importData() {
  //  return request.post<>("/log/log-access/import")
  //},
  // 导出访问日志数据
  //exportData(data: LogAccessQuery) {
  //  return request.get<>("/log/log-access/export", data)
  //},
}
