import { defineConfig } from 'vite'

import vue from '@vitejs/plugin-vue'
// 自动引入插件
import Components from 'unplugin-vue-components/vite'
// Naiue UI解析器
import {NaiveUiResolver} from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
      vue(),
      Components({
        dts: true, // ts 环境下要启用
        resolvers: [NaiveUiResolver()],
        dirs: ['src/components', 'src/layouts'], // 扫描的文件夹,引入自定义组件
      })
  ],
})
