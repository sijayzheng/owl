export const FileOssStorageApi = {
  // 分页查询OSS列表
  page(data: FileOssStorageQuery) {
    return request.page<Result<Array<FileOssStorage>>>("/file/file-oss-storage/page", data)
  },
  // 查询OSS列表
  list(data: FileOssStorageQuery) {
    return request.get<Array<FileOssStorage>>("/file/file-oss-storage/list", data)
  },
  // 根据ID查询OSS详情
  getById(id: number) {
    return request.get<FileOssStorage>("/file/file-oss-storage/{id}")
  },
  // 新增OSS
  add(data: FileOssStorageForm) {
    return request.post<boolean>("/file/file-oss-storage/add", data)
  },
  // 修改OSS
  update(data: FileOssStorageForm) {
    return request.post<boolean>("/file/file-oss-storage/update", data)
  },
  // 删除OSS
  remove(ids: number[]) {
    return request.post<boolean>("/file/file-oss-storage/remove", ids)
  },
  // 下载OSS导入模板
  //template() {
  //  return request.get<>("/file/file-oss-storage/template")
  //},
  // 导入OSS数据
  //importData() {
  //  return request.post<>("/file/file-oss-storage/import")
  //},
  // 导出OSS数据
  //exportData(data: FileOssStorageQuery) {
  //  return request.get<>("/file/file-oss-storage/export", data)
  //},
}
