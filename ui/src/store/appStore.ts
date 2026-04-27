export const useAppStore = defineStore('app', () => {
  const mobile = ref<boolean>(false) // 是否是移动端
  const title = ref(import.meta.env.VITE_APP_TITLE) // 标题
  const pageLoading = ref<boolean>(false) // 路由跳转loading
  const breadcrumb = ref<boolean>(true) // 面包屑
  const breadcrumbIcon = ref<boolean>(false) // 面包屑图标
  const collapse = ref<boolean>(false) // 折叠菜单
  const uniqueOpened = ref<boolean>(true) // 是否只保持一个子菜单的展开
  const hamburger = ref<boolean>(true) // 折叠图标
  const screenfull = ref<boolean>(false) // 全屏图标
  const size = ref<boolean>(false) // 尺寸图标
  const locale = ref<boolean>(false) // 多语言图标
  const tagsView = ref<boolean>(true) // 标签页
  const tagsViewIcon = ref<boolean>(false) // 是否显示标签图标
  const logo = ref<boolean>(true) // logo
  const fixedHeader = ref<boolean>(true) // 固定toolheader
  const footer = ref<boolean>(false) // 显示页脚
  const greyMode = ref<boolean>(false) // 是否开始灰色模式，用于特殊悼念日
  const dynamicRouter = ref<boolean>(true) // 是否动态路由
  const serverDynamicRouter = ref<boolean>(true) // 是否服务端渲染动态路由
  const fixedMenu = ref<boolean>(false) // 是否固定菜单
  const layout = ref('topLeft') // layout布局
  const isDark = ref<boolean>(false) // 是否是暗黑模式
  const currentSize = ref('default') // 组件尺寸
  const theme: { [key: string]: string } = {
    // 主题色
    elColorPrimary: '#409eff',
    // 左侧菜单边框颜色
    leftMenuBorderColor: 'inherit',
    // 左侧菜单背景颜色
    leftMenuBgColor: '#001529',
    // 左侧菜单浅色背景颜色
    leftMenuBgLightColor: '#0f2438',
    // 左侧菜单选中背景颜色
    leftMenuBgActiveColor: 'var(--el-color-primary)',
    // 左侧菜单收起选中背景颜色
    leftMenuCollapseBgActiveColor: 'var(--el-color-primary)',
    // 左侧菜单字体颜色
    leftMenuTextColor: '#bfcbd9',
    // 左侧菜单选中字体颜色
    leftMenuTextActiveColor: '#fff',
    // logo字体颜色
    logoTitleTextColor: '#fff',
    // logo边框颜色
    logoBorderColor: 'inherit',
    // 头部背景颜色
    topHeaderBgColor: '#fff',
    // 头部字体颜色
    topHeaderTextColor: 'inherit',
    // 头部悬停颜色
    topHeaderHoverColor: '#f6f6f6',
    // 头部边框颜色
    topToolBorderColor: '#eee',
  }

  function setCssVarTheme() {
    for (const key in theme) {
      setCssVar(`--${humpToUnderline(key)}`, theme[key])
    }
    setPrimaryLight()
  }

  function setPrimaryLight() {
    if (theme.elColorPrimary) {
      const elColorPrimary = theme.elColorPrimary
      const color = isDark.value ? '#000000' : '#ffffff'
      const lightList = [3, 5, 7, 8, 9]
      lightList.forEach((v) => {
        setCssVar(`--el-color-primary-light-${v}`, mix(color, elColorPrimary, v / 10))
      })
      setCssVar(`--el-color-primary-dark-2`, mix(color, elColorPrimary, 0.2))
    }
  }

  function initTheme() {
    const dark = useDark({
      valueDark: 'dark',
      valueLight: 'light',
    })
    dark.value = isDark.value
    const newTitle = import.meta.env.VITE_APP_TITLE
    if (newTitle !== title.value) {
      title.value = newTitle
    }
  }

  return {
    mobile,
    title,
    pageLoading,
    breadcrumb,
    breadcrumbIcon,
    collapse,
    uniqueOpened,
    hamburger,
    screenfull,
    size,
    locale,
    tagsView,
    tagsViewIcon,
    logo,
    fixedHeader,
    footer,
    greyMode,
    dynamicRouter,
    serverDynamicRouter,
    fixedMenu,
    layout,
    isDark,
    currentSize,
    theme,
    setCssVarTheme,
    initTheme,
  }
})
