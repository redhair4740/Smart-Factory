import axios from 'axios';
import { ElMessage } from 'element-plus';
import Cookies from 'js-cookie';

// 创建axios实例
const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL, // 从环境变量中读取基础URL
  timeout: 10000, // 请求超时时间
  withCredentials: true, // 允许跨域携带cookie
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    'X-Requested-With': 'XMLHttpRequest'
  }
});

// 请求拦截器
service.interceptors.request.use(
  config => {
    console.log('发送请求:', config.url, config.data);
    
    // 从cookie中获取token
    const token = Cookies.get('token');
    if (token) {
      // 设置请求头
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    
    return config;
  },
  error => {
    console.error('请求错误:', error);
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  response => {
    console.log('收到响应:', response.config.url, response.data);
    
    const res = response.data;
    // 根据响应状态码判断请求是否成功
    if (res.code === 200) {
      return res;
    } else {
      ElMessage.error(res.message || '请求失败');
      return Promise.reject(new Error(res.message || '请求失败'));
    }
  },
  error => {
    console.error('响应错误:', error.config?.url, error.message);
    console.error('错误详情:', error.response?.data || error);
    
    let message = '网络请求错误';
    if (error.response) {
      switch (error.response.status) {
        case 401:
          message = '未授权，请重新登录';
          break;
        case 403:
          message = '拒绝访问';
          break;
        case 404:
          message = '请求的资源不存在';
          break;
        case 500:
          message = '服务器内部错误';
          break;
        default:
          message = `请求失败: ${error.response.status}`;
      }
    }
    ElMessage.error(message);
    return Promise.reject(error);
  }
);

export default service;