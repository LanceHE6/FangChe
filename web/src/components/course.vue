<script setup>

import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";

const dialogFormVisible = ref(false)
const formLabelWidth = '140px'

const tableData=ref([])
const search=ref()
const form = reactive({
  name: '',
  introduction: '',
  chapters: [{
    name:'',
    content:''
  }],
  resources: '',
  category: '',
})

// 删除
const handleDelete = async (index) => {
  console.log(tableData)
  let token = localStorage.getItem('token')
  let res=await axios.delete('/api/course/del/'+tableData._rawValue[index].id,{
    headers:{
      Authorization:"Bearer "+token
    }
  })
  console.log(res)
  if(res.data.code===200){
    console.log('删除成功')
    acquire()
  }
}
// 获取
const handleChange=async (index)=>{

  let token=localStorage.getItem('token')
  let res=await axios.get('/api/video/list',{
    headers:{
      Authorization:"Bearer "+token
    }
  })
  console.log(res)
  if(res.data.code===200){
    console.log('成功')
    localStorage.setItem('video',res.data.data)
    await router.push('courseC')
  }
}
// 搜索特定内容
const check=async ()=>{
  console.log(search.value)
  let res=await axios.get('/api/course/search',{

    params:{keyword:search.value}
  }
)
  if(res.data.code===200){
    let newsCourse=[]
    for(let course of res.data.data.rows){
      let metaCourse={}
      metaCourse=course.course
      metaCourse['collected']=course.collected
      newsCourse.push(metaCourse)
    }
    tableData.value=newsCourse
  }
}
// 添加
const add=async ()=>{
  let token = localStorage.getItem('token')
  let id=localStorage.getItem('id')
  dialogFormVisible.value=!dialogFormVisible
  let res=await axios.post('/api/course/add',
      {
        name:form.name,
        introduction:form.introduction,
        chapters:form.chapters,
        resources:form.resources,
        teacher_id:id,
        category:form.category,
      }
  ,{
    headers:{
      Authorization:"Bearer "+token
    }
  })
  if(res.data.code===200){
    ElMessage({
      message: '添加成功',
      type: 'success',
    })

  }else {
    ElMessage({
      message: '添加失败',
      type: 'error',
    })
    window.location.reload()
  }
}


// 获取所有内容
const acquire=async ()=>{

  let res=await axios.get('/api/course/search')
  console.log('999999999999999',res.data.data)
  if(res.data.code===200){
    let newsCourse=[]

    for(let course of res.data.data.rows){
      let metaCourse={}
      metaCourse=course.course
      metaCourse['collected']=course.collected
      newsCourse.push(metaCourse)
    }

    tableData.value=newsCourse
  }
}

onMounted(()=>{
  acquire()
})
</script>

<template>
  <el-button text @click="dialogFormVisible = true">添加课程</el-button>

  <el-dialog v-model="dialogFormVisible" title="添加课程">
    <el-form :model="form">
      <el-form-item label="课程名" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="课程介绍" :label-width="formLabelWidth">
        <el-input v-model="form.introduction" autocomplete="off" />
      </el-form-item>

      <el-form-item label="上传视频" :label-width="formLabelWidth">
        <el-input v-model="form.resources" autocomplete="off" />
      </el-form-item>

      <el-form-item label="课程类别" :label-width="formLabelWidth">
        <el-input v-model="form.category" autocomplete="off" />
      </el-form-item>

      <el-form-item label="章节信息" :label-width="formLabelWidth">
        <el-input v-model="form.chapters.name" autocomplete="off" />
        <el-input v-model="form.chapters.content" autocomplete="off" />
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="add">确认</el-button>
      </span>
    </template>
  </el-dialog>


  <el-table :data="tableData" style="width: 100%">

    <el-table-column prop="name" label="课程名" width="250">

    </el-table-column>
    <el-table-column prop="" label="教师" width="250">

    </el-table-column>
    <el-table-column align="right">
      <template #header>
        <el-input v-model="search" size="small" placeholder="请输入搜索内容" />
        <button class="searchB" @click="check">搜索</button>
      </template>
      <template #default="scope">
        <el-button
            size="small"
            type="danger"
            @click="handleChange(scope.$index)">编辑
        </el-button>

        <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.$index)">删除
        </el-button>

      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>
.searchB{
  position: absolute;
  width: 4vw;
  right: 1vw;
  font-size: 2vh;
  border: none;
}
.searchB:hover{
  cursor: pointer;
}
.searchB:active{
  color: #0056b3;
}
</style>