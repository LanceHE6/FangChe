<script setup>
import {onBeforeMount, onMounted, reactive} from "vue";
import router from "@/router/index.js";

import axios from "axios";
import HeaderMenu from "@/components/HeaderMenu.vue";

const errorHandler = () => true
let user=reactive({
  username:'',
  imgPath:'',
  account:'',
  identity:'',
  gender:''
})


const ziyems=[
  {name:'我的课程',href:"one"},
  {name:'我的测评',href: "two"},
  {name: '基本设置',href: "three"},
  {name:'安全设置',href: "four"},
]

const toChange=(ziyem)=>{
  router.push(ziyem.href)
}
const getUserMessage=async()=>{
  const token = localStorage.getItem("token")
  console.log(token)
  let res = await axios.get('/api/user/info',{
    headers: {
      "Authorization": 'bearer '+token,
    }
  })
  console.log(res)
  user.username=res.data.data.nickname;
  user.account=res.data.data.account;
  user.imgPath=res.data.data.avatar;
  user.identity=res.data.data.role;
  user.gender=res.data.data.gender;
  console.log(user)
}

//上传头像
// const postAvatar= async ()=>{
//   document.querySelector('input[type="file"]').click();
// }
//
// const handleFileChange = (e) => {
//   selectedFile.value = e.target.files[0];
//   if (selectedFile.value) {
//     uploadImage(selectedFile.value);
//   }
// };
const uploadImage = async ()=>{

  const formData = new FormData();
  formData.append('image',file)


  const token = localStorage.getItem("token")

  let res=await axios.post('/api/user/set-avatar',{
    headers: {
      "Authorization": "Bearer "+token,
      formData
    }
  })
  if(res.data.code===200){{
    alert('上传成功')

  }}else {
    alert('上传失败')
  }
}
onBeforeMount(()=>{
  getUserMessage()
      console.log("获取数据")
}


)
</script>

<template>
  <HeaderMenu></HeaderMenu>、
  <div class="all">
  <div class="demo-type">
    <el-avatar @click="postAvatar" :size="100" src="{{user.imgPath}}" @error="errorHandler">
      <input type="file" @change="handleFileChange" style="display: none">
    </el-avatar>
    <div style=" max-width: 10vw;max-height: 4vh;margin-top: 1vh;font-size: 3vh">
      {{user.username===null?user.account:user.username}}
    </div>
  </div>


  <div class="bottom">
    <button @click="getUserMessage">获取数据</button>
      <el-container >
        <div class="aside">
          <div v-for="(ziyem,index) in ziyems" :key="index" @click="toChange(ziyem)">
          <button style="width: 12vw;height: 6vh;font-size: 3vh;margin-bottom: 2vh;border:0.1vh solid whitesmoke ">
            {{ziyem.name}}
          </button>
          </div>
        </div>
        <el-main class="right">
          <router-view></router-view>
        </el-main>
      </el-container>

  </div>
  </div>
</template>

<style scoped>
.all{
  padding-top: 10vh;
}
.demo-type{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 25vh;
  background-color: green;
}
.bottom{
  width: 100vw;
  height: 50vh;
  padding-top:2vh ;
  padding-left: 12vw;
  padding-right: 13vw;
}
.aside{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border: 0.1vw solid whitesmoke;
  width: 18vw;
  height: 50vh;
}
button:hover{
  cursor: pointer;
  background-color: #e0e0e0;
}
.right{
  width: 50vw;
  border: 0.1vh solid whitesmoke;
  max-height: 500vh;
  padding: 0;
  margin-left: 1vw;
}
</style>