export const SysMessageApi = {
  // 分页查询系统消息列表
  page(data: SysMessageQuery) {
    return request.page<Result<Array<SysMessage>>>("/system/sys-message/page", data)
  },
  // 查询系统消息列表
  list(data: SysMessageQuery) {
    return request.get<Array<SysMessage>>("/system/sys-message/list", data)
  },
  // 根据ID查询系统消息详情
  getById(id: number) {
    return request.get<SysMessage>("/system/sys-message/{id}")
  },
  // 新增系统消息
  add(data: SysMessageForm) {
    return request.post<boolean>("/system/sys-message/add", data)
  },
  // 修改系统消息
  update(data: SysMessageForm) {
    return request.post<boolean>("/system/sys-message/update", data)
  },
  // 删除系统消息
  remove(ids: number[]) {
    return request.post<boolean>("/system/sys-message/remove", ids)
  },
  // 下载系统消息导入模板
  //template() {
  //  return request.get<>("/system/sys-message/template")
  //},
  // 导入系统消息数据
  //importData() {
  //  return request.post<>("/system/sys-message/import")
  //},
  // 导出系统消息数据
  //exportData(data: SysMessageQuery) {
  //  return request.get<>("/system/sys-message/export", data)
  //},
}
