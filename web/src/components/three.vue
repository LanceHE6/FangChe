<script setup lang="ts">
import {reactive, ref, defineProps, onMounted, computed} from 'vue'
import type { ComponentSize, FormProps } from 'element-plus'
import axios from "axios";

const labelPosition = ref<FormProps['labelPosition']>('top')
const account=localStorage.getItem('user.account')
const sizeForm = reactive({
  nickname:account,
  delivery: false,
  signature:'',
  gender:'',
  introduction:'',
})
let Sex=ref('保密')
const isState = ref(false);


const buttonText = computed(() => {
  return isState.value ? '保存' : '修改';
});
// 定义一个方法来切换按钮文本

const change=()=>{
  isState.value = !isState.value;


  if(!isState.value){
    submit()
    if(sizeForm.gender==='-1'){
      Sex.value="保密"

    }else if(sizeForm.gender==='0'){
      Sex.value='女'
      console.log(Sex.value)
    }else if(sizeForm.gender==='1'){
      Sex.value='男'
    }
  }
}
const submit=()=>{
  console.log(sizeForm)
  const token = localStorage.getItem("token")
  let res=axios.put('/api/user/update',{
    headers: {
      "Authorization": "Bearer "+token
    }
  })
  console.log(res)
  if(res.data.code===200){
    alert("修改成功")
  }else {
    alert("修改失败")
  }
}
</script>

<template>
  <div class="three">
    基本设置
    <button class="topLeft" @click="change">{{buttonText}}</button>
    <el-form size="large"
        style="max-width: 50vw"
        :model="sizeForm"
        label-width="auto"
        :label-position="labelPosition"
    >
      <el-form-item label="昵称">
        <el-input v-if="isState" v-model="sizeForm.nickname" />
      </el-form-item>
      <div class="writeText" v-if="!isState">{{sizeForm.nickname}}</div>
      <hr>
      <el-form-item label="个性签名">
        <el-input v-if="isState" v-model="sizeForm.signature" maxlength="15" show-word-limit></el-input>
      </el-form-item>
      <div class="writeText" v-if="!isState">{{sizeForm.signature}}</div>
      <hr>
      <el-form-item label="性别">
        <el-radio-group  v-if="isState" v-model="sizeForm.gender">
          <el-radio value="-1">保密</el-radio>
          <el-radio value="1">男</el-radio>
          <el-radio value="0">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <div class="writeText" v-if="!isState">{{Sex}}</div>
      <hr>
      <el-form-item label="简介">
        <el-input v-if="isState" v-model="sizeForm.introduction" type="textarea" maxlength="100" show-word-limit autosize></el-input>
      </el-form-item>
      <div class="writeText" v-if="!isState">{{sizeForm.introduction}}</div>
      <hr>
    </el-form>

  </div>
</template>

<style scoped>
.topLeft{
  margin-left: 33vw;
  border: none;
  background-color: white;
  color: red;
  font-size: 3vh;
}
.topLeft:hover{
  cursor: pointer;
}
.three{
  font-size: 4vh;
  width: 100%;
  max-height:100vh ;
}
.writeText{
  font-size: 2.5vh;
}
.el-form-item--large{
  --font-size: 2vh;
  margin-bottom: 1vh;
  margin-top: 1.5vh;
}
.el-form{
  margin-top: 3.5vh;
  width: 60vw;
}
.el-input--large{
  font-size: 2.5vh;
}
.el-input--large{
  border: none;
  outline: none;
}
.el-input__wrapper{
  border: none;
  outline: none;
}
.el-radio__label{
  font-size: 2vh;
}
</style>