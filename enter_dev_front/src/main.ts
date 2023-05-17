import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vue3videoPlay from "vue3-video-play"; // 引入组件
import "vue3-video-play/dist/style.css"; // 引入css
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'


const app = createApp(App).use(store).use(ElementPlus, { size: 'small', zIndex: 3000}).use(vue3videoPlay)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(router).mount('#app')
