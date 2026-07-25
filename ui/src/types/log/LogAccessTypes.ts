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
  // 访问状态
  status: number
  // 访问时间
  accessTime: string
  // 消耗时间
  costTime: number
}

export interface LogAccessQuery {
  // 访问人员
  accessUsername?: string
  // 请求url
  accessUrl?: string
  // 访问时间
  accessTimeRange?: string[]
}

export interface LogAccessPageQuery extends LogAccessQuery, PageQuery {
}

export interface LogAccessForm {
  // 主键
  id?: number
  // 用户id
  userId?: number
  // 模块标题
  title?: string
  // 业务类型
  operateType?: string
  // 方法名称
  method?: string
  // 请求方式
  requestMethod?: string
  // 访问人员
  accessUsername?: string
  // 请求url
  accessUrl?: string
  // 主机地址
  accessIp?: string
  // 访问地点
  accessLocation?: string
  // 请求参数
  accessParam?: string
  // 返回参数
  jsonResult?: string
  // 访问状态
  status?: number
  // 错误消息
  errorMsg?: string
  // 访问时间
  accessTime?: string
  // 消耗时间
  costTime?: number
}

export const logAccessInitData: LogAccessForm = {
  // 主键
  id: undefined,
  // 用户id
  userId: undefined,
  // 模块标题
  title: undefined,
  // 业务类型
  operateType: undefined,
  // 方法名称
  method: undefined,
  // 请求方式
  requestMethod: undefined,
  // 访问人员
  accessUsername: undefined,
  // 请求url
  accessUrl: undefined,
  // 主机地址
  accessIp: undefined,
  // 访问地点
  accessLocation: undefined,
  // 请求参数
  accessParam: undefined,
  // 返回参数
  jsonResult: undefined,
  // 访问状态
  status: undefined,
  // 错误消息
  errorMsg: undefined,
  // 访问时间
  accessTime: undefined,
  // 消耗时间
  costTime: undefined,
}
