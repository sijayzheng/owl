export interface SysConfig extends BaseEntity {
  // 主键
  id: number
  // 参数名称
  configName: string
  // 参数键名
  configKey: string
  // 参数键值
  configValue: string
}

export interface SysConfigQuery {
  // 参数名称
  configName?: string
  // 参数键名
  configKey?: string
}

export interface SysConfigForm {
  // 主键
  id?: number
  // 参数名称
  configName?: string
  // 参数键名
  configKey?: string
  // 参数键值
  configValue?: string
}
