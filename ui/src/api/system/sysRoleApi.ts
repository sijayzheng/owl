export const sysRoleApi = {
  // 分页查询系统角色列表
  page(data: SysRolePageQuery) {
    return request.page<Result<SysRole[]>>('/system/sysRole/page', data)
  },
  // 查询系统角色列表
  list(data: SysRoleQuery) {
    return request.get<SysRole[]>('/system/sysRole/list', data)
  },
  // 根据ID查询系统角色详情
  getById(id: number) {
    return request.get<SysRole>(`/system/sysRole/${id}`)
  },
  // 保存系统角色
  save(data: SysRoleForm) {
    return request.post<boolean>('/system/sysRole/save', data)
  },
  // 删除系统角色
  remove(ids: number[]) {
    return request.post<boolean>('/system/sysRole/remove', ids)
  },
  // 下载系统角色导入模板
  downloadTemplate() {
    return request.download('/system/sysRole/downloadTemplate')
  },
  // 导入系统角色数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/system/sysRole/import', formData)
  },
  // 导出系统角色数据
  exportData(data: SysRoleQuery) {
    return request.download('/system/sysRole/export', data)
  },
}
