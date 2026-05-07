export interface SysTask extends BaseEntity {
  // 主键
  id: number
  // 任务唯一标识符
  taskKey: string
  // 任务名称
  taskName: string
  // 任务分组
  taskGroup: string
  // 任务描述
  description: string
  // 调度类型 (CRON, FIXED_DELAY, FIXED_RATE)
  scheduleType: string
  // Cron 表达式
  cronExpression: string
  // 固定延迟时间 (毫秒) (如果使用 fixedDelay)
  fixedDelay: string
  // 初始延迟时间 (毫秒) (如果使用 fixedDelay 或 fixedRate)
  initialDelay: string
  // 固定频率时间 (毫秒) (如果使用 fixedRate)
  fixedRate: string
  // 是否启用
  enabled: boolean
  // 是否允许并发执行
  concurrent: boolean
  // 任务失败时是否发送通知
  notifyOnFailure: boolean
  // Spring Bean类名 (包含包路径)
  beanClass: string
  // 执行方法名
  methodName: string
  // 执行方法参数
  methodParams: string
  // 接续任务id
  nextTaskId: number
  // 等待成功
  waitSuccess: boolean
  // 备注
  remark: string
}

export interface SysTaskQuery {
  // 任务名称
  taskName?: string
}

export interface SysTaskForm {
  // 主键
  id?: number
  // 任务唯一标识符
  taskKey?: string
  // 任务名称
  taskName?: string
  // 任务分组
  taskGroup?: string
  // 任务描述
  description?: string
  // 调度类型 (CRON, FIXED_DELAY, FIXED_RATE)
  scheduleType?: string
  // Cron 表达式
  cronExpression?: string
  // 固定延迟时间 (毫秒) (如果使用 fixedDelay)
  fixedDelay?: string
  // 初始延迟时间 (毫秒) (如果使用 fixedDelay 或 fixedRate)
  initialDelay?: string
  // 固定频率时间 (毫秒) (如果使用 fixedRate)
  fixedRate?: string
  // 是否启用
  enabled?: boolean
  // 是否允许并发执行
  concurrent?: boolean
  // 任务失败时是否发送通知
  notifyOnFailure?: boolean
  // Spring Bean类名 (包含包路径)
  beanClass?: string
  // 执行方法名
  methodName?: string
  // 执行方法参数
  methodParams?: string
  // 接续任务id
  nextTaskId?: number
  // 等待成功
  waitSuccess?: boolean
  // 备注
  remark?: string
}
