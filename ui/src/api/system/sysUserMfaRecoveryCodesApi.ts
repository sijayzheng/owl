export const sysUserMfaRecoveryCodesApi = {
  // 分页查询MFA备用验证码列表
  page(data: SysUserMfaRecoveryCodesPageQuery) {
    return request.page<Result<SysUserMfaRecoveryCodes[]>>('/system/sysUserMfaRecoveryCodes/page', data)
  },
  // 查询MFA备用验证码列表
  list(data: SysUserMfaRecoveryCodesQuery) {
    return request.get<SysUserMfaRecoveryCodes[]>('/system/sysUserMfaRecoveryCodes/list', data)
  },
  // 根据ID查询MFA备用验证码详情
  getById(id: number) {
    return request.get<SysUserMfaRecoveryCodes>(`/system/sysUserMfaRecoveryCodes/${id}`)
  },
  // 保存MFA备用验证码
  save(data: SysUserMfaRecoveryCodesForm) {
    return request.post<boolean>('/system/sysUserMfaRecoveryCodes/save', data)
  },
  // 删除MFA备用验证码
  remove(ids: number[]) {
    return request.post<boolean>('/system/sysUserMfaRecoveryCodes/remove', ids)
  },
  // 下载MFA备用验证码导入模板
  downloadTemplate() {
    return request.download('/system/sysUserMfaRecoveryCodes/downloadTemplate')
  },
  // 导入MFA备用验证码数据
  importData(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/system/sysUserMfaRecoveryCodes/import', formData)
  },
  // 导出MFA备用验证码数据
  exportData(data: SysUserMfaRecoveryCodesQuery) {
    return request.download('/system/sysUserMfaRecoveryCodes/export', data)
  },
}
