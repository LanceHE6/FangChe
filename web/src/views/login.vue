<script>
import {ref} from "vue";
import axios from "axios";
import router from "@/router/routes.js";
const username=ref('')
const password=ref('')

// console.log(input1,input2)

export default {
  name: 'Login',
  data() {
    return {
      account: username.value,
      password: password.value,
    };

  },
  methods: {
    async login() {
      try {
        const response = await axios.post('api/user/login', {
          account: this.account,
          password: this.password,
        }, {
        });
        console.log(response)
        console.log('登陆成功',response.data);
        alert("登录成功")
        // await router.push('/')
      } catch (error) {
        console.log('登录失败',error);
        alert('登录失败')
      }
    },
  },
};

</script>

<template>
  <div class="back">

  <div class="left">
    <img src="@/imgs/1.webp">
  </div>
  <div class="right">
  <el-divider class="right-center">
    <el-form class="Rcenter">
      <h2 style="margin-left: 1vw;color: mediumslateblue">欢迎登录方舟</h2>

      <br>
      <el-input
          v-model="account"
          style="width: 16vw;height: 5vh"
          placeholder="请输入账号"
          clearable
      />
      <br>

      <el-input
          v-model="password"
          style="width: 16vw;height: 5vh;margin-top: 2vh"
          type="password"
          placeholder="请输入密码"
          show-password
      />
      <br>
      <el-button type="info" @click="login">登录</el-button>
      <el-button style="margin-left: 2vw">注册</el-button>
    </el-form>

  </el-divider>
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
  input{

  }
  button{
    color: blue;
    background-color: firebrick;
    margin-top: 3vh;
    width: 7vw;
    height: 4vh
  }
</style>