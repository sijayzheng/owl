import type {Captcha} from '@/types/auth.ts'

export const authApi = {
  login(data: LoginReq) {
    return request.post<LoginResp>('/auth/login', data)
  },
  logout() {
    return request.post<boolean>('/auth/logout')
  },
  captcha() {
    return request.get<Captcha>('/auth/captcha')
  },
}
