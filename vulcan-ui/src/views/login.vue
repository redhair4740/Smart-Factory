<template>
  <el-card>
    <h3 class="login-title">Login</h3>
    <el-form ref="loginForm" :rules="rules" @submit.prevent="handleSubmit">
      <el-form-item label="Username" prop="username">
        <el-input
          v-model="username"
          placeholder="Enter your username"
        ></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="password">
        <el-input
          type="password"
          v-model="password"
          placeholder="Enter your password"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">Login</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script lang="ts">
import { ref, reactive, onMounted } from "vue";
import { useRouter } from "vue-router";
export default {
  name: "Login",
  setup() {
    const username = ref("");
    const password = ref("");
    const rules = reactive({
      username: [
        {
          required: true,
          message: "Please enter your username",
          trigger: "blur",
        },
      ],
      password: [
        {
          required: true,
          message: "Please enter your password",
          trigger: "blur",
        },
      ],
    });
    const router = useRouter();
    const handleSubmit = () => {
      router.push("/index");
    };

    onMounted(() => {
      // Any initialization logic here
    });

    return {
      username,
      password,
      rules,
      handleSubmit,
    };
  },
};
</script>
