import JSEncrypt from 'jsencrypt';

// RSA公钥 - 实际项目中应该从后端获取或配置文件读取
const PUBLIC_KEY = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgax6uY6BIWCykCyVhlTpaC8jdkr7GzhpOkhzdZYi/sRr7kBy41x7o8xDp4e5NpWTWF49EQxuRKObpNOfLXugYvUKxgc9UY8vWILjOg3oyN+GNKMxZAnOPPbxS2eNFjZYQleM/y8MyVdKqxfnS4Dtfe2Tj0VR63rQ2IAZ5YCBO8wIDAQAB';

/**
 * RSA加密函数
 * @param text 要加密的文本
 * @returns 加密后的文本
 */
export function encrypt(text: string): string {
  if (!text) return '';
  
  const encryptor = new JSEncrypt();
  encryptor.setPublicKey(PUBLIC_KEY);
  
  // JSEncrypt一次加密长度有限，对于较长的文本可能需要分段加密
  const encrypted = encryptor.encrypt(text);
  if (!encrypted) {
    console.error('加密失败');
    return '';
  }
  
  return encrypted;
}

/**
 * Base64编码函数（用于简单的混淆，不是真正的加密）
 * @param text 要编码的文本
 * @returns 编码后的文本
 */
export function base64Encode(text: string): string {
  return window.btoa(encodeURIComponent(text));
}

/**
 * 获取系统时间戳，用于签名
 * @returns 当前时间戳
 */
export function getTimestamp(): number {
  return Date.now();
}

/**
 * 生成签名（用于请求防篡改）
 * @param params 请求参数
 * @param timestamp 时间戳
 * @returns 签名字符串
 */
export function generateSignature(params: Record<string, any>, timestamp: number): string {
  // 将参数按键名排序
  const keys = Object.keys(params).sort();
  
  // 构建签名字符串
  let signStr = '';
  keys.forEach(key => {
    if (params[key] !== null && params[key] !== undefined && key !== 'sign') {
      signStr += `${key}=${params[key]}&`;
    }
  });
  
  // 添加时间戳
  signStr += `timestamp=${timestamp}`;
  
  // 进行Base64编码作为签名（实际项目中应使用更安全的算法）
  return base64Encode(signStr);
}