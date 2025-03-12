<template>
  <div class="index-container">
    <h1>智能工厂系统</h1>
    <p>欢迎使用智能工厂管理系统</p>
    <el-button type="primary" @click="logout">退出登录</el-button>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/store/authStore'
import { ElMessage } from 'element-plus'

const router = useRouter()
const authStore = useAuthStore()

const logout = () => {
  authStore.logout()
  ElMessage.success('已退出登录')
  router.push('/login')
}
</script>

<style scoped>
.index-container {
  padding: 20px;
  text-align: center;
  margin-top: 100px;
}

h1 {
  margin-bottom: 20px;
  color: var(--primary-color);
}

p {
  margin-bottom: 30px;
  color: var(--text-color-regular);
}
</style>
