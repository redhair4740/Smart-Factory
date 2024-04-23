<template>
  <n-card title="登录">
    <n-form>
      <n-form-item label="用户名" prop="username">
        <n-input v-model="form.username" clearable placeholder="请输入用户名" />
      </n-form-item>
      <n-form-item label="密码" prop="password">
        <n-password v-model="form.password" clearable placeholder="请输入密码" />
      </n-form-item>
      <n-form-item>
        <n-button type="primary" @click="handleLogin">登录</n-button>
      </n-form-item>
    </n-form>
  </n-card>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      form: {
        username: '',
        password: '',
      },
    };
  },
  methods: {
    handleLogin() {
      // 实现登录逻辑，如发送请求到后端服务
      console.log('Logging in with:', this.form);
    },
  },
};
</script>
