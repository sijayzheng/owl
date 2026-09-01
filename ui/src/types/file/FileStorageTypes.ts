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
}

export interface FileStorageQuery {
  // 原名
  originalName?: string
  // 文件后缀名
  fileSuffix?: string
  // MIME类型
  contentType?: string
}

export interface FileStoragePageQuery extends FileStorageQuery, PageQuery {
}

export interface FileStorageForm {
}

export const fileStorageInitData: FileStorageForm = {
}
