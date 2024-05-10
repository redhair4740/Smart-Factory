<template>
  <n-card :bordered="false" size="huge" title="登录">
    <n-form>
      <n-form-item label="用户名" prop="loginName">
        <n-input size="large" round v-model:value="form.loginName" type="text" clearable placeholder="请输入用户名" />
      </n-form-item>
      <n-form-item label="密码" prop="password">
        <n-input size="large" round type="password" v-model:value="form.password" clearable placeholder="请输入密码" />
      </n-form-item>
      <n-form-item>
        <n-checkbox v-model:checked="form.rememberMe">记住密码</n-checkbox>
      </n-form-item>
      <n-form-item>
        <n-button type="primary" @click="handleLogin">登录</n-button>
      </n-form-item>
    </n-form>
  </n-card>
</template>

<script setup lang="js">
import { ref } from 'vue';
import Cookies from "js-cookie";
import { encrypt, decrypt } from './../utils/jsencrypt'

const form = ref({
  loginName: '',
  password: '',
  rememberMe: false
});

const handleLogin = async () => {

  // 实现登录逻辑，如发送请求到后端服务
  if (form.value.rememberMe) {
    Cookies.set("loginName", form.value.loginName, { expires: 30 });
    Cookies.set("password", encrypt(form.value.password), { expires: 30 });
    Cookies.set('rememberMe', form.value.rememberMe, { expires: 30 });
  } else {
    Cookies.remove("loginName");
    Cookies.remove("password");
    Cookies.remove('rememberMe');
  }

  // 加密
  form.value.password = encrypt(form.value.password);
  await store.dispatch('auth/login', form.value).then(() => {
    router.push({ name: 'Index' });
  }).catch(() => {
    // this.loading = false;
  });

};

</script>
