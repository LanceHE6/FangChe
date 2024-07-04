<script setup>

import {onMounted, reactive, ref} from "vue";
import router from "@/router/index.js";
import HeaderMenu from "@/components/HeaderMenu.vue";
import {Eventbus} from './event-bus.js'

const centerDialogVisible = ref(false)
const testContents =reactive([
  {title:'java',label1: 'aaa',label2:'bbb',label3:'ccc',label4:'ddd'},
  {title:'c++',label1: '444',label2:'333',label3:'222',label4:'111'},
])
const radio=ref([])

const cancel= async ()=>{
  await router.push('/test')
}
// 点击提交会对题目答案进行判断，出现对了几个，错了几个
// 没做完，不对
const judge=async ()=>{
  if(radio.length===null){
    alert('请答题')
  }
  console.log(radio.value)


}

onMounted(()=>{

})

</script>

<template>
  <HeaderMenu/>
<div style="width: 100vw;padding-top: 12vh;display: flex;justify-content: center">
  <div class="content">
    <div class="example" v-for="(test,index) in testContents" :key="index">
      {{test.title}}
      <div>
        <el-radio-group v-model="radio[index]">
          <el-radio :label="1">A{{test.label1}}</el-radio>
          <el-radio :label="2">B{{test.label2}}</el-radio>
          <el-radio :label="3">C{{test.label3}}</el-radio>
          <el-radio :label="4">D{{test.label4}}</el-radio>
        </el-radio-group>
      </div>
    </div>
    <div class="bottom">
      <button class="button-end" @click="centerDialogVisible = true">取消</button>
      <el-dialog v-model="centerDialogVisible" title="提醒" width="30%" center>
    <span>   确认要退出吗</span>
        <template #footer>
      <span>
        <el-button @click="centerDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="cancel">确认</el-button>
      </span>
        </template>
      </el-dialog>
      <button class="button-end" @click="judge">提交</button>
    </div>
  </div>
</div>
</template>

<style scoped>
.content{
  padding-top: 5vh;
  width: 70%;
  max-height: 200vh;
  box-shadow: 0.15vh 0.25vh 0.5vh 0.5vh;
  border: 0.1vh solid darkgray;
  border-radius: 1.5vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  .example{
    font-size: 2.5vh;
    margin-bottom: 2vh;
    width: 75%;
    max-height: 25vh;
  }
  .bottom{
    margin-top: 2vh;
    margin-bottom: 3vh;
    width: 57%;
    display: flex;
    justify-content: space-between;
    .button-end{
      width: 12vw;
      height: 5vh;

    }
  }
}
.button-end:hover{
  cursor: pointer;
}
.button-end:active{
  background-color: white;
}
</style>