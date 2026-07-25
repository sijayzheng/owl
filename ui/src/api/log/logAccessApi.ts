export const logAccessApi = {
  // 分页查询访问日志列表
  page(data: LogAccessPageQuery) {
    return request.page<Result<LogAccess[]>>('/log/logAccess/page', data)
  },
  // 查询访问日志列表
  list(data: LogAccessQuery) {
    return request.get<LogAccess[]>('/log/logAccess/list', data)
  },
  // 根据ID查询访问日志详情
  getById(id: number) {
    return request.get<LogAccess>(`/log/logAccess/${id}`)
  },
  // 保存访问日志
  save(data: LogAccessForm) {
    return request.post<boolean>('/log/logAccess/save', data)
  },
  // 删除访问日志
  remove(ids: number[]) {
    return request.post<boolean>('/log/logAccess/remove', ids)
  },
  // 下载访问日志导入模板
  downloadTemplate() {
    return request.download('/log/logAccess/downloadTemplate')
  },
  // 导入访问日志数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/log/logAccess/import', formData)
  },
  // 导出访问日志数据
  exportData(data: LogAccessQuery) {
    return request.download('/log/logAccess/export', data)
  },
}
