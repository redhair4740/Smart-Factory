// src/store/authStore.ts
import { defineStore } from 'pinia';
import { User } from '@/model/auth.ts';
import { login as apiLogin } from '@/api/auth.ts';
import Cookies from 'js-cookie';

// 定义响应类型
interface ApiResponse {
  code: number;
  msg?: string;
  message?: string;
  data?: any;
  tokenValue?: string;
  token?: string;
  user?: User;
  [key: string]: any;
}

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
        console.log('AuthStore: 调用登录API');
        // 使用unknown作为中间类型，避免类型错误
        const apiResponse = await apiLogin(data) as unknown;
        const response = apiResponse as ApiResponse;

        // 处理不同格式的响应
        if (response) {
          console.log('AuthStore: 收到登录响应', response);
          let tokenInfo: ApiResponse | null = null;
          
          // 处理Sa-Token响应格式
          if (response.code === 200 && response.data) {
            tokenInfo = response.data;
          } else if (response.code === 200 && response.msg === 'ok') {
            // 另一种可能的成功格式
            tokenInfo = response;
          }
          
          if (tokenInfo) {
            // 提取token和用户信息
            const token = tokenInfo.tokenValue || tokenInfo.token;
            
            if (token) {
              // 保存token
              this.token = token;
              
              // 保存用户信息（如果有）
              if (tokenInfo.user) {
                this.user = tokenInfo.user;
              } else {
                // 如果响应中没有完整用户信息，至少保存用户名
                this.user = {
                  ...this.user,
                  loginName: data.loginName
                };
              }
              
              // 持久化存储
              localStorage.setItem('token', token);
              localStorage.setItem('user', JSON.stringify(this.user));
              Cookies.set('token', token, { expires: 7 }); // 7天过期
              
              return true;
            }
          }
        }
        
        console.error('AuthStore: 登录响应格式不正确', response);
        return false;
      } catch (error) {
        console.error('AuthStore: 登录失败', error);
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