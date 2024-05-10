import { createRouter, createWebHistory } from 'vue-router';

import login from '@/views/login.vue';
import index from '@/views/index.vue';

const routes = [
    {
        path: '/login',
        name: 'login',
        component: login,
        meta: { title: '登录' }, // 为每个路由添加一个 `title` 属性
        hidden: true
    },
    {
        path: '/',
        // component: Layout,
        component: index,
        name: 'Home',
        meta: { title: '首页' },
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
