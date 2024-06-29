<script setup>
import {reactive, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";
const router = useRouter();
let dialogFormVisible = ref(false)
const user =reactive({
  account:"",
  password:""
})
const login =async()=>{
  console.log(user)

  let result =await axios.post("/api/user/login",{
    account:user.account,
    password:user.password,
  });
  console.log(result)
  if(result.data.code==200){
    // console.log("登录成功")
    await router.push('/')
  }
  else {
    console.log("登录失败")
    alert("登录失败")
  }
  user.password=''
  user.account=''
}

const form = reactive({
  account: "",
  newpassword:"",
  verifycode:"",
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})
const submitNews=async()=>{
  console.log(form)
  let result =await axios.post("/api/user/reset-password/verify-code",{
    account:form.account,
    new_password:form.newpassword,
    verify_code:form.verifycode,
  });
  console.log(result)
  if(result.data.code==200){
    console.log("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa")

  }
  else if(result.data.code==400){
    alert("验证码错误")
    console.log("bbbbbbbbbbbbbbbbbbbbbb")
  }
  // dialogFormVisible=false

  window.location.reload()
}
const sendyan=async()=>{
  console.log(form)
  let result =await axios.post("/api/user/reset-password/send-code",{
    account:form.account,
  });
  console.log(result)
  if(result.data.code==200){
    console.log("发送成功")
  }

}
</script>

<script>
import { reactive } from 'vue';
import axios from 'axios';

export default {
  setup() {
    const user = reactive({
      email: "",
      password: ""
    });

    const errors = reactive({
      email: null,
      password: null
    });

    const register = async () => {

      errors.email = null;
      errors.password = null;


      if (user.email === '') {
        errors.email = '邮箱不能为空';
        return;
      }

      if (user.password === '') {
        errors.password = '密码不能为空';
        return;
      }

      try {
        let result = await axios.post("/api/user/register", user);
        if (result.data.code == 200) {
          console.log("注册成功");
        } else {
          console.log("注册失败");
        }
      } catch (error) {
        console.log("发生错误", error);

      }
    };

    return {
      user,
      errors,
      register
    };
  }
}
</script>





<template>
  <!--  <p>{{$route}}</p>-->
  <div class="back">

    <div class="left">
      <img src="@/imgs/1.webp">
    </div>
    <div class="right">
      <el-divider class="right-center">
        <el-form class="Rcenter">
          <h2 style="margin-left: 1vw;color: mediumslateblue">欢迎登录方车</h2>

          <br>
          <el-input
              v-model="user.account"
              style="width: 16vw;height: 5vh"
              placeholder="请输入账号"
              clearable
          />
          <br>

          <el-input
              v-model="user.password"
              style="width: 16vw;height: 5vh;margin-top: 2vh"
              type="password"
              placeholder="请输入密码"
              show-password
          />
          <br>
          <el-button type="info" @click="login">登录</el-button>
          <el-button style="margin-left: 2vw" @click="register">注册</el-button>
        </el-form>


      </el-divider>
      <div>
        <el-button class="re-password" plain @click="dialogFormVisible = true">
          忘记密码？
        </el-button>
      </div>
      <el-dialog v-model="dialogFormVisible" title="重置密码" width="500">
        <el-form :model="form">
          <el-form-item label="请输入邮箱" :label-width="formLabelWidth">
            <el-input v-model="form.account" autocomplete="off" />
          </el-form-item>
          <el-form-item>
            <el-input v-model="form.verifycode" style="width: 60%;height:3vh "></el-input>
            <el-button style="height: 3vh;margin-top: 0" @click="sendyan">发送验证码</el-button>
          </el-form-item>
          <el-form-item>
            <el-input v-model="form.newpassword" placeholder="请输入新密码"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click="submitNews">
              确认
            </el-button>
          </div>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<style scoped>
.back{
  background: white;
  height: 100vh;
  width: 100vw;
  background-image: url("@/imgs/2.jpg");
  background-size: cover;
}
.left{
  margin-top: 22vh;
  margin-left:15vw ;
}
.right{
  position: fixed;
  top: 30vh;
  right: 15vw;
  width: 25vw;
  height: 35vh;
  background-color: white ;

}
.right-center{
  padding-top: 16vh;
  width: 25vw;
  height: 35vh;
  background-color: white;
}
.Rcenter{
  width: 23vw;
  height: 37vh;
  padding-top: 3vh;
  padding-left: 3vw;
  font-size: 3vh;
  background-color: white;
}

button{
  color: blue;
  background-color: firebrick;
  margin-top: 3vh;
  width: 7vw;
  height: 4vh
}
.re-password{
  background: white;
  position: fixed;
  right: 16vw;
  top: 62vh;
  height: 1vh;
  border: none;
}
</style>