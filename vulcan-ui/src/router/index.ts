// src/router/index.ts
import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Login from "@/views/login.tsx";
import AppLayout from "@/components/layout/AppLayout.vue";

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
    path: "/",
    component: AppLayout,
    redirect: "/dashboard",
    children: [
      {
        path: "dashboard",
        name: "Dashboard",
        component: () => import("@/views/dashboard/index.vue"),
        meta: {
          title: "仪表盘",
          requiresAuth: true
        }
      }
    ]
  },
  {
    path: "/:pathMatch(.*)*",
    name: "NotFound",
    component: () => import("@/views/error/404.vue"),
    meta: {
      title: "404",
      requiresAuth: false
    }
  }
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
