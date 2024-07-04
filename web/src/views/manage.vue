<script setup>
import HeaderMenu from "@/components/HeaderMenu.vue";
</script>

<template>
  <HeaderMenu />

  <div class="all">
    <h1>管理界面</h1>
    <div>
      <router-link to="/manage/provide">发布</router-link>
      |
      <router-link to="/manage/delete">删除操作</router-link>
    </div>
    <div class="connect">
      <router-view></router-view>
    </div>
  </div>


</template>

<style scoped>
h1{
  box-shadow: 0.11vw 0.11vw  ;
  margin-bottom: 1vh;
}
.all{
  width: 100vw;
  padding-top: 10vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  .connect{
    margin-top: 1vh;
    padding-top:2vh;
    width: 70%;
    border-radius: 1vh;
    border: 0.1vh solid gray;
    box-shadow: 0.15vw 0.15vw 0.15vw 0.15vw;

  }
}
</style>