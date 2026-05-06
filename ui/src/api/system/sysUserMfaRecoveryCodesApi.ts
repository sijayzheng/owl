export const SysUserMfaRecoveryCodesApi = {
  // 分页查询MFA备用验证码列表
  page(data: SysUserMfaRecoveryCodesQuery) {
    return request.page<Result<Array<SysUserMfaRecoveryCodes>>>("/system/sys-user-mfa-recovery-codes/page", data)
  },
  // 查询MFA备用验证码列表
  list(data: SysUserMfaRecoveryCodesQuery) {
    return request.get<Array<SysUserMfaRecoveryCodes>>("/system/sys-user-mfa-recovery-codes/list", data)
  },
  // 根据ID查询MFA备用验证码详情
  getById(id: number) {
    return request.get<SysUserMfaRecoveryCodes>("/system/sys-user-mfa-recovery-codes/{id}")
  },
  // 新增MFA备用验证码
  add(data: SysUserMfaRecoveryCodesForm) {
    return request.post<boolean>("/system/sys-user-mfa-recovery-codes/add", data)
  },
  // 修改MFA备用验证码
  update(data: SysUserMfaRecoveryCodesForm) {
    return request.post<boolean>("/system/sys-user-mfa-recovery-codes/update", data)
  },
  // 删除MFA备用验证码
  remove(ids: number[]) {
    return request.post<boolean>("/system/sys-user-mfa-recovery-codes/remove", ids)
  },
  // 下载MFA备用验证码导入模板
  //template() {
  //  return request.get<>("/system/sys-user-mfa-recovery-codes/template")
  //},
  // 导入MFA备用验证码数据
  //importData() {
  //  return request.post<>("/system/sys-user-mfa-recovery-codes/import")
  //},
  // 导出MFA备用验证码数据
  //exportData(data: SysUserMfaRecoveryCodesQuery) {
  //  return request.get<>("/system/sys-user-mfa-recovery-codes/export", data)
  //},
}
