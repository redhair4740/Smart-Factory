import request from '@/utils/request';
import { User } from '@/model/auth.ts';

// 登录函数
export async function login(data: User) {
  try {
    console.log('尝试登录，用户名:', data.loginName);

    // 使用正确的路径
    const loginPath = '/auth/login';
    console.log('使用登录路径:', loginPath);

    // 发送登录请求到后端API
    const response = await request({
      url: loginPath,
      method: 'post',
      data: {
        loginName: data.loginName,
        password: data.password,
        loginType: data.loginType
      }
    });

    return response;
  } catch (error: unknown) {
    console.error('登录失败:', error);
  }
}