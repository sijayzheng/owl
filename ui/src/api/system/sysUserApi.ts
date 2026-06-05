export const sysUserApi = {
  // 分页查询系统用户列表
  page(data: SysUserPageQuery) {
    return request.page<Result<SysUser[]>>('/system/sysUser/page', data)
  },
  // 查询系统用户列表
  list(data: SysUserQuery) {
    return request.get<SysUser[]>('/system/sysUser/list', data)
  },
  // 根据ID查询系统用户详情
  getById(id: number) {
    return request.get<SysUser>(`/system/sysUser/${id}`)
  },
  // 保存系统用户
  save(data: SysUserForm) {
    return request.post<boolean>('/system/sysUser/save', data)
  },
  // 删除系统用户
  remove(ids: number[]) {
    return request.post<boolean>('/system/sysUser/remove', ids)
  },
  // 下载系统用户导入模板
  downloadTemplate() {
    return request.download('/system/sysUser/downloadTemplate')
  },
  // 导入系统用户数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/system/sysUser/import', formData)
  },
  // 导出系统用户数据
  exportData(data: SysUserQuery) {
    return request.download('/system/sysUser/export', data)
  },
}
