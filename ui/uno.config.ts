import {defineConfig} from 'unocss'
import presetMini from '@unocss/preset-mini'
import presetAttributify from '@unocss/preset-attributify'
import presetIcons from '@unocss/preset-icons'
import transformerVariantGroup from '@unocss/transformer-variant-group'

export default defineConfig({
  presets: [
    presetMini(),     // 基础样式
    presetAttributify(),
    presetIcons({
      scale: 1.2,
      warn: true,
      extraProperties: {
        display: 'inline-block',
        'vertical-align': 'middle',
      },
    }),
  ],
  transformers: [
    transformerVariantGroup(),
  ],
  theme: {
    colors: {
      primary: 'var(--el-color-primary)',
      primary_dark: 'var(--el-color-primary-light-5)',
    },
  },
  shortcuts: {
    'flex-center': 'flex items-center justify-center',
    'flex-between': 'flex items-center justify-between',
    'wh-full': 'w-full h-full',
    'abs-center': 'absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2',
    'btn': 'px-4 py-1 rounded inline-block bg-teal-700 text-white cursor-pointer !outline-none hover:bg-teal-800 disabled:cursor-default disabled:bg-gray-600 disabled:opacity-50',
    'icon-btn': 'inline-block cursor-pointer select-none opacity-75 transition duration-200 ease-in-out hover:opacity-100 hover:text-teal-600',
    'c-primary': 'c-[#1c6b48] dark:c-[#5cad8a]',
    'border-main': 'border-gray/20',
    'bg-main': 'bg-white dark:bg-hex-121212',
    'bg-hover': 'bg-gray/10',
  },
})
