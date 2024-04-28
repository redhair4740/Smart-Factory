// token.js
import { login } from '@/api/login'
import { setToken } from '@/utils/token.js'
import store from "@/store/index.js";

export default {
    namespaced: true,
    state: {
        isAuthenticated: false,
    },
    mutations: {
        SET_AUTHENTICATED(state, value) {
            state.isAuthenticated = value;
        },
    },
    actions: {
        async login({ commit }, userInfo) {
            return new Promise((resolve, reject) => {
                login(userInfo).then(res => {
                    setToken(res.data.tokenValue)
                    commit('SET_AUTHENTICATED', true)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },
        logout({ commit }) {
            commit('setToken', null);
        },
    },
    getters: {
        getToken(state) {
            return state.token;
        },
        isAuthenticated(state) {
            return state.isAuthenticated;
        },
    },
};

// Vue Router
router.beforeEach((to, from, next) => {
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
    const isAuthenticated = store.state.isAuthenticated;

    if (requiresAuth && !isAuthenticated) {
        // 用户未登录且访问需要授权的页面，保存目标路由并跳转到登录页
        store.commit('SET_REDIRECT_PATH', to.fullPath);
        next({ name: 'Login' });
    } else {
        next(); // 其他情况正常放行
    }
});
