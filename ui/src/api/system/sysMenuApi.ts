export const sysMenuApi = {
  // 查询系统菜单树形结构数据
  getTree(data: SysMenuQuery) {
    return request.get<SysMenu[]>('/system/sysMenu/tree', data)
  },
  // 查询系统菜单列表
  list(data: SysMenuQuery) {
    return request.get<SysMenu[]>('/system/sysMenu/list', data)
  },
  // 根据ID查询系统菜单详情
  getById(id: number) {
    return request.get<SysMenu>(`/system/sysMenu/${id}`)
  },
  // 保存系统菜单
  save(data: SysMenuForm) {
    return request.post<boolean>('/system/sysMenu/save', data)
  },
  // 删除系统菜单
  remove(ids: number[]) {
    return request.post<boolean>('/system/sysMenu/remove', ids)
  },
  // 下载系统菜单导入模板
  downloadTemplate() {
    return request.download('/system/sysMenu/downloadTemplate')
  },
  // 导入系统菜单数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/system/sysMenu/import', formData)
  },
  // 导出系统菜单数据
  exportData(data: SysMenuQuery) {
    return request.download('/system/sysMenu/export', data)
  },
}
