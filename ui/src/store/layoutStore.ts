export const useLayoutStore = defineStore('layout', () => {
  const isSidebarCollapse = ref(false)

  const toggleSidebarCollapse = () => {
    isSidebarCollapse.value = !isSidebarCollapse.value
  }

  return {
    isSidebarCollapse,
    toggleSidebarCollapse,
  }
})
