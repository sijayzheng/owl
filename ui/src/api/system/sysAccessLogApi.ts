export const sysAccessLogApi = {
  // 分页查询访问日志列表
  page(data: SysAccessLogPageQuery) {
    return request.page<Result<SysAccessLog[]>>('/system/sysAccessLog/page', data)
  },
  // 查询访问日志列表
  list(data: SysAccessLogQuery) {
    return request.get<SysAccessLog[]>('/system/sysAccessLog/list', data)
  },
  // 根据ID查询访问日志详情
  getById(id: number) {
    return request.get<SysAccessLog>(`/system/sysAccessLog/${id}`)
  },
  // 保存访问日志
  save(data: SysAccessLogForm) {
    return request.post<boolean>('/system/sysAccessLog/save', data)
  },
  // 删除访问日志
  remove(ids: number[]) {
    return request.post<boolean>('/system/sysAccessLog/remove', ids)
  },
  // 下载访问日志导入模板
  downloadTemplate() {
    return request.download('/system/sysAccessLog/downloadTemplate')
  },
  // 导入访问日志数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/system/sysAccessLog/import', formData)
  },
  // 导出访问日志数据
  exportData(data: SysAccessLogQuery) {
    return request.download('/system/sysAccessLog/export', data)
  },
}
