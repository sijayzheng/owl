export const sysDeptApi = {
  // 查询系统部门树形结构数据
  getTree(data: SysDeptQuery) {
    return request.get<SysDept[]>('/system/sysDept/tree', data)
  },
  // 查询系统部门列表
  list(data: SysDeptQuery) {
    return request.get<SysDept[]>('/system/sysDept/list', data)
  },
  // 根据ID查询系统部门详情
  getById(id: number) {
    return request.get<SysDept>(`/system/sysDept/${id}`)
  },
  // 保存系统部门
  save(data: SysDeptForm) {
    return request.post<boolean>('/system/sysDept/save', data)
  },
  // 删除系统部门
  remove(ids: number[]) {
    return request.post<boolean>('/system/sysDept/remove', ids)
  },
  // 下载系统部门导入模板
  downloadTemplate() {
    return request.download('/system/sysDept/downloadTemplate')
  },
  // 导入系统部门数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/system/sysDept/import', formData)
  },
  // 导出系统部门数据
  exportData(data: SysDeptQuery) {
    return request.download('/system/sysDept/export', data)
  },
}
