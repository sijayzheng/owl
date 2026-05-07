export const SysRoleApi = {
  // 分页查询系统角色列表
  page(data: SysRoleQuery) {
    return request.page<Result<Array<SysRole>>>("/system/sys-role/page", data)
  },
  // 查询系统角色列表
  list(data: SysRoleQuery) {
    return request.get<Array<SysRole>>("/system/sys-role/list", data)
  },
  // 根据ID查询系统角色详情
  getById(id: number) {
    return request.get<SysRole>("/system/sys-role/{id}")
  },
  // 新增系统角色
  add(data: SysRoleForm) {
    return request.post<boolean>("/system/sys-role/add", data)
  },
  // 修改系统角色
  update(data: SysRoleForm) {
    return request.post<boolean>("/system/sys-role/update", data)
  },
  // 删除系统角色
  remove(ids: number[]) {
    return request.post<boolean>("/system/sys-role/remove", ids)
  },
  // 下载系统角色导入模板
  //template() {
  //  return request.get<>("/system/sys-role/template")
  //},
  // 导入系统角色数据
  //importData() {
  //  return request.post<>("/system/sys-role/import")
  //},
  // 导出系统角色数据
  //exportData(data: SysRoleQuery) {
  //  return request.get<>("/system/sys-role/export", data)
  //},
}
