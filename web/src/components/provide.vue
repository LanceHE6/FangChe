<script setup>
import { ref } from 'vue';
import axios from "axios";

const files = ref(null);

// 用于显示上传状态的变量
const uploadStatus = ref('');

// 用于显示错误信息的变量
const error = ref('');

// 选择文件的函数
const selectFile = () => {
  files.value = document.querySelector('input[type="file"]').files[0];
};

// 上传文件的函数
const uploadFile = async () => {
  if (!files.value) {
    error.value = '请选择一个文件';
    return;
  }
  // 创建 FormData 对象
  const file = new FormData();
  file.append('file', files.value);
  console.log('正在上传')

  const res=await axios.post('/api/question/add',file,{
    headers:{
      Authorization:'Bearer "+token,'
    }
  })
  console.log('res',res)
  if(res.data.code===200){
    console.log('上传成功')
  }else {}
  console.log("失败")

};


</script>

<template>
<div class="provide">
  <h1>发布</h1>
  <input type="file" @change="selectFile" />
  <button @click="uploadFile">Upload File</button>
  <p v-if="error">{{ error }}</p>
  <p v-if="uploadStatus">{{ uploadStatus }}</p>
</div>
</template>

<style scoped>

</style>