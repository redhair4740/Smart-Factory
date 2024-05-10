import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
    state: () => ({
        isAuthenticated: false,
        user: null,
    }),
    getters: {
        isAuthenticated: () => state => state.isAuthenticated,
        user: () => state => state.user,
    },
    actions: {
        async login(userInfo) {
            // 假设login是异步API调用
            const response = await login(userInfo);
            this.setUser(response.user);
            this.setAuthenticated(true);
        },
        logout() {
            this.setUser(null);
            this.setAuthenticated(false);
        },
        setAuthenticated(value) {
            this.$state.isAuthenticated = value;
        },
        setUser(user) {
            this.$state.user = user;
        },
    },
});
