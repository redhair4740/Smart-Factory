import { createRouter, createWebHistory } from 'vue-router';
import Login from './../components/Login.vue';

const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login,
        meta: { title: '登录' } // 为每个路由添加一个 `title` 属性
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
