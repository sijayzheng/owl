export const SysUserApi = {
  // 分页查询系统用户列表
  page(data: SysUserQuery) {
    return request.page<Result<Array<SysUser>>>("/system/sys-user/page", data)
  },
  // 查询系统用户列表
  list(data: SysUserQuery) {
    return request.get<Array<SysUser>>("/system/sys-user/list", data)
  },
  // 根据ID查询系统用户详情
  getById(id: number) {
    return request.get<SysUser>("/system/sys-user/{id}")
  },
  // 新增系统用户
  add(data: SysUserForm) {
    return request.post<boolean>("/system/sys-user/add", data)
  },
  // 修改系统用户
  update(data: SysUserForm) {
    return request.post<boolean>("/system/sys-user/update", data)
  },
  // 删除系统用户
  remove(ids: number[]) {
    return request.post<boolean>("/system/sys-user/remove", ids)
  },
  // 下载系统用户导入模板
  //template() {
  //  return request.get<>("/system/sys-user/template")
  //},
  // 导入系统用户数据
  //importData() {
  //  return request.post<>("/system/sys-user/import")
  //},
  // 导出系统用户数据
  //exportData(data: SysUserQuery) {
  //  return request.get<>("/system/sys-user/export", data)
  //},
}
