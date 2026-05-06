export const SysMenuApi = {
  // 查询系统菜单树形结构数据
  getTree() {
    return request.get<Array<SysMenu>>("/system/sys-menu/tree")
  },
  // 查询系统菜单列表
  list(data: SysMenuQuery) {
    return request.get<Array<SysMenu>>("/system/sys-menu/list", data)
  },
  // 根据ID查询系统菜单详情
  getById(id: number) {
    return request.get<SysMenu>("/system/sys-menu/{id}")
  },
  // 新增系统菜单
  add(data: SysMenuForm) {
    return request.post<boolean>("/system/sys-menu/add", data)
  },
  // 修改系统菜单
  update(data: SysMenuForm) {
    return request.post<boolean>("/system/sys-menu/update", data)
  },
  // 删除系统菜单
  remove(ids: number[]) {
    return request.post<boolean>("/system/sys-menu/remove", ids)
  },
  // 下载系统菜单导入模板
  //template() {
  //  return request.get<>("/system/sys-menu/template")
  //},
  // 导入系统菜单数据
  //importData() {
  //  return request.post<>("/system/sys-menu/import")
  //},
  // 导出系统菜单数据
  //exportData(data: SysMenuQuery) {
  //  return request.get<>("/system/sys-menu/export", data)
  //},
}
