import type { MessageBoxData, MessageParamsWithType } from 'element-plus'

export const modal = {
  // 消息提示
  msg(content: MessageParamsWithType) {
    ElMessage.info(content)
  },
  // 错误消息
  msgError(content: MessageParamsWithType) {
    ElMessage.error(content)
  },
  // 成功消息
  msgSuccess(content: MessageParamsWithType) {
    ElMessage.success(content)
  },
  // 警告消息
  msgWarning(content: MessageParamsWithType) {
    ElMessage.warning(content)
  },
  // 弹出提示
  alert(content: string) {
    void ElMessageBox.alert(content, '系统提示')
  },
  // 错误提示
  alertError(content: string) {
    void ElMessageBox.alert(content, '系统提示', { type: 'error' })
  },
  // 成功提示
  alertSuccess(content: string) {
    void ElMessageBox.alert(content, '系统提示', { type: 'success' })
  },
  // 警告提示
  alertWarning(content: string) {
    void ElMessageBox.alert(content, '系统提示', { type: 'warning' })
  },
  // 通知提示
  notify(content: string) {
    ElNotification.info(content)
  },
  // 错误通知
  notifyError(content: string) {
    ElNotification.error(content)
  },
  // 成功通知
  notifySuccess(content: string) {
    ElNotification.success(content)
  },
  // 警告通知
  notifyWarning(content: string) {
    ElNotification.warning(content)
  },
  // 确认窗体
  confirm(content: string): Promise<MessageBoxData> {
    return ElMessageBox.confirm(content, '系统提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    })
  },
  // 提交内容
  prompt(content: string) {
    return ElMessageBox.prompt(content, '系统提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    })
  },
}
