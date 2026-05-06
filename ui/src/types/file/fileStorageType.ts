export interface FileStorage extends BaseEntity {
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
  // 存储路径
  path: string
  // 删除
  deleted: boolean
}

export interface FileStorageQuery {
  // 文件名
  fileName?: string
  // 原名
  originalName?: string
  // 文件后缀名
  fileSuffix?: string
}

export interface FileStorageForm {
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
  // 存储路径
  path?: string
}
