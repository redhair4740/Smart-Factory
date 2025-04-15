<template>
  <div class="not-found-container">
    <div class="content">
      <el-image class="error-image" :src="notFoundImage" alt="404" />
      <h1 class="error-title">404</h1>
      <p class="error-message">抱歉，您访问的页面不存在</p>
      <el-button type="primary" @click="goHome">返回首页</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import notFoundImage from '@/assets/404.svg'

const router = useRouter()

const goHome = () => {
  router.push('/dashboard')
}
</script>

<style scoped>
.not-found-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f7fa;
}

.content {
  text-align: center;
  padding: 2rem;
}

.error-image {
  width: 300px;
  margin-bottom: 2rem;
}

.error-title {
  font-size: 6rem;
  color: #409eff;
  margin: 0;
  line-height: 1.2;
}

.error-message {
  font-size: 1.5rem;
  color: #606266;
  margin: 1rem 0 2rem;
}

@media (max-width: 768px) {
  .error-image {
    width: 200px;
  }

  .error-title {
    font-size: 4rem;
  }

  .error-message {
    font-size: 1.2rem;
  }
}
</style>