export const sysDictTypeApi = {
  // 分页查询字典类型列表
  page(data: SysDictTypePageQuery) {
    return request.page<Result<SysDictType[]>>('/system/sysDictType/page', data)
  },
  // 查询字典类型列表
  list(data: SysDictTypeQuery) {
    return request.get<SysDictType[]>('/system/sysDictType/list', data)
  },
  // 根据ID查询字典类型详情
  getById(id: number) {
    return request.get<SysDictType>(`/system/sysDictType/${id}`)
  },
  // 保存字典类型
  save(data: SysDictTypeForm) {
    return request.post<boolean>('/system/sysDictType/save', data)
  },
  // 删除字典类型
  remove(ids: number[]) {
    return request.post<boolean>('/system/sysDictType/remove', ids)
  },
  // 下载字典类型导入模板
  downloadTemplate() {
    return request.download('/system/sysDictType/downloadTemplate')
  },
  // 导入字典类型数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/system/sysDictType/import', formData)
  },
  // 导出字典类型数据
  exportData(data: SysDictTypeQuery) {
    return request.download('/system/sysDictType/export', data)
  },
}
