export const sysConfigApi = {
  // 分页查询参数配置列表
  page(data: SysConfigPageQuery) {
    return request.page<Result<SysConfig[]>>('/system/sysConfig/page', data)
  },
  // 查询参数配置列表
  list(data: SysConfigQuery) {
    return request.get<SysConfig[]>('/system/sysConfig/list', data)
  },
  // 根据ID查询参数配置详情
  getById(id: number) {
    return request.get<SysConfig>(`/system/sysConfig/${id}`)
  },
  // 保存参数配置
  save(data: SysConfigForm) {
    return request.post<boolean>('/system/sysConfig/save', data)
  },
  // 删除参数配置
  remove(ids: number[]) {
    return request.post<boolean>('/system/sysConfig/remove', ids)
  },
  // 下载参数配置导入模板
  downloadTemplate() {
    return request.download('/system/sysConfig/downloadTemplate')
  },
  // 导入参数配置数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/system/sysConfig/import', formData)
  },
  // 导出参数配置数据
  exportData(data: SysConfigQuery) {
    return request.download('/system/sysConfig/export', data)
  },
}
