import { fileURLToPath } from 'node:url'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import UnoCSS from 'unocss/vite'
import AutoImport from 'unplugin-auto-import/vite'
import ElementPlus from 'unplugin-element-plus/vite'
import IconsResolver from 'unplugin-icons/resolver'
import Icons from 'unplugin-icons/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import Components from 'unplugin-vue-components/vite'
import { defineConfig } from 'vite'
import compression from 'vite-plugin-compression'
import progress from 'vite-plugin-progress'
import { getFileBasedRouteName } from 'vue-router/unplugin'
import VueRouter from 'vue-router/vite'

// https://vite.dev/config/
const apiPrefix = /^\/api/
export default defineConfig({
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  plugins: [
    VueRouter({
      // 如何以及扫描哪些文件夹以查找文件
      routesFolder: [
        {
          src: 'src/views',
          path: '',
        },
      ],
      // 哪些类型的文件应被视为页面
      extensions: [ '.vue' ],
      // 要包含哪些文件
      filePatterns: [ '**/*' ],
      // 要排除的文件
      exclude: [],
      // 生成的 d.ts 文件路径
      dts: true,
      // 如何生成路由名称
      getRouteName: routeNode => getFileBasedRouteName(routeNode),
      // <route> 自定义块的默认语言
      routeBlockLang: 'json5',
      // 如何导入路由，也可以是字符串
      importMode: 'async',
      // 路径解析器的选项
      pathParser: {
        // `users.[id]` 应该被解析为 `users/:id` 吗？
        dotNesting: true,
      },
    }),
    vue(),
    vueJsx(),
    UnoCSS(),
    AutoImport({
      imports: [ 'vue', 'vue-router', '@vueuse/core', 'pinia', {
        'element-plus': [ 'ElMessage', 'ElMessageBox', 'ElNotification', 'ElLoading' ],
      }, {
        'alova/client': [ 'useFetcher', 'useForm', 'usePagination', 'useRequest', 'useUploader' ],
      } ],
      resolvers: [
        ElementPlusResolver(),
        IconsResolver({ prefix: 'Icon' }),
      ],
      dirs: [
        'src/api/**',
        'src/utils/**',
        'src/composables/**',
        'src/hooks/**',
        'src/store/**',
        'src/router/**',
        {
          glob: 'src/types/**',
          types: true,
        },
      ],
      vueTemplate: true,
      dts: true,
    }),
    Components({
      resolvers: [
        ElementPlusResolver({ importStyle: 'sass' }),
        IconsResolver({ enabledCollections: [ 'ep' ] }),
      ],
      dts: true,
      dirs: [ 'src/components/**' ],
    }),
    ElementPlus({ useSource: true }),
    Icons({ autoInstall: true }),
    compression({ algorithm: 'gzip' }),
    progress({
      format: '🚀 构建中 [:bar] :percent | 耗时: :elapsed s | 剩余: :etas s',
      width: 50,
    }),
  ],
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@use "@/styles/element-vars.scss" as *;`,
      },
    },
  },
  server: {
    port: 9528,
    cors: true,
    proxy: {
      '/api': {
        target: 'http://localhost:9527',
        changeOrigin: true,
        rewrite: path => path.replace(apiPrefix, ''),
      },
    },
  },
  build: {
    outDir: '../src/main/resources/static',
    assetsDir: 'static',
    chunkSizeWarningLimit: 1000,
    modulePreload: false,
    cssCodeSplit: true,
  },
  optimizeDeps: {
    include: [
      'vue',
      'vue-router',
      'pinia',
      '@vueuse/core',
      'element-plus',
      '@element-plus/icons-vue',
      'alova',
      'alova/fetch',
      'alova/vue',
      '@alova/adapter-axios',
      'axios',
      'echarts',
      'vxe-table',
      'dayjs',
      'nprogress',
      'qrcode',
      'qs',
      'crypto-js',
      'file-saver',
      'vue-types',
      // Element Plus 样式子模块：部分样式可能未被自动预构建，显式列出以避免开发时请求瀑布流
      'element-plus/es',
      'element-plus/es/components/message/style/index',
      'element-plus/es/components/avatar/style/index',
      'element-plus/es/components/badge/style/index',
      'element-plus/es/components/base/style/index',
      'element-plus/es/components/breadcrumb-item/style/index',
      'element-plus/es/components/breadcrumb/style/index',
      'element-plus/es/components/dropdown-item/style/index',
      'element-plus/es/components/dropdown-menu/style/index',
      'element-plus/es/components/dropdown/style/index',
      'element-plus/es/components/icon/style/index',
      'element-plus/es/components/menu-item/style/index',
      'element-plus/es/components/menu/style/index',
      'element-plus/es/components/scrollbar/style/index',
      'element-plus/es/components/sub-menu/style/index',
      'element-plus/es/components/tag/style/index',
      'element-plus/es/components/button/style/index',
      'element-plus/es/components/form-item/style/index',
      'element-plus/es/components/form/style/index',
      'element-plus/es/components/input/style/index',
      'element-plus/es/components/config-provider/style/index',
      'element-plus/es/components/card/style/index',
      'element-plus/es/components/empty/style/index',
      'element-plus/es/components/link/style/index',
      'element-plus/es/components/loading/style/index',
      'element-plus/es/components/message-box/style/index',
      'element-plus/es/components/popover/style/index',
      'element-plus/es/components/tooltip/style/index',
      'element-plus/es/components/dialog/style/index',
      'element-plus/es/components/notification/style/index',
      'element-plus/es/components/pagination/style/index',
      'element-plus/es/components/table-column/style/index',
      'element-plus/es/components/table/style/index',
    ],
  },
})
