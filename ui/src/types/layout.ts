export interface TagView {
  fullPath: string
  path: string
  title: string
  name?: string
  icon?: string
  affix?: boolean
  query?: Record<string, string>
  params?: Record<string, string>
}
