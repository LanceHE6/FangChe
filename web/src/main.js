import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import Header from "@/components/Header.vue";
import router from "@/router/routes";
import axios from "axios";
axios.defaults.baseURL = 'http://192.168.151.155:8080/';

const app = createApp(App)
app.component('Header', Header)
app.use(ElementPlus).use(router)
app.mount('#app')
