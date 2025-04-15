import request from '@/utils/request';
import { User } from '@/model/auth.ts';
import { encrypt, getTimestamp, generateSignature } from '@/utils/encryption';

// 登录函数
export async function login(data: User) {
  try {
    console.log('尝试登录，用户名:', data.loginName);

    // 注意：这里假设密码已经在login.tsx中加密了，所以不再重复加密

    // 构建请求参数
    const timestamp = getTimestamp();
    const requestData = {
      loginName: data.loginName,
      password: data.password, // 已加密的密码
      loginType: data.loginType || 'PC',
    };

    // 生成签名
    const sign = generateSignature(requestData, timestamp);

    // 添加签名到请求
    const requestWithSign = {
      ...requestData,
      sign
    };

    console.log('发送登录请求:', {
      ...requestWithSign,
      password: '********' // 日志中隐藏密码
    });

    // 使用正确的路径
    const loginPath = '/auth/login';

    // 发送登录请求到后端API
    const response = await request({
      url: loginPath,
      method: 'post',
      data: requestWithSign
    });

    return response;
  } catch (error: unknown) {
    console.error('登录失败:', error);
    // 重新抛出错误，以便调用者处理
    throw error;
  }
}