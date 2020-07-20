<template>

    <div class="app-container">
  
      <h2 style="text-align: center;">发布新课程</h2>
  
      <el-steps :active="3" process-status="wait" align-center style="margin-bottom: 40px;">
        <el-step title="填写课程基本信息"/>
        <el-step title="创建课程大纲"/>
        <el-step title="确认发布"/>
      </el-steps>
      
      <div class="ccInfo">
        <img :src="coursePublish.cover">
        <div class="main">
          <h2>{{ coursePublish.title }}</h2>
          <p class="gray"><span>共{{ coursePublish.lessonNum }}课时</span></p>
          <p><span>所属分类：{{ coursePublish.levelOne }} — {{ coursePublish.levelTwo }}</span></p>
          <p>课程讲师：{{ coursePublish.teacherName }}</p>
          <h3 class="red">￥{{ coursePublish.price }}</h3>
        </div>
      </div>


      <el-form label-width="120px">
        
        <el-form-item align="center">
          <el-button @click="previous">返回修改</el-button>
          <el-button :disabled="saveBtnDisabled" type="primary" @click="publish">确认发布</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>

  <script>
    import course from '@/api/course'
    export default {
      data() {
        return {
          saveBtnDisabled: false, // 保存按钮是否禁用
          coursePublish: {},
          courseId: '',
        }
      },
    
      created() {
        this.init()
        
      },
    
      methods: {

        init(){
          if(this.$route.params && this.$route.params.id){
            this.id = this.$route.params.id
            this.getCourseInfoAll()
          }
        },
        previous() {
          console.log('previous')
          this.$router.push({ path: '/course/chapter/'+this.id })
        },

        getCourseInfoAll(){
          course.getAllCourseInfo(this.id)
          .then(response=>{
            this.coursePublish = response.data.courseInfo
          })
        },
    
        publish() {
          console.log('publish')
          course.updateCourseStatus(this.id)
          .then(response=>{
            this.$message({
              type: 'success',
              message: '成功发布!'
            })
            this.$router.push({ path: '/course/list' })
          })
          .catch(response=>{
            this.$message({
            type: 'error',
            message: '发布失败'
          })
          })
          
        }
      }
    }
    </script>
    <style scoped>
      .ccInfo {
          background: #f5f5f5;
          padding: 20px;
          overflow: hidden;
          border: 1px dashed #DDD;
          margin-bottom: 40px;
          position: relative;
      }
      .ccInfo img {
          background: #d6d6d6;
          width: 500px;
          height: 278px;
          display: block;
          float: left;
          border: none;
      }
      .ccInfo .main {
          margin-left: 520px;
      }
      
      .ccInfo .main h2 {
          font-size: 28px;
          margin-bottom: 30px;
          line-height: 1;
          font-weight: normal;
      }
      .ccInfo .main p {
          margin-bottom: 10px;
          word-wrap: break-word;
          line-height: 24px;
          max-height: 48px;
          overflow: hidden;
      }
      
      .ccInfo .main p {
          margin-bottom: 10px;
          word-wrap: break-word;
          line-height: 24px;
          max-height: 48px;
          overflow: hidden;
      }
      .ccInfo .main h3 {
          left: 540px;
          bottom: 20px;
          line-height: 1;
          font-size: 28px;
          color: #d32f24;
          font-weight: normal;
          position: absolute;
      }
      </style>