export const SysDictDataApi = {
  // 分页查询字典数据列表
  page(data: SysDictDataQuery) {
    return request.page<Result<Array<SysDictData>>>("/system/sys-dict-data/page", data)
  },
  // 查询字典数据列表
  list(data: SysDictDataQuery) {
    return request.get<Array<SysDictData>>("/system/sys-dict-data/list", data)
  },
  // 根据ID查询字典数据详情
  getById(id: number) {
    return request.get<SysDictData>("/system/sys-dict-data/{id}")
  },
  // 新增字典数据
  add(data: SysDictDataForm) {
    return request.post<boolean>("/system/sys-dict-data/add", data)
  },
  // 修改字典数据
  update(data: SysDictDataForm) {
    return request.post<boolean>("/system/sys-dict-data/update", data)
  },
  // 删除字典数据
  remove(ids: number[]) {
    return request.post<boolean>("/system/sys-dict-data/remove", ids)
  },
  // 下载字典数据导入模板
  //template() {
  //  return request.get<>("/system/sys-dict-data/template")
  //},
  // 导入字典数据数据
  //importData() {
  //  return request.post<>("/system/sys-dict-data/import")
  //},
  // 导出字典数据数据
  //exportData(data: SysDictDataQuery) {
  //  return request.get<>("/system/sys-dict-data/export", data)
  //},
}
