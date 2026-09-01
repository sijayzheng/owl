export const sysNoticeApi = {
  // 分页查询通知公告列表
  page(data: SysNoticePageQuery) {
    return request.page<Result<SysNotice[]>>('/system/sysNotice/page', data)
  },
  // 查询通知公告列表
  list(data: SysNoticeQuery) {
    return request.get<SysNotice[]>('/system/sysNotice/list', data)
  },
  // 根据ID查询通知公告详情
  getById(id: number) {
    return request.get<SysNotice>(`/system/sysNotice/${id}`)
  },
  // 保存通知公告
  save(data: SysNoticeForm) {
    return request.post<boolean>('/system/sysNotice/save', data)
  },
  // 删除通知公告
  remove(ids: number[]) {
    return request.post<boolean>('/system/sysNotice/remove', ids)
  },
  // 下载通知公告导入模板
  downloadTemplate() {
    return request.download('/system/sysNotice/downloadTemplate')
  },
  // 导入通知公告数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/system/sysNotice/import', formData)
  },
  // 导出通知公告数据
  exportData(data: SysNoticeQuery) {
    return request.download('/system/sysNotice/export', data)
  },
}
