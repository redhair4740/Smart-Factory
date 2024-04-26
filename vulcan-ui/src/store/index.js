// index.js
import { createStore } from 'vuex';
import auth from './modules/auth.js'

export default createStore({
    // 其他配置项（如 state、mutations、actions 等）
    modules: {
        auth
    },
});