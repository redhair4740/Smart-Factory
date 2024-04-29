import { createRouter, createWebHistory } from 'vue-router';

import Login from '@/views/login.vue';
import Index from '@/views/index.vue';

const routes = [
    {
        path: '/login',
        component: Login,
        meta: { title: '登录' }, // 为每个路由添加一个 `title` 属性
        hidden: true
    },
    {
        path: '/',
        // component: Layout,
        component: Index,
        name: Index,
        meta: { title: '首页' },
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
