export const FileStorageApi = {
  // 分页查询文件存储列表
  page(data: FileStorageQuery) {
    return request.page<Result<Array<FileStorage>>>("/file/file-storage/page", data)
  },
  // 查询文件存储列表
  list(data: FileStorageQuery) {
    return request.get<Array<FileStorage>>("/file/file-storage/list", data)
  },
  // 根据ID查询文件存储详情
  getById(id: number) {
    return request.get<FileStorage>("/file/file-storage/{id}")
  },
  // 新增文件存储
  add(data: FileStorageForm) {
    return request.post<boolean>("/file/file-storage/add", data)
  },
  // 修改文件存储
  update(data: FileStorageForm) {
    return request.post<boolean>("/file/file-storage/update", data)
  },
  // 删除文件存储
  remove(ids: number[]) {
    return request.post<boolean>("/file/file-storage/remove", ids)
  },
  // 下载文件存储导入模板
  //template() {
  //  return request.get<>("/file/file-storage/template")
  //},
  // 导入文件存储数据
  //importData() {
  //  return request.post<>("/file/file-storage/import")
  //},
  // 导出文件存储数据
  //exportData(data: FileStorageQuery) {
  //  return request.get<>("/file/file-storage/export", data)
  //},
}
