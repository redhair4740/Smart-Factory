// src/router/index.ts
import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Index from "@/views/index.vue";
import Login from "@/views/login.tsx";

// 定义路由配置数组
const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    redirect: "/login"
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    meta: {
      requiresAuth: false,
      title: "登录"
    }
  },
  {
    path: "/index",
    name: "Index",
    component: Index,
    meta: {
      requiresAuth: true,
      title: "首页"
    }
  }
];

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
