import 'axios'

declare module 'axios' {
  export interface AxiosResponse<T = any> {
    headers: {
      'content-disposition'?: string
      // 保留原有的其他头部属性
      [key: string]: any
    } & AxiosResponseHeaders
  }
}
