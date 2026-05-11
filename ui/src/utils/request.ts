import { createAlova } from 'alova'
import { createClientTokenAuthentication } from 'alova/client'
import adapterFetch from 'alova/fetch'
import VueHook from 'alova/vue'
import { ElMessage } from 'element-plus'

const { onAuthRequired } = createClientTokenAuthentication({
  assignToken: (method) => {
    method.config.headers.Authorization = `Bearer ${localStorage.getItem('token')}`
  },
})

const alovaInstance = createAlova({
  baseURL: '/api',
  timeout: 180_000,
  statesHook: VueHook,
  requestAdapter: adapterFetch(),
  cacheFor: null,
  beforeRequest: onAuthRequired(),
  responded: {
    onSuccess: async (response, method) => {
      if (response.status >= 400) {
        throw new Error(response.statusText)
      }
      const result = await response.json() as Result
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
      if (method.config.meta?.full) {
        return result
      } else {
        return result.data
      }
    },
    onError: (err) => {
      console.error(err.message)
      throw err
    },
  },
})

export default {
  page<T = any>(url: string, params?: any) {
    return alovaInstance.Get<T>(url, {
      params,
      meta: {
        full: true,
      },
    })
  },
  get<T = any>(url: string, params?: any) {
    return alovaInstance.Get<T>(url, { params })
  },
  post<T = any>(url: string, data?: any) {
    return alovaInstance.Post<T>(url, data)
  },
}
