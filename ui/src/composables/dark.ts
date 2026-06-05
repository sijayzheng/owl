export const isDark = useDark({
  storageKey: 'owl-theme',
  valueDark: 'dark',
  valueLight: 'light',
  selector: 'html',
  attribute: 'class',
})

export const toggleDark = useToggle(isDark)
