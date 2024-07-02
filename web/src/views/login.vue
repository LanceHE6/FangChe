<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from "element-plus";
//调用后台接口完成注册

//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)

//定义数据模型
const registerData = ref({
  account:'',
  verify_code:'',
  password:'',
})

//定义表单校验规则
const rules = ref({
  account:[
    {required:true,massage:'请输入邮箱地址',trigger:'blur'},
    {min:5,max:20,message:'请输入5~20位非空字符',trigger:'blur'}
  ],
  verify_code:[
    {required:true,massage:'请输入验证码',trigger:'blur'},
    {min:6,max:6,message:'请输入6位验证码',trigger:'blur'}
  ],
  password:[
    {required:true,massage:'请输入密码',trigger:'blur'},
    {min:5,max:16,message:'请输入5~16位非空字符',trigger:'blur'}
  ],
})

// 客户端的邮箱地址是用户输入的
// 使用async函数来发送POST请求
// 调用async函数发送验证码
//申请验证码函数
//import {userApplyService} from '@/api/user.js'
async function apply() {
  console.log(registerData.value);
  try {
    const data = registerData.value;

    // 发送POST请求到后端API
    const response = await axios.post('/api/user/register/send-code', {
      account: data.account
    });

    // 根据后端返回的code判断请求是否成功
    if (response.data.code === 200) {
      // 成功
      ElMessage.success( '申请成功');
    } else {
      // 失败
      ElMessage.error( '申请失败: 未知错误');
    }
  } catch (error) {
    // 处理错误
    ElMessage.error('发生错误: ' + error.message);
  }
}


//注册函数
import {userRegisterService} from '@/api/user.js'
const register = async () => {
  console.log(registerData.value)
  //registerData是一个响应式对象，调用时现需要加上.value
  let result = await userRegisterService(registerData.value);
  console.log(result.data)
  if(result.data.code == 200) {
    //成功
    ElMessage.success('注册成功')
    alert(result.msg ? result.massage:'注册成功');


  }else{
    //失败
    ElMessage.error('注册失败')
    alert('注册失败')
  }
}

//登录函数
import {userLoginService} from '@/api/user.js'
import axios from "axios";
const login = async() =>{
  console.log(registerData.value)
  //调用接口完成登录
  let result = await userLoginService(registerData.value);
  console.log(result)
  if(result.data.code == 200){
    alert(result.msg ? result.msg : '登录成功')
    ElMessage.success('登录成功')

  }else{
    alert('登录失败')
    ElMessage.error('登录失败')
  }
}

//定义函数，清空数据模型
const clearRegisterData = () =>{
  registerData.value = {
    account:'',
    verify_code:'',
    password:''
  }
}
</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!-- 注册表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
        <el-form-item>
          <h1>注册</h1>
        </el-form-item>
        <el-form-item prop="account">
          <el-input :prefix-icon="User" placeholder="请输入注册邮箱" v-model="registerData.account"></el-input>
        </el-form-item>
        <el-form-item prop="verify_code">
          <el-input :prefix-icon="Lock" type="verify_code" placeholder="请点击验证按钮获取验证码" v-model="registerData.verify_code"></el-input>
        </el-form-item>
        <!-- 申请发送邮箱验证码按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="apply">
            验证
          </el-button>
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
        </el-form-item>
        <!-- 注册按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="register">
            注册
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isRegister = false;clearRegisterData()">
            ← 返回
          </el-link>
        </el-form-item>
      </el-form>


      <!-- 登录表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-else :model="registerData" :rules="rules">
        <el-form-item>
          <h1>登录</h1>
        </el-form-item>
        <el-form-item prop="account">
          <el-input :prefix-icon="User" placeholder="请输入邮箱" v-model="registerData.account"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
        </el-form-item>
        <el-form-item class="flex">
          <div class="flex">
            <el-link type="primary" :underline="false">找回密码</el-link>
          </div>
        </el-form-item>

        <!-- 登录按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
        </el-form-item>

        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isRegister = true;clearRegisterData()">
            注册 →
          </el-link>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>

.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: url('@/assets/logo3.png') no-repeat 60% center / 600px auto,
    url('@/assets/login_bg.jpg') no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form
  {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title
    {
      margin: 0 auto;
    }

    .button-apply {
      width: 100%;
      padding: 10px 20px;
      border: 1px solid #ccc;
      border-radius: 5px;
      background-color: #f9f9f9;
      cursor: pointer;
      margin-bottom: 20px;

      &:hover {
        background-color: #e9e9e9;
      }
    }

    .button-register {
      width: 100%;
      padding: 15px 20px;
      border: 2px solid #007bff;
      border-radius: 7px;
      background-color: #007bff;
      color: white;
      cursor: pointer;

      &:hover {
        background-color: #0056b3;
      }
    }
    .flex
    {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>