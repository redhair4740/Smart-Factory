<template>
  <n-card :bordered="false" size="huge" title="登录">
    <n-form>
      <n-form-item label="用户名" prop="username">
        <n-input size="large" round v-model="form.username" clearable placeholder="请输入用户名" />
      </n-form-item>
      <n-form-item label="密码" prop="password">
        <n-input size="large" round type="password" v-model="form.password" clearable placeholder="请输入密码" />
      </n-form-item>
      <n-form-item>
        <n-checkbox v-model="form.rememberMe">记住密码</n-checkbox>
      </n-form-item>
      <n-form-item>
        <n-button type="primary" @click="handleLogin">登录</n-button>
      </n-form-item>
    </n-form>
  </n-card>
</template>

<script>
import Cookies from "js-cookie";

export default {
  name: 'Login',
  data() {
    return {
      form: {
        username: '',
        password: '',
        rememberMe: false
      },
    };
  },
  methods: {
    handleLogin() {
      // 实现登录逻辑，如发送请求到后端服务
      console.log('Logging in with:', this.form);
      console.log('Remember me:', this.form.rememberMe);
      if(this.form.rememberMe){
        Cookies.set("username", this.form.username, { expires: 30 });
        Cookies.set("password", encrypt(this.form.password), { expires: 30 });
        Cookies.set('rememberMe', this.form.rememberMe, { expires: 30 });
      }else{
        Cookies.remove("username");
        Cookies.remove("password");
        Cookies.remove("rememberMe");
      }
    },
  },
};
</script>
