import { useRouter } from 'vue-router';
import router from "../router/index.js";

export default {
    install(app) {
        app.provide('updatePageTitle', (title) => {
            document.title = title;
        });

        app.config.globalProperties.$updatePageTitle = function (title) {
            document.title = title;
        };

        const updateTitle = () => {
            const router = useRouter();
            const route = router.currentRoute.value;
            const pageTitle = route.meta.title ?? 'SmartFactory'; // 如果没有定义 `meta.title`，使用默认标题
            document.title = pageTitle;
        };

        router.afterEach(updateTitle); // 监听路由切换后的钩子
    },
};
