<script setup>

import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {ElMessage, ElMessageBox} from "element-plus";
import router from "@/router/index.js";
import {CirclePlus} from "@element-plus/icons-vue";

const dialogFormVisible = ref(false)
const formLabelWidth = '140px'
let token=localStorage.getItem('token')
const dialogVisible = ref(false)
const tableData=ref([])
const impath='/api/course/set-image'
const inVpath='/api/video/add'
const select=reactive({
  page:1,
  pageSize:10,
})
let videoL=[]

let total=ref()
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
  console.log('index',index)
  console.log(tableData._rawValue[index].resource)
  let videoList=JSON.parse(tableData._rawValue[index].resource)
  console.log(videoList)
  for (let i=0;i<videoList.length;i++){
      let id=videoList[i]
    console.log('id',id)
      await axios.get('/api/video/search',{
        headers:{
          Authorization:"Bearer "+token
        },params:{
          id:id
        }
      }).then(res=>{
        console.log('成功',res.data.data)
        videoL.push(res.data.data)
        console.log('res.data.data',res.data.data)
        localStorage.setItem('video',JSON.stringify(videoL))
        router.push('courseC')
      }).catch(error=>{
        console.log('失败',error)
      })
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

  let res=await axios.get('/api/course/search',{params:select})
  if(res.data.code===200){
    let newsCourse=[]
    for(let course of res.data.data.rows){
      let metaCourse={}
      metaCourse=course.course
      metaCourse['collected']=course.collected
      newsCourse.push(metaCourse)
    }
    total=res.data.data.total
    tableData.value=newsCourse
  }
  console.log(tableData)
}

const handleChangePage=(page)=>{
  select.page = page;
  acquire()
}

// 添加图片和视频
let index1=ref()
let Cid=ref()
let Name=ref()
const selectIV=(index)=>{
  console.log(tableData)
  dialogVisible.value=!dialogVisible.value;
  index1.value=index
  console.log(tableData._rawValue[index1.value].id)
  Cid=tableData.value[index].id
  Name=tableData.value[index].name


  console.log('index_2',index)
  console.log('index1_2',index1)
}
const videoList=ref([])
const fileList = ref([]);
const geiId=async (response)=>{
  console.log('视频ID',response.data.id)
  console.log(typeof tableData._rawValue[index1.value]);
  tableData._rawValue[index1.value].resource = JSON.parse(tableData._rawValue[index1.value].resource);
  console.log(index1)
  tableData._rawValue[index1.value].resource.push(response.data.id)

  update()
}

const update=async ()=>{
  let res=await axios.put('/api/course/update',{id:tableData._rawValue[index1.value].id,
    resources:JSON.stringify( tableData._rawValue[index1.value].resource)
  },{
    headers:{
      Authorization:"Bearer "+token
    }
  })
  console.log('333333333333333333',res)
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
              @click="selectIV(scope.$index)"
              style="border:none;"
              size="small"
              type="primary">
            <el-icon><CirclePlus />
            </el-icon>
          </el-button>

          <el-dialog
              text
              v-model="dialogVisible"
              title="Tips"
              width="30%"
          >

            <span>
              <el-upload
                  v-model:file-list="fileList"
                  class="upload-demo"
                  :headers='{Authorization:"bearer "+token }'
                  :action=axios.defaults.baseURL+impath
                  :data="{id:Cid}"
                  list-type="picture"
              >
               <el-button type="primary">上传课程图片</el-button>
              </el-upload>

            </span>
            <span>
              <el-upload
                  v-model:file-list="videoList"
                  class="upload-demo"
                  :headers='{Authorization:"bearer "+token }'
                  :action=axios.defaults.baseURL+inVpath
                  :data="{name:Name}"
                  list-type="picture"
                  :on-success="geiId"
              >
               <el-button type="primary">上传课程视频</el-button>
              </el-upload>
            </span>
            <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogVisible = false">
          完成
        </el-button>
      </span>
            </template>
          </el-dialog>


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
  <div class="example-pagination-block">
    <el-pagination
        :page-size="10"
        layout="prev, pager, next"
        :total="total"
        @current-change="handleChangePage"
    />
  </div>
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

.el-pagination{
  display: flex;
  align-items: center;
  justify-content: center;
  --el-pagination-bg-color:#f5f5f5;
}
.el-pagination button .el-pagination button:disabled{
  background-color:#f5f5f5;
}
</style>