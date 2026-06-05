import type { RequestBody } from 'alova'
import { axiosRequestAdapter } from '@alova/adapter-axios'
import { createAlova } from 'alova'
import VueHook from 'alova/vue'
import { ElMessage } from 'element-plus'
import FileSaver from 'file-saver'

const alovaInstance = createAlova({
  baseURL: '/api',
  timeout: 180_000,
  statesHook: VueHook,
  requestAdapter: axiosRequestAdapter(),
  cacheFor: null,
  beforeRequest(method) {
    const token = getToken()
    if (token)
      method.config.headers.Authorization = `Bearer ${token}`
  },
  responded: {
    onSuccess: (response, method) => {
      if (response.status >= 400) {
        throw new Error(response.statusText)
      }
      if (method.config.responseType === 'blob') {
        const disposition = response.headers['content-disposition'] as string
        let fileName: string
        if (disposition?.includes('attachment; filename="')) {
          fileName = disposition?.substring(22, disposition?.length - 1)
          fileName = decodeURIComponent(fileName)
        } else {
          fileName = uuid()
        }
        const blob = new Blob([response.data], { type: 'application/octet-stream' })
        FileSaver.saveAs(blob, fileName)
        return response
      }
      const result: Result = response.data as Result
      if (result.code !== 200) {
        ElMessage.error(result.message)
        throw new Error(result.message)
      }
      if (result.message) {
        if (result.data) {
          ElMessage.success(result.message)
        } else {
          ElMessage.error(result.message)
        }
      }
      if ((method.config.meta as { full?: any } | undefined)?.full != null) {
        return result
      } else {
        return result.data
      }
    },
    onError: (err) => {
      console.error(err)
      throw err
    },
  },
})

export default {
  page<T = any>(url: string, params?: Pair) {
    return alovaInstance.Get<T>(url, {
      params,
      meta: {
        full: true,
      },
    })
  },
  get<T = any>(url: string, params?: Pair) {
    return alovaInstance.Get<T>(url, { params })
  },
  post<T = any>(url: string, data?: RequestBody) {
    return alovaInstance.Post<T>(url, data)
  },
  download(url: string, params?: Pair) {
    return useRequest(() => alovaInstance.Get(url, {
      responseType: 'blob',
      params,
    }), { immediate: false })
  },
}
