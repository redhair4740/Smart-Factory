import apiClient from '@/api/api'

// 登录方法
export function login(userinfo) {
    return apiClient.post('/auth/login', userinfo);
}