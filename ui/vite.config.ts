import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import UnoCSS from 'unocss/vite'
import AutoImport from 'unplugin-auto-import/vite'
import ElementPlus from 'unplugin-element-plus/vite'
import IconsResolver from 'unplugin-icons/resolver'
import Icons from 'unplugin-icons/vite'
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'
import Components from 'unplugin-vue-components/vite'
import {defineConfig} from 'vite'
import compression from 'vite-plugin-compression'
import progress from 'vite-plugin-progress'
import {fileURLToPath} from 'node:url'
import {VueRouterAutoImports} from 'vue-router/unplugin'
import VueRouter from 'vue-router/vite'

// https://vite.dev/config/
export default defineConfig({
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  plugins: [
    VueRouter(),
    vue(),
    vueJsx(),
    UnoCSS(),
    AutoImport({
      imports: ['vue', VueRouterAutoImports, 'pinia', '@vueuse/core'],
      resolvers: [
        ElementPlusResolver(),
        IconsResolver({prefix: 'Icon'}),
      ],
      dts: true,
      dirs: [
        'src/api/**',
        'src/utils/**',
        'src/composables/**',
        'src/stores/**',
        {
          glob: 'src/types/**',
          types: true
        },
      ],
    }),
    Components({
      resolvers: [
        ElementPlusResolver({importStyle: 'sass'}),
        IconsResolver({enabledCollections: ['ep']}),
      ],
      dts: true,
      dirs: [
        'src/components',
      ],
    }),
    ElementPlus({useSource: true}),
    Icons({autoInstall: true}),
    compression({algorithm: 'gzip'}),
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
  build: {
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
      'echarts',
      'vxe-table',
      'dayjs',
      'nprogress',
      'qrcode',
      'qs',
      'crypto-js',
      'file-saver',
      'element-plus/es',
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
      'element-plus/es/components/tag/style/index'
    ],
  },
})
