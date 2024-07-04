<script setup>

</script>

<template>
  <div class="top">
    <div class="img">
    <img src="../imgs/4.png" style="width: 9vw;height: 9vh">
    </div>

    <router-link style="margin-left: 3vw" to="/">课程</router-link>
    <router-link style="margin-left: 2vh" to="/test">测试题</router-link>
    <router-link style="margin-left: 45vw" to="/user/one">个人中心</router-link>

  </div>
</template>

<style scoped>
.img{
  width: 9vw;
  height: 10vh;
  margin-top: 2vh;
  margin-left: 13vw;
}
.top{
  top: 0;
  left: 0;
  width: 100vw;
  height: 10vh;
  background-color: white;
  border: 0.1vh solid whitesmoke;
  display: flex;
  font-size: 3vh;
  align-items: center;

}
a, .green{
  color: black;
}
</style>