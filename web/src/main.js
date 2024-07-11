import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import Header from "@/components/Header.vue";
import router from "./router";
import axios from "axios";
axios.defaults.baseURL = 'http://26.107.171.13:3008/';

const app = createApp(App)
app.component('Header', Header)
app.use(ElementPlus).use(router).use(store)
app.mount('#app')
