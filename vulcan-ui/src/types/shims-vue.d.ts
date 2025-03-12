declare module '*.vue' {
  import type { DefineComponent } from 'vue'
  const component: DefineComponent<{}, {}, any>
  export default component
}

// 声明JSX命名空间，修复JSX元素隐式具有类型 "any" 的错误
declare namespace JSX {
  interface IntrinsicElements {
    [elemName: string]: any;
  }
} 