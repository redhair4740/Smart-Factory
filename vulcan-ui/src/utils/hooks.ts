import { ref, onUnmounted } from 'vue'

/**
 * 防抖函数 hook
 * @param fn 需要防抖的函数
 * @param delay 延迟时间，单位毫秒
 * @returns 防抖后的函数
 */
export function useDebounce<T extends (...args: any[]) => any>(
  fn: T,
  delay: number = 300
): (...args: Parameters<T>) => void {
  let timer: number | null = null

  // 在组件卸载时清除定时器
  onUnmounted(() => {
    if (timer) clearTimeout(timer)
  })

  return function(...args: Parameters<T>) {
    if (timer) clearTimeout(timer)
    timer = window.setTimeout(() => {
      fn(...args)
    }, delay)
  }
}

/**
 * 节流函数 hook
 * @param fn 需要节流的函数
 * @param delay 延迟时间，单位毫秒
 * @returns 节流后的函数
 */
export function useThrottle<T extends (...args: any[]) => any>(
  fn: T,
  delay: number = 300
): (...args: Parameters<T>) => void {
  let last = 0

  return function(...args: Parameters<T>) {
    const now = Date.now()
    if (now - last >= delay) {
      fn(...args)
      last = now
    }
  }
}

/**
 * 使用异步加载状态
 * @returns loading状态和控制函数
 */
export function useLoading() {
  const loading = ref(false)

  const startLoading = () => {
    loading.value = true
  }

  const stopLoading = () => {
    loading.value = false
  }

  // 包装异步函数，自动处理loading状态
  const withLoading = async <T>(fn: () => Promise<T>): Promise<T> => {
    try {
      startLoading()
      return await fn()
    } finally {
      stopLoading()
    }
  }

  return {
    loading,
    startLoading,
    stopLoading,
    withLoading
  }
} 