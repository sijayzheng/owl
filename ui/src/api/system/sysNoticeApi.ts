export const SysNoticeApi = {
  // 分页查询通知公告列表
  page(data: SysNoticeQuery) {
    return request.page<Result<Array<SysNotice>>>("/system/sys-notice/page", data)
  },
  // 查询通知公告列表
  list(data: SysNoticeQuery) {
    return request.get<Array<SysNotice>>("/system/sys-notice/list", data)
  },
  // 根据ID查询通知公告详情
  getById(id: number) {
    return request.get<SysNotice>("/system/sys-notice/{id}")
  },
  // 新增通知公告
  add(data: SysNoticeForm) {
    return request.post<boolean>("/system/sys-notice/add", data)
  },
  // 修改通知公告
  update(data: SysNoticeForm) {
    return request.post<boolean>("/system/sys-notice/update", data)
  },
  // 删除通知公告
  remove(ids: number[]) {
    return request.post<boolean>("/system/sys-notice/remove", ids)
  },
  // 下载通知公告导入模板
  //template() {
  //  return request.get<>("/system/sys-notice/template")
  //},
  // 导入通知公告数据
  //importData() {
  //  return request.post<>("/system/sys-notice/import")
  //},
  // 导出通知公告数据
  //exportData(data: SysNoticeQuery) {
  //  return request.get<>("/system/sys-notice/export", data)
  //},
}
