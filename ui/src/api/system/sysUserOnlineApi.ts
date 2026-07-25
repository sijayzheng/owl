export const sysUserOnlineApi = {
  // 分页查询在线用户列表
  page(data: SysUserOnlinePageQuery) {
    return request.page<Result<SysUserOnline[]>>('/system/sysUserOnline/page', data)
  },
  // 查询在线用户列表
  list(data: SysUserOnlineQuery) {
    return request.get<SysUserOnline[]>('/system/sysUserOnline/list', data)
  },
  // 根据ID查询在线用户详情
  getById(id: number) {
    return request.get<SysUserOnline>(`/system/sysUserOnline/${id}`)
  },
  // 保存在线用户
  save(data: SysUserOnlineForm) {
    return request.post<boolean>('/system/sysUserOnline/save', data)
  },
  // 删除在线用户
  remove(ids: number[]) {
    return request.post<boolean>('/system/sysUserOnline/remove', ids)
  },
  // 下载在线用户导入模板
  downloadTemplate() {
    return request.download('/system/sysUserOnline/downloadTemplate')
  },
  // 导入在线用户数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/system/sysUserOnline/import', formData)
  },
  // 导出在线用户数据
  exportData(data: SysUserOnlineQuery) {
    return request.download('/system/sysUserOnline/export', data)
  },
}
