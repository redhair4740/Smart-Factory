// src/store/authStore.ts
import { defineStore } from 'pinia';
import { User } from '@/model/auth.ts';
import { login as apiLogin } from '@/api/auth.ts';
import Cookies from 'js-cookie';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    user: JSON.parse(localStorage.getItem('user') || '{}') as User,
  }),

  getters: {
    isLoggedIn(): boolean {
      return !!this.token;
    },
    isAuthenticated(): boolean {
      return !!this.token;
    }
  },

  actions: {
    async login(data: User): Promise<boolean> {
      try {
        const response = await apiLogin(data);
        if (response && response.data && response.data.code === 200) {
          const { token, user } = response.data;
          this.token = token;
          this.user = user;
          
          // 保存到本地存储
          localStorage.setItem('token', token);
          localStorage.setItem('user', JSON.stringify(user));
          Cookies.set('token', token, { expires: 7 }); // 7天过期
          
          return true;
        }
        return false;
      } catch (error) {
        console.error('Login failed:', error);
        return false;
      }
    },

    logout(): void {
      this.token = '';
      this.user = {} as User;
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      Cookies.remove('token');
    },
  },
});