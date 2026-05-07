const tokenStorage = useStorage<string>('token', '')

export const getToken = () => tokenStorage.value

export const setToken = (access_token: string) => (tokenStorage.value = access_token)

export const removeToken = () => (tokenStorage.value = '')
