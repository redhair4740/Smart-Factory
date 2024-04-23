import { defineConfig } from 'vite'

import vue from '@vitejs/plugin-vue'
// 自动引入插件
import Components from 'unplugin-vue-components/vite'
// Naiue UI解析器
import {NaiveUiResolver} from 'unplugin-vue-components/resolvers'
import AutoImport from 'unplugin-auto-import/vite'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        AutoImport({
            imports: [
                'vue',
                {
                    'naive-ui': [
                        'useDialog',
                        'useMessage',
                        'useNotification',
                        'useLoadingBar'
                    ]
                }
            ]
        }),
  ],
})
