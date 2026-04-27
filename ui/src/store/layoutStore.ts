export const useLayoutStore = defineStore('layout', () => {
  const isCollapse = ref(false)

  const toggleCollapse = () => {
    isCollapse.value = !isCollapse.value
  }

  const setCollapse = (value: boolean) => {
    isCollapse.value = value
  }

  return {
    isCollapse,
    toggleCollapse,
    setCollapse,
  }
})
