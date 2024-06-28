import { createRouter, createWebHistory } from 'vue-router'
import Main from '../views/main.vue'
// import About from ''

const routes = [
    {
        path: '/',
        name: 'Main',
        component: Main
    },
    // {
    //     path: '/about',
    //     name: 'About',
    //     component: About
    // }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router