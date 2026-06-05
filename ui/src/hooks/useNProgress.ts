import * as NProgressModule from 'nprogress'
import 'nprogress/nprogress.css'
import '@/styles/nProgress.scss'

export function useNProgress() {
  const NProgress = ('default' in NProgressModule ? NProgressModule.default : NProgressModule) as typeof NProgressModule
  NProgress.configure({
    showSpinner: false,
  })

  function start() {
    NProgress.start()
  }

  function done() {
    NProgress.done()
  }

  function setProgress(progress: number) {
    NProgress.set(progress)
  }

  return {
    start,
    done,
    setProgress,
  }
}
