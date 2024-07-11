import {createRouter,createWebHashHistory} from "vue-router";
import main from "../views/main.vue";
import user from "../views/userCenter.vue";
import login from "../views/login.vue";
import class_page from "../views/class_page.vue";


const routes=[
    {
        path:"/",
        name:"main",
        component:main,

    },
    {
        path: "/class_page",
        name:"class_page",
        component: class_page,

    },
    {
        path: "/user",
        name:"user",
        component: user,

    },
    {
        path: "/login",
        name:"login",
        component: login,

    },

]

//配置信息中需要页面的相关配置
const router=createRouter({
    history:createWebHashHistory(),
    routes
})
export default router;