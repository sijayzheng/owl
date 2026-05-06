export const SysDictTypeApi = {
  // 分页查询字典类型列表
  page(data: SysDictTypeQuery) {
    return request.page<Result<Array<SysDictType>>>("/system/sys-dict-type/page", data)
  },
  // 查询字典类型列表
  list(data: SysDictTypeQuery) {
    return request.get<Array<SysDictType>>("/system/sys-dict-type/list", data)
  },
  // 根据ID查询字典类型详情
  getById(id: number) {
    return request.get<SysDictType>("/system/sys-dict-type/{id}")
  },
  // 新增字典类型
  add(data: SysDictTypeForm) {
    return request.post<boolean>("/system/sys-dict-type/add", data)
  },
  // 修改字典类型
  update(data: SysDictTypeForm) {
    return request.post<boolean>("/system/sys-dict-type/update", data)
  },
  // 删除字典类型
  remove(ids: number[]) {
    return request.post<boolean>("/system/sys-dict-type/remove", ids)
  },
  // 下载字典类型导入模板
  //template() {
  //  return request.get<>("/system/sys-dict-type/template")
  //},
  // 导入字典类型数据
  //importData() {
  //  return request.post<>("/system/sys-dict-type/import")
  //},
  // 导出字典类型数据
  //exportData(data: SysDictTypeQuery) {
  //  return request.get<>("/system/sys-dict-type/export", data)
  //},
}
