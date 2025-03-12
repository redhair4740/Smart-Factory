import JSEncrypt from 'jsencrypt';

// RSA公钥，与后端私钥对应
const PUBLIC_KEY = `-----BEGIN PUBLIC KEY-----
MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMnkKpVQIABmY/Y7IBYy9cMQAA5s1ytj
3T12f4Uccxa3RvDZ88cKWIq5niUOQGYcpa/OOSuRc2wW/mjTUNUFhN0CAwEAAQ==
-----END PUBLIC KEY-----`;

/**
 * RSA加密
 * @param text 要加密的文本
 * @returns 加密后的Base64编码字符串
 */
export function encrypt(text: string): string {
  if (!text) return '';
  
  const encryptor = new JSEncrypt();
  encryptor.setPublicKey(PUBLIC_KEY);
  
  // 使用RSA加密，返回Base64编码的加密字符串
  const encrypted = encryptor.encrypt(text);
  return encrypted || '';
}