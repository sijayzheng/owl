export const sysMessageApi = {
  // 分页查询系统消息列表
  page(data: SysMessagePageQuery) {
    return request.page<Result<SysMessage[]>>('/system/sysMessage/page', data)
  },
  // 查询系统消息列表
  list(data: SysMessageQuery) {
    return request.get<SysMessage[]>('/system/sysMessage/list', data)
  },
  // 根据ID查询系统消息详情
  getById(id: number) {
    return request.get<SysMessage>(`/system/sysMessage/${id}`)
  },
  // 保存系统消息
  save(data: SysMessageForm) {
    return request.post<boolean>('/system/sysMessage/save', data)
  },
  // 删除系统消息
  remove(ids: number[]) {
    return request.post<boolean>('/system/sysMessage/remove', ids)
  },
  // 下载系统消息导入模板
  downloadTemplate() {
    return request.download('/system/sysMessage/downloadTemplate')
  },
  // 导入系统消息数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/system/sysMessage/import', formData)
  },
  // 导出系统消息数据
  exportData(data: SysMessageQuery) {
    return request.download('/system/sysMessage/export', data)
  },
}
