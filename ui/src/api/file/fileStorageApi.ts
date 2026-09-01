export const fileStorageApi = {
  // 分页查询文件存储列表
  page(data: FileStoragePageQuery) {
    return request.page<Result<FileStorage[]>>('/file/fileStorage/page', data)
  },
  // 查询文件存储列表
  list(data: FileStorageQuery) {
    return request.get<FileStorage[]>('/file/fileStorage/list', data)
  },
  // 根据ID查询文件存储详情
  getById(id: number) {
    return request.get<FileStorage>(`/file/fileStorage/${id}`)
  },
  // 保存文件存储
  save(data: FileStorageForm) {
    return request.post<boolean>('/file/fileStorage/save', data)
  },
  // 删除文件存储
  remove(ids: number[]) {
    return request.post<boolean>('/file/fileStorage/remove', ids)
  },
  // 下载文件存储导入模板
  downloadTemplate() {
    return request.download('/file/fileStorage/downloadTemplate')
  },
  // 导入文件存储数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/file/fileStorage/import', formData)
  },
  // 导出文件存储数据
  exportData(data: FileStorageQuery) {
    return request.download('/file/fileStorage/export', data)
  },
}
