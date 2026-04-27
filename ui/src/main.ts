import '@/styles/dark.scss'
import '@/styles/index.scss'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'animate.css'
import 'nprogress/nprogress.css'
import 'virtual:uno.css'
import VXETable from 'vxe-table'
import 'vxe-table/lib/style.css'
import {createApp} from 'vue'
import {createPinia} from 'pinia'
import App from './App.vue'
import router from './router'
import HighLight from '@highlightjs/vue-plugin'
import 'highlight.js/lib/common'
import 'highlight.js/styles/atom-one-dark.css'
import 'dayjs/locale/zh-cn'

const app = createApp(App)


app.use(router)
app.use(createPinia())


Object.entries(ElementPlusIconsVue).forEach(([key, component]) => {
  app.component(key, component)
})

app.use(VXETable)
app.use(HighLight)


app.mount('#app')
