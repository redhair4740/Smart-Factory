// token.js
import { login } from '@/api/login'
import { setToken } from '@/utils/token.js'

export default {
    namespaced: true,
    state: {
        token: null,
        isAuthenticated: false,
    },
    mutations: {
        SET_TOKEN(state, token) {
            state.token = token;
            state.isAuthenticated = !!token;
        },
    },
    actions: {
        async login({ commit }, userInfo) {
            return new Promise((resolve, reject) => {
                login(userInfo).then(res => {
                    setToken(res.data.tokenValue)
                    commit('SET_TOKEN', res.data.tokenValue)
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
