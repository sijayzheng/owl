export const LogLoginApi = {
  // 分页查询登录日志列表
  page(data: LogLoginQuery) {
    return request.page<Result<Array<LogLogin>>>("/log/log-login/page", data)
  },
  // 查询登录日志列表
  list(data: LogLoginQuery) {
    return request.get<Array<LogLogin>>("/log/log-login/list", data)
  },
  // 根据ID查询登录日志详情
  getById(id: number) {
    return request.get<LogLogin>("/log/log-login/{id}")
  },
  // 新增登录日志
  add(data: LogLoginForm) {
    return request.post<boolean>("/log/log-login/add", data)
  },
  // 修改登录日志
  update(data: LogLoginForm) {
    return request.post<boolean>("/log/log-login/update", data)
  },
  // 删除登录日志
  remove(ids: number[]) {
    return request.post<boolean>("/log/log-login/remove", ids)
  },
  // 下载登录日志导入模板
  //template() {
  //  return request.get<>("/log/log-login/template")
  //},
  // 导入登录日志数据
  //importData() {
  //  return request.post<>("/log/log-login/import")
  //},
  // 导出登录日志数据
  //exportData(data: LogLoginQuery) {
  //  return request.get<>("/log/log-login/export", data)
  //},
}
