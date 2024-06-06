// src/store/authStore.ts
import { defineStore } from 'pinia';
import { User } from '@/model/auth.ts'; // 假设你有一个 User 类型定义

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: '',
    user: {} as User,
  }),

  getters: {
    isLoggedIn(): boolean {
      return !!this.token;
    },
  },

  actions: {
    async login(data: { username: string; password: string }): Promise<void> {
      // 这里应该是你的登录逻辑，例如调用API并处理响应
      // 以下代码仅为示例
      const response = await fetch('/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });

      if (response.ok) {
        const { token, user } = await response.json();
        this.token = token;
        this.user = user;
      } else {
        throw new Error('Login failed');
      }
    },

    logout(): void {
      this.token = '';
      this.user = {};
    },
  },
});