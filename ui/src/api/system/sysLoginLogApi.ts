export const sysLoginLogApi = {
  // 分页查询登录日志列表
  page(data: SysLoginLogPageQuery) {
    return request.page<Result<SysLoginLog[]>>('/system/sysLoginLog/page', data)
  },
  // 查询登录日志列表
  list(data: SysLoginLogQuery) {
    return request.get<SysLoginLog[]>('/system/sysLoginLog/list', data)
  },
  // 根据ID查询登录日志详情
  getById(id: number) {
    return request.get<SysLoginLog>(`/system/sysLoginLog/${id}`)
  },
  // 保存登录日志
  save(data: SysLoginLogForm) {
    return request.post<boolean>('/system/sysLoginLog/save', data)
  },
  // 删除登录日志
  remove(ids: number[]) {
    return request.post<boolean>('/system/sysLoginLog/remove', ids)
  },
  // 下载登录日志导入模板
  downloadTemplate() {
    return request.download('/system/sysLoginLog/downloadTemplate')
  },
  // 导入登录日志数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/system/sysLoginLog/import', formData)
  },
  // 导出登录日志数据
  exportData(data: SysLoginLogQuery) {
    return request.download('/system/sysLoginLog/export', data)
  },
}
