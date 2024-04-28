import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    {
        path: '/login',
        component: () => import('@/views/login.vue'),
        meta: { title: '登录' }, // 为每个路由添加一个 `title` 属性
        hidden: true
    },
    {
        path: '',
        // component: Layout,
        redirect: 'index',
        children: [
            {
                path: 'index',
                component: () => import('@/views/index.vue'),
                name: 'Index',
                meta: { title: '首页', icon: 'dashboard', affix: true }
            }
        ]
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
