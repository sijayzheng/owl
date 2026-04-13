import {useDark, useToggle} from '@vueuse/core'

export const isDark = useDark({
  storageKey: 'owl-theme',
  valueDark: 'dark',
  valueLight: 'light',
  selector: 'html',
  attribute: 'class',
})

export const toggleDark = useToggle(isDark)
