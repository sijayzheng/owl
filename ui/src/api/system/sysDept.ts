export const SysDeptApi = {
  // 查询系统部门树形结构数据
  getTree() {
    return request.get<Array<SysDept>>("/system/sys-dept/tree")
  },
  // 查询系统部门列表
  list(data: SysDeptQuery) {
    return request.get<Array<SysDept>>("/system/sys-dept/list", data)
  },
  // 根据ID查询系统部门详情
  getById(id: number) {
    return request.get<SysDept>("/system/sys-dept/{id}")
  },
  // 新增系统部门
  add(data: SysDeptForm) {
    return request.post<boolean>("/system/sys-dept/add", data)
  },
  // 修改系统部门
  update(data: SysDeptForm) {
    return request.post<boolean>("/system/sys-dept/update", data)
  },
  // 删除系统部门
  remove(ids: number[]) {
    return request.post<boolean>("/system/sys-dept/remove", ids)
  },
  // 下载系统部门导入模板
  //template() {
  //  return request.get<>("/system/sys-dept/template")
  //},
  // 导入系统部门数据
  //importData() {
  //  return request.post<>("/system/sys-dept/import")
  //},
  // 导出系统部门数据
  //exportData(data: SysDeptQuery) {
  //  return request.get<>("/system/sys-dept/export", data)
  //},
}
