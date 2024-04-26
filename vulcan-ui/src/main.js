import { createApp } from 'vue'
import '@/style.css'
import App from '@/App.vue'
import router from '@/router';
import TitlePlugin from '@/plugins/title-plugin';
import store from '@/store'; // 导入创建的 Store
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

createApp(App)
    .use(naive)
    .use(router)
    .use(TitlePlugin)
    .use(store)
    .provide('$axios',axios)
    .mount('#app');
