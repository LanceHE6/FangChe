<template>
  <div class="body">
    <el-container class="container">
      <Header></Header>
      <el-main class="main">
        <div class="ad">
          <img src="../assets/main/u1072.png" alt="ad" class="img-ad">
          <div class="text-ad">
            <el-text class="text-ad1">在线课程</el-text>
            <el-text class="text-ad2">多元化培训课程，OMO培训服务、直播教学服务</el-text>
          </div>

        </div>

          <div class="course">
            <div class="course-list">
              <el-row :gutter="20">
                <el-col :span="6" v-for="item in 20" :key="item" ><div class="grid-content ep-bg-purple" />
                  <el-card style="max-width: 480px">
                    <p v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</p>
                    <template #footer>Footer content</template>
                  </el-card>
                </el-col>
              </el-row>
            </div>

            <el-pagination background layout="prev, pager, next" :total="1000" />
          </div>

      </el-main>

      <el-footer>
        Footer
      </el-footer>
    </el-container>
  </div>
</template>

<script setup>

import Header from  "../components/HeaderMenu.vue";
</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
}
.body {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  background-color: white;
}

.main {
  overflow: hidden;
}
.ad {
  width: 100%;
  height: 40%;
  background-color: #409EFF;;
  background-image: url("../assets/main/u314.jpg");
}
.img-ad {
  width: 20%;
  height: 90%;
  margin: 5% 72%;
  min-width: 245px;
  min-height: 345px;
}
.text-ad {
  color: white;
  font-size: 20px;
  font-weight: bold;
  margin: -20% 0 0 10%;
}
.text-ad1 {
  color: black;
  font-size: 30px;
  font-weight: bold;
  display: block;
}
.text-ad2 {
  color: black;
  font-size: 18px;
  display: block;
}
.course {
  width: 80%;
  margin-top: 100px;
}
.course-list {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

el-row {
  margin-bottom: 20px;
}
el-row:last-child {
  margin-bottom: 0;
}
el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>
