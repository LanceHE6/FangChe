import {createRouter,createWebHashHistory} from "vue-router";
import main from "../views/main.vue";
import user from "../views/userCenter.vue";
import login from "../views/login.vue";

const routes=[
    {
        path:"/",
        name:"main",
        component:main,

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
    {
      path: "/evaluation"  ,
        name: "evaluation",
        component:()=>import('../views/evaluation.vue'),
    },
    {
        path: "/study",
        name: "study",
        component: ()=>import('../views/study.vue'),
        redirect:"/study/zaixian",
        children:[{
            path:'zaixian',
            name:'zaixiankec',
            component:()=>
                import('../views/zaixian.vue'),
        },
            {
                path: "studyPath",
                name: "studyPath",
                component:()=>import('../views/studyPath.vue'),
            }]
    }

]

//配置信息中需要页面的相关配置
const router=createRouter({
    history:createWebHashHistory(),
    routes
})
export default router;