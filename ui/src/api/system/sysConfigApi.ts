export const SysConfigApi = {
  // 分页查询参数配置列表
  page(data: SysConfigQuery) {
    return request.page<Result<Array<SysConfig>>>("/system/sys-config/page", data)
  },
  // 查询参数配置列表
  list(data: SysConfigQuery) {
    return request.get<Array<SysConfig>>("/system/sys-config/list", data)
  },
  // 根据ID查询参数配置详情
  getById(id: number) {
    return request.get<SysConfig>("/system/sys-config/{id}")
  },
  // 新增参数配置
  add(data: SysConfigForm) {
    return request.post<boolean>("/system/sys-config/add", data)
  },
  // 修改参数配置
  update(data: SysConfigForm) {
    return request.post<boolean>("/system/sys-config/update", data)
  },
  // 删除参数配置
  remove(ids: number[]) {
    return request.post<boolean>("/system/sys-config/remove", ids)
  },
  // 下载参数配置导入模板
  //template() {
  //  return request.get<>("/system/sys-config/template")
  //},
  // 导入参数配置数据
  //importData() {
  //  return request.post<>("/system/sys-config/import")
  //},
  // 导出参数配置数据
  //exportData(data: SysConfigQuery) {
  //  return request.get<>("/system/sys-config/export", data)
  //},
}
