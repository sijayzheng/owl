interface UseCountdownOptions {
  /** 倒计时总秒数（初始值） */
  seconds: number
  /** 倒计时结束时的回调函数 */
  onEnd?: () => void
  /** 是否立即开始（默认 false） */
  immediate?: boolean
}

interface UseCountdownReturn {
  /** 当前剩余秒数 */
  currentSeconds: Ref<number>
  /** 是否正在倒计时 */
  isActive: Ref<boolean>
  /** 格式化的时间字符串 (HH:MM:SS) */
  formatTime: Ref<string>
  /** 开始倒计时（如果已暂停则恢复） */
  start: () => void
  /** 暂停倒计时 */
  pause: () => void
  /** 重置倒计时到初始秒数，停止倒计时 */
  reset: () => void
  /** 设置剩余秒数（会停止当前倒计时） */
  setRemainingSeconds: (seconds: number) => void
}

/**
 * 倒计时组合式函数
 * @param options 配置项
 * @returns 倒计时控制方法与状态
 */
export function useCountdown(options: UseCountdownOptions): UseCountdownReturn {
  const {
    seconds: initialSeconds,
    onEnd,
    immediate = false
  } = options

  const currentSeconds = ref(initialSeconds)
  const isActive = ref(false)
  let timer: ReturnType<typeof setInterval> | null = null

  // 格式化时间显示 (HH:MM:SS)
  const formatTime = computed(() => {
    const totalSecs = Math.max(0, Math.floor(currentSeconds.value))
    const hours = Math.floor(totalSecs / 3600)
    const minutes = Math.floor((totalSecs % 3600) / 60)
    const secs = totalSecs % 60
    return [hours, minutes, secs].map(v => v.toString().padStart(2, '0')).join(':')
  })

  // 清除定时器
  const clearTimer = () => {
    if (timer) {
      clearInterval(timer)
      timer = null
    }
  }

  // 停止倒计时（仅清除定时器，不修改状态标志）
  const stopTimer = () => {
    clearTimer()
    isActive.value = false
  }

  // 开始倒计时（内部逻辑）
  const startTimer = () => {
    if (timer) {
      return
    } // 已有定时器则不重复启动
    if (currentSeconds.value <= 0) {
      // 剩余0秒时触发结束回调，但不再启动计时器
      onEnd?.()
      return
    }

    isActive.value = true
    timer = setInterval(() => {
      if (currentSeconds.value > 0) {
        currentSeconds.value -= 1
        if (currentSeconds.value === 0) {
          // 倒计时结束
          stopTimer()
          onEnd?.()
        }
      } else {
        // 防御：如果剩余秒数已经 <=0，停止计时器并调用结束回调
        stopTimer()
        onEnd?.()
      }
    }, 1000)
  }

  // 开始（暴露给外部，可从暂停或初始状态恢复）
  const start = () => {
    if (isActive.value) {
      return
    } // 正在倒计时，忽略重复调用
    if (currentSeconds.value <= 0) {
      return
    } // 剩余0秒，不启动
    startTimer()
  }

  // 暂停
  const pause = () => {
    if (!isActive.value) {
      return
    }
    stopTimer()
  }

  // 重置到初始秒数（停止倒计时）
  const reset = () => {
    pause()
    currentSeconds.value = initialSeconds
  }

  // 手动设置剩余秒数（停止倒计时）
  const setRemainingSeconds = (seconds: number) => {
    pause()
    currentSeconds.value = Math.max(0, seconds)
  }

  // 若需要立即开始
  if (immediate && currentSeconds.value > 0) {
    start()
  }

  // 组件卸载时清理定时器
  onUnmounted(() => {
    clearTimer()
  })

  return {
    currentSeconds,
    isActive,
    formatTime,
    start,
    pause,
    reset,
    setRemainingSeconds,
  }
}
