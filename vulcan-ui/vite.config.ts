import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import AutoImport from "unplugin-auto-import/vite"; //按需自动加载API插件
import Components from "unplugin-vue-components/vite";
import * as path from "path"; // 需引入
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  resolve: {
    alias: [{ find: "@", replacement: path.resolve(__dirname, "src") }], // 设置别名
  },
});
