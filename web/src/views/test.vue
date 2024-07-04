<script setup>
import Header from "@/components/Header.vue";

import {onMounted, ref} from "vue";
import {CloseBold, Search} from "@element-plus/icons-vue";
import axios from "axios";
// 搜索的关键字
const search=ref('')
// 点击搜索后，进行的函数
const submit= async ()=>{
  let token = localStorage.getItem('token')
  let res=await axios.get('/question/searchQuestion',{
    headers:{
      authorization:"Bearer "+token
    },
    type:search.value,
  })
  console.log(res)
  subjects.value=res.data
}
//前端存放测试题
const subjects=ref([
  {type:'高数'},
  {type:'线代'},
])
// 点击不同的测试题
const select=(subject)=>{
  // console.log(subject.id)
  console.log('选择')
}
// 获取所有的测试题
const acquireQuestion= async ()=>{
  const token = localStorage.getItem("token")
  let res=await axios.get('/question/searchTypes',{
    headers:{
      authorization: 'Bearer '+token
    }
  })
  console.log(res)
  if(res.data.code===200){
    subjects.value=res.data.type


    console.log("获取成功")


  }else {
    console.log("获取失败")
  }

}

const deleteSubject=async (subject,event)=>{
  event.stopPropagation()
  console.log('删除')


}
// 进页面时就调用获取所有测试题的函数
onMounted(()=>
    acquireQuestion()
)
</script>

<template>

<Header></Header>
  <div class="all">
  <div class="searchB">
    <form class="searchForm">
        <input class="searchInput" v-model="search"/>
        <button class="searchButton" @click="submit">
          <el-icon size="large"><Search /></el-icon>
        </button>
    </form>
    <button class="postSubject">上传</button>
  </div>
  <div class="bottom">
    <div v-for="(subject,index) in subjects" :key="index" @click="select(subject)" class="connect">
      {{subject.type}}
      测试题
      <button @click="deleteSubject(subject,$event)" class="cButton" ><el-icon><CloseBold /></el-icon></button>
    </div>
  </div>
  </div>
</template>

<style scoped>

.all{
  display: flex;
  width: 100vw;

  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.searchB{
  display: flex;
  .postSubject{
    position: relative;

  }
}
.searchForm{
  display: flex;
  justify-content: center;
  margin-top: 2.5vh;
  width: 100vw;
  height: 5vh;
  .searchInput{
    width: 50vw;
    font-size: 2.0vh;
  }
  .searchButton{
    width: 5vh;
    height: 4.5vh;
    position: relative;
    top: 0.25vh;
    right: 6vh;
    border: none;
    background-color: white;

  }
}
.searchButton:hover{
  cursor: pointer;
}

.bottom{
  margin-top: 2.5vh;
  width: 73vw;
  max-height: 200vh;
  padding: 1.5vh;
  display: flex;
  border: 0.1vh solid whitesmoke;
  flex-wrap: wrap;
  .connect{
    width: 17vw;
    height: 15vh;
    margin-left: 0.6vw;
    margin-top: 2.5vh;
    background-color: white;
    transition: box-shadow 0.5s ease;
    border-radius: 2vh;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    .cButton{
      position: absolute;
      top: 1.5vh;
      right: 0.9vw;
      border: none;
      font-size: 3vh;
      background-color: white;
      color: red;
    }
  }
}
.cButton:hover{
  cursor: pointer;
  background-color: #181818;
}
.connect:hover{
  box-shadow:  0 0.4vh 0.8vh gray;
  cursor: pointer;
}
</style>