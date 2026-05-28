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

export interface SysConfigPageQuery extends SysConfigQuery, PageQuery {
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

export const sysConfigInitData: SysConfigForm = {
  // 主键
  id: undefined,
  // 参数名称
  configName: undefined,
  // 参数键名
  configKey: undefined,
  // 参数键值
  configValue: undefined,
}
