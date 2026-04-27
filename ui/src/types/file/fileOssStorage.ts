export interface FileOssStorage extends BaseEntity {
  // 主键
  id: number
  // 文件名
  fileName: string
  // 原名
  originalName: string
  // 文件后缀名
  fileSuffix: string
  // 文件大小(字节)
  fileSize: number
  // MIME类型
  contentType: string
  // url地址
  url: string
  // 服务商
  service: string
  // 删除
  deleted: boolean
}

export interface FileOssStorageQuery {
  // 文件名
  fileName?: string
  // 原名
  originalName?: string
  // 文件后缀名
  fileSuffix?: string
}

export interface FileOssStorageForm {
  // 主键
  id?: number
  // 文件名
  fileName?: string
  // 原名
  originalName?: string
  // 文件后缀名
  fileSuffix?: string
  // 文件大小(字节)
  fileSize?: number
  // MIME类型
  contentType?: string
  // url地址
  url?: string
  // 服务商
  service?: string
}
