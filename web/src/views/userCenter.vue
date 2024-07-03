<script setup>
import {onBeforeMount, onMounted, reactive} from "vue";
import router from "@/router/index.js";

import axios from "axios";

const errorHandler = () => true
let user=reactive({
  username:'',
  imgPath:'',
  account:'',
  file:''
})


const ziyems=[
  {name:'我的课程',href:"/one"},
  {name:'我的测评',href: "/two"},
  {name: '基本设置',href: "/three"},
  {name:'安全设置',href: "/four"},
]

const toChange=(ziyem)=>{
  router.push(ziyem.href)
}
const getUserMessage=async()=>{
  const token = localStorage.getItem("token")
  let res = await axios.get('/api/user/info',{
    headers: {
      "Authorization": "Bearer "+token
    }
  })
  console.log('res',res)
  user.username=res.data.nickname;
  user.account=res.data.account;
  localStorage.setItem(user.account)
  user.imgPath=JSON.parse(res.data.avatar);
  user.imgPath=btoa(
      new Uint8Array(res.data).reduce(
          (data, byte) => data + String.fromCharCode(byte),
          ''
      )
  )

}
const postAvatar= async ()=>{
  document.querySelector('input[type="file"]').click();
}

const handleFileChange = (e) => {
  selectedFile.value = e.target.files[0];
  if (selectedFile.value) {
    uploadImage(selectedFile.value);
  }
};
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
  if(res.data.code==200){{
    alert('上传成功')
  }}else {
    alert('上传失败')
  }
}
onBeforeMount(
    getUserMessage
)
</script>

<template>
  <div class="all">
  <div class="demo-type">
    <el-avatar @click="postAvatar" :size="100" src="https://empty" @error="errorHandler">
      <img src='../imgs/3.png'/>
      <input type="file" @change="handleFileChange" style="display: none">
    </el-avatar>
    <div style="margin-top: 1vh;font-size: 3vh">{{user.username}}</div>
  </div>


  <div class="bottom">
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
  position: relative;
  bottom: 8vh;
}
.demo-type{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 25vh;
  background-color: #181818;
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