export interface LogAccess  {
  // 主键
  id: number
  // 用户id
  userId: number
  // 模块标题
  title: string
  // 业务类型
  operateType: string
  // 方法名称
  method: string
  // 请求方式
  requestMethod: string
  // 访问人员
  accessUsername: string
  // 请求url
  accessUrl: string
  // 主机地址
  accessIp: string
  // 访问地点
  accessLocation: string
  // 请求参数
  accessParam: string
  // 返回参数
  jsonResult: string
  // 访问状态
  status: number
  // 错误消息
  errorMsg: string
  // 访问时间
  accessTime: string
  // 消耗时间
  costTime: number
}

export interface LogAccessQuery {
  // 用户id
  userId?: number
  // 模块标题
  title?: string
  // 业务类型
  operateType?: string
  // 方法名称
  method?: string
  // 请求url
  accessUrl?: string
}

export interface LogAccessForm {
  // 主键
  id?: number
}
