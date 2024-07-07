<script setup>
import {onBeforeMount, ref} from "vue";
import {Avatar} from "@element-plus/icons-vue";
import router from "@/router/index.js";

const manager=ref(false)

onBeforeMount(()=>{
  const role='2'
  if(role==='2'||role==='3'){
    manager.value=true
  }
})

const toManage=async ()=>{
  await router.push('/manage')
 window.location.reload()
}
const toTest=async ()=>{
  await router.push('/test')
}
const toUser=async ()=>{
  await router.push('/user/one')
}


</script>

<template>

      <el-header class="menu-header">
        <el-menu
            :default-active="activeIndex"
            mode="horizontal"
            @select="handleSelect"
            class="menu"
            :ellipsis="false"
        >

          <img
              class="main-logo"
              src="../assets/logo3.png"
              alt="Logo"
          />


          <el-menu-item index="1" style="font-size: 28px; margin-left: 40px">在线课程</el-menu-item>
          <el-menu-item @click="toTest" index="2" style="font-size: 28px; margin-left: 40px">测评</el-menu-item>

          <div class="menu-right"/>
          <el-sub-menu index="3">
            <template #title>
              <el-icon>
                <el-icon :size="60"><Avatar /></el-icon>
              </el-icon>
              <div style="font-size: 20px">
                {{'用户'}}
              </div>

            </template>
            <el-menu-item index="/" @click="toUser">用户中心</el-menu-item>
            <el-menu-item @click="">帮助中心</el-menu-item>
            <el-menu-item @click="">关于</el-menu-item>
            <el-menu-item v-if="manager" @click="toManage">管理</el-menu-item>
            <el-menu-item @click="">退出登录</el-menu-item>
          </el-sub-menu>

        </el-menu>

      </el-header>

</template>

<style scoped>
* {
  padding: 0;
  margin: 0;
}

.menu-header{
  position: fixed;
  width: 100%;
  background-color: white;
  z-index: 99;
}
.menu {
  height: 72px;
}
.main-logo {
  width: 150px;
  height: 100px;
  margin-top: -5px;
  margin-left: 5%;
}

.menu-right {
  flex-grow: 1;
}
</style>
