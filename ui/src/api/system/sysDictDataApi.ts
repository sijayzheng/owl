export const sysDictDataApi = {
  // 分页查询字典数据列表
  page(data: SysDictDataPageQuery) {
    return request.page<Result<SysDictData[]>>('/system/sysDictData/page', data)
  },
  // 查询字典数据列表
  list(data: SysDictDataQuery) {
    return request.get<SysDictData[]>('/system/sysDictData/list', data)
  },
  // 根据ID查询字典数据详情
  getById(id: number) {
    return request.get<SysDictData>(`/system/sysDictData/${id}`)
  },
  // 保存字典数据
  save(data: SysDictDataForm) {
    return request.post<boolean>('/system/sysDictData/save', data)
  },
  // 删除字典数据
  remove(ids: number[]) {
    return request.post<boolean>('/system/sysDictData/remove', ids)
  },
  // 下载字典数据导入模板
  downloadTemplate() {
    return request.download('/system/sysDictData/downloadTemplate')
  },
  // 导入字典数据数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/system/sysDictData/import', formData)
  },
  // 导出字典数据数据
  exportData(data: SysDictDataQuery) {
    return request.download('/system/sysDictData/export', data)
  },
}
