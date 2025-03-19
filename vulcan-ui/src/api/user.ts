import request from '@/utils/request';
import { User } from '@/model/auth.ts';
import { encrypt, getTimestamp, generateSignature } from '@/utils/encryption';
import { ElMessage } from 'element-plus';

/**
 * 用户注册
 * @param data 用户注册信息
 * @returns 注册结果
 */
export async function register(data: User) {
  try {
    console.log('尝试注册，用户名:', data.loginName);

    // 对密码进行加密
    const encryptedPassword = encrypt(data.password || '');
    if (!encryptedPassword) {
      throw new Error('密码加密失败');
    }
    
    // 构建请求参数
    const timestamp = getTimestamp();
    const requestData = {
      loginName: data.loginName,
      password: encryptedPassword,
      name: data.name || data.loginName,
      email: data.email,
      phone: data.phone,
      timestamp: timestamp,
    };
    
    // 生成签名
    const sign = generateSignature(requestData, timestamp);
    
    // 添加签名到请求
    const requestWithSign = {
      ...requestData,
      sign
    };
    
    console.log('发送注册请求:', {
      ...requestWithSign,
      password: '********' // 日志中隐藏密码
    });

    // 发送注册请求到后端API
    const response = await request({
      url: '/sys/user/register',
      method: 'post',
      data: requestWithSign
    });

    return response;
  } catch (error: unknown) {
    console.error('注册失败:', error);
    // 若错误中包含Token信息，可能是权限问题
    if (String(error).includes('token')) {
      ElMessage.error('注册服务暂时不可用，请稍后再试');
    }
    throw error;
  }
}

/**
 * 检查用户名是否可用
 * @param loginName 用户名
 * @returns 是否可用
 */
export async function checkUsername(loginName: string) {
  try {
    console.log('检查用户名是否可用:', loginName);
    
    // 添加时间戳避免缓存
    const timestamp = new Date().getTime();
    
    const response = await request({
      url: '/sys/user/check-username',
      method: 'get',
      params: { loginName, _t: timestamp }
    });
    
    return response;
  } catch (error) {
    console.error('检查用户名失败:', error);
    
    // 如果是权限错误，使用模拟响应，避免阻塞用户注册
    if (String(error).includes('token') || String(error).includes('登录')) {
      console.warn('权限验证失败，返回模拟响应');
      return {
        code: 200,
        data: true, // 默认假设用户名可用
        message: '用户名检查服务暂不可用，请在注册后验证'
      };
    }
    
    throw error;
  }
} 