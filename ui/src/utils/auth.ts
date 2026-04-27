const tokenStorage = useStorage<string | null>('token', null)

export const getToken = () => tokenStorage.value

export const setToken = (access_token: string) => (tokenStorage.value = access_token)

export const removeToken = () => (tokenStorage.value = null)
