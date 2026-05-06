export interface LogTask  {
  // 主键
  id: number
  // 关联的任务id
  taskId: number
  // 本次执行的唯一ID
  executionId: string
  // 任务开始执行时间
  startTime: string
  // 任务结束执行时间
  endTime: string
  // 任务执行耗时(毫秒)
  durationMs: number
  // 执行状态
  status: string
  // 执行结果消息
  resultMessage: string
  // 错误堆栈信息
  errorStackTrace: string
  // 执行线程名
  threadName: string
  // 日志创建时间
  createTime: string
}

export interface LogTaskQuery {
  // 关联的任务id
  taskId?: number
  // 任务开始执行时间
  startTimeRange?: string[]
  // 执行状态
  status?: string
}

export interface LogTaskForm {
  // 主键
  id?: number
}
