import axios from 'axios';
import { User } from '@/model/auth.ts';

// 开发环境API基础URL
const BASE_URL = 'http://localhost:8080';

// 模拟登录响应，实际项目中应该连接真实后端
export async function login(data: User) {
  try {
    // 实际项目中应该使用真实API
    // const response = await axios.post(`${BASE_URL}/auth/login`, data);
    
    // 模拟登录成功响应
    return {
      code: 200,
      message: '登录成功',
      data: {
        token: 'mock-token-' + Date.now(),
        user: {
          id: 1,
          loginName: data.loginName,
          name: '测试用户',
          email: 'test@example.com',
          plantCode: 'PLANT001',
          superAdminFlag: 1
        }
      }
    };
  } catch (error) {
    console.error('登录失败:', error);
    throw error;
  }
}