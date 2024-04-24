import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router';
import TitlePlugin from './plugins/title-plugin';

createApp(App)
    .use(router)
    .use(TitlePlugin)
    .mount('#app');
