/**
 * 生成随机字符串
 */
export function uuid() {
  return 'xxxxx-xxxxx-4xxxx-yxxxx-xxxxx'.replace(/[xy]/g, (c: string) => {
    const r: number = (Math.random() * 16) | 0
    const v: number = c === 'x' ? r : (r & 0x3) | 0x8
    return v.toString()
  })
}

/**
 * @param {string} path
 */
export function isHttp(path: string): boolean {
  return regexConstants.httpUrl.test(path)
}

// 返回项目路径
export function getNormalPath(p: string): string {
  if (p.length === 0 || !p || p === 'undefined') {
    return p
  }
  const res = p.replace('//', '/')
  if (res.at(-1) === '/') {
    return res.slice(0, res.length - 1)
  }
  return res
}
