import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// import ElementPlusIconsVue from '@element-plus/icons-vue'
import Header from "@/components/Header.vue";
import router from "./router";
import axios from "axios";

import 'core-js/stable';
import 'regenerator-runtime/runtime';

axios.defaults.baseURL = 'http://26.107.171.13:8080/';

const app = createApp(App)
app.component('Header', Header)
app.use(ElementPlus).use(router).use(store)
app.mount('#app')
