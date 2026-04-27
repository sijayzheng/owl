export const sysUserApi = {
  // 分页查询系统用户列表
  page(data: SysUserQuery) {
    return request.page<Result<Array<SysUser>>>('/system/sys_user/page')
  },
  // 查询系统用户列表
  list(data: SysUserQuery) {
    return request.get<Array<SysUser>>('/system/sys_user/list')
  },
  // 根据ID查询系统用户详情
  getById(id: number) {
    return request.get<SysUser>('/system/sys_user/{id}')
  },
  // 新增系统用户
  add(data: SysUserForm) {
    return request.post<boolean>('/system/sys_user/add')
  },
  // 修改系统用户
  update(data: SysUserForm) {
    return request.post<boolean>('/system/sys_user/update')
  },
  // 删除系统用户
  remove(ids: number[]) {
    return request.post<boolean>('/system/sys_user/remove', [...ids])
  },
  // 下载系统用户导入模板
  // template() {
  //   return request.get<>('/system/sys_user/template')
  // },
  // 导入系统用户数据
  // importData() {
  //   return request.post<>('/system/sys_user/import')
  // },
  // 导出系统用户数据
  // exportData(data: SysUserQuery) {
  //   return request.get<>('/system/sys_user/export')
  // },
}
