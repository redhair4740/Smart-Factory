<template>
  <router-view v-slot="{ Component }">
    <component :is="Component" />
  </router-view>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/store/authStore'

const router = useRouter()
const authStore = useAuthStore()

// 全局路由守卫
router.beforeEach(async (to, from, next) => {
  // 更新页面标题
  document.title = to.meta.title ? `${to.meta.title} - 智能工厂系统` : '智能工厂系统'
  
  // 判断是否需要认证
  const requiresAuth = to.meta.requiresAuth !== false
  const isAuthenticated = authStore.isAuthenticated
  
  if (requiresAuth && !isAuthenticated) {
    // 需要认证但未登录，重定向到登录页
    next({ path: '/login', query: { redirect: to.fullPath } })
  } else if (to.path === '/login' && isAuthenticated) {
    // 已登录访问登录页，重定向到首页
    next({ path: '/index' })
  } else {
    // 其他情况正常通过
    next()
  }
})
</script>

<style>
#app {
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB',
    'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  margin: 0;
  padding: 0;
  height: 100vh;
}

html, body {
  margin: 0;
  padding: 0;
  height: 100%;
}
</style>