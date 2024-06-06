import axios from 'axios';
import { User } from '@/model/auth.ts';

const BASE_URL = 'http://localhost:8080';

export async function login(data: User) {
  try {
    const response = await axios.post(`${BASE_URL}/auth/login`, data);
    return response.data;
  } catch (error) {
    // 错误处理逻辑
    console.error('Error fetching users:', error);
    throw error;
  }
}