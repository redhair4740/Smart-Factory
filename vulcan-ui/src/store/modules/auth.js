// auth.js
import { login } from '@/api/login'

export default {
    namespaced: true,
    state: {
        token: null,
        isAuthenticated: false,
    },
    mutations: {
        setToken(state, token) {
            state.token = token;
            state.isAuthenticated = !!token;
        },
    },
    actions: {
        async login({ commit }, userInfo) {
            return new Promise((resolve, reject) => {
                login(userInfo).then(res => {
                    // setToken(res.token)
                    // commit('SET_TOKEN', res.token)
                    // resolve()
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
