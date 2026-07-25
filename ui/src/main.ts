import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import HighLight from '@highlightjs/vue-plugin'
import App from './App.vue'
import router from './router'
import '@/styles/dark.scss'
import '@/styles/index.scss'
import 'animate.css'
import 'nprogress/nprogress.css'
import 'virtual:uno.css'
import 'highlight.js/lib/common'
import 'highlight.js/styles/atom-one-dark.css'
import 'dayjs/locale/zh-cn'

const app = createApp(App)

app.use(router)
app.use(createPinia())

Object.entries(ElementPlusIconsVue).forEach(([ key, component ]) => {
  app.component(key, component)
})

app.use(HighLight)
app.directive('hasPerm', {
  mounted(el: HTMLElement, binding: DirectiveBinding<string>) {
    const { permissions } = useUserStore()
    const { value } = binding
    if (value && Array.isArray(value) && value.length > 0) {
      const hasPermission = permissions.some((perm: string) => {
        return perm === '*:*:*' || value.includes(perm)
      })
      if (!hasPermission) {
        el.parentNode && el.parentNode.removeChild(el)
        return false
      }
    } else {
      throw new Error('check perms! Like v-has-permi="[\'system:user:add\',\'system:user:edit\']"')
    }
  },
})

app.mount('#app')
