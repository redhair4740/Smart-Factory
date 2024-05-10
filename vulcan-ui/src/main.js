import { createApp } from 'vue'
import '@/style.css'
import App from '@/App.vue'
import router from '@/router';
import TitlePlugin from '@/plugins/title-plugin';
import { createPinia } from 'pinia'; // 引入Pinia
import { stores } from '@/store/store.js'; // 导入集中管理的store
import axios from 'axios'

import {
    // create naive ui
    create,
    // component
    NButton,
    NCard,
    NForm,
    NFormItem,
    NInput,
    NCheckbox,
    NDynamicInput,
} from 'naive-ui'

const naive = create({
    components: [NButton,NCard,NForm,NFormItem,NInput,NCheckbox,NDynamicInput]
})

// 创建Pinia实例
const pinia = createPinia();

createApp(App)
    .use(naive)
    .use(router)
    .use(TitlePlugin)
    .use(pinia)
    .provide('$axios',axios)
    .mount('#app');
