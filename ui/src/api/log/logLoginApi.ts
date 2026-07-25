export const logLoginApi = {
  // 分页查询登录日志列表
  page(data: LogLoginPageQuery) {
    return request.page<Result<LogLogin[]>>('/log/logLogin/page', data)
  },
  // 查询登录日志列表
  list(data: LogLoginQuery) {
    return request.get<LogLogin[]>('/log/logLogin/list', data)
  },
  // 根据ID查询登录日志详情
  getById(id: number) {
    return request.get<LogLogin>(`/log/logLogin/${id}`)
  },
  // 保存登录日志
  save(data: LogLoginForm) {
    return request.post<boolean>('/log/logLogin/save', data)
  },
  // 删除登录日志
  remove(ids: number[]) {
    return request.post<boolean>('/log/logLogin/remove', ids)
  },
  // 下载登录日志导入模板
  downloadTemplate() {
    return request.download('/log/logLogin/downloadTemplate')
  },
  // 导入登录日志数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/log/logLogin/import', formData)
  },
  // 导出登录日志数据
  exportData(data: LogLoginQuery) {
    return request.download('/log/logLogin/export', data)
  },
}
