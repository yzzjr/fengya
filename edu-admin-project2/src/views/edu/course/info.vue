<template>

    <div class="app-container">
  
      <h2 style="text-align: center;">发布新课程</h2>
  
      <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
        <el-step title="填写课程基本信息"/>
        <el-step title="创建课程大纲"/>
        <el-step title="确认发布"/>
      </el-steps>
  
      <el-form label-width="120px">

        <el-form-item label="课程标题">
          <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
        </el-form-item>
      
        <!-- 所属分类 TODO -->
        <el-form-item label="课程一级类别">
          <el-select
            v-model="courseInfo.subjectParentId"
            placeholder="请选择" @change="subjectLevelOneChanged">
            <el-option
              v-for="subject in oneLevelSubjectList"
              :key="subject.id"
              :label="subject.title"
              :value="subject.id"/>
          </el-select>
          <el-select
          v-model="courseInfo.subjectId"
          placeholder="请选择">
          <el-option
            v-for="subject in twoLevelSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
        </el-form-item>
        <!-- 课程讲师 TODO -->
        <el-form-item label="课程讲师">
            <el-select
              v-model="courseInfo.teacherId"
              placeholder="请选择">
              <el-option
                v-for="teacher in teacherList"
                :key="teacher.id"
                :label="teacher.name"
                :value="teacher.id"/>
            </el-select>
          </el-form-item>

        <el-form-item label="总课时">
          <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>
       
        
      
        <!-- 课程简介 TODO -->
        <el-form-item label="课程简介">
          <tinymce :height="300" v-model="courseInfo.description"/>
        </el-form-item>
        <!-- 课程封面 TODO -->
        <!-- 课程封面-->
        <el-form-item label="课程封面">

          <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API+'/eduservice/oss/upload?host=cover'"
            class="avatar-uploader">
            <img :src="courseInfo.cover">
          </el-upload>
        
        </el-form-item>
      
        <el-form-item label="课程价格">
          <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>
      
        <el-form-item align="center">
          <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并下一步</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  
  
  <script>
    import course from '@/api/course'
    import subject from '@/api/subject'
    import Tinymce from '@/components/Tinymce'
    const defaultForm = {
      title: '',
      subjectId: '',
      teacherId: '',
      lessonNum: 0,
      description: '',
      cover: 'https://fengya-teacher.oss-cn-beijing.aliyuncs.com/%E6%8D%95%E8%8E%B7.PNG',
      price: 0,
      subjectParentId:''
    }
    
    export default {
      components:{Tinymce},
      data() {
        return {
          teacherList:[],
          oneLevelSubjectList:[],
          twoLevelSubjectList:[],
            courseInfo: defaultForm,
          saveBtnDisabled: false, // 保存按钮是否禁用
          BASE_API: process.env.BASE_API
        }
      },
    
      watch: {
        $route(to, from) {
          console.log('watch $route')
          this.init()
        }
      },
    
      created() {
        console.log('info created')
        
        this.init()
      },
    
      methods: {
        
        getTeacherList(){
            course.getAllTeacherList()
            .then(response =>{
                this.teacherList = response.data.items
            })
        },

        init() {
          if (this.$route.params && this.$route.params.id) {
            const id = this.$route.params.id
            console.log(id)
            this.getIdCourse(id)

          } else {
            this.courseInfo = { ...defaultForm }
            this.getTeacherList()
            this.getLevelAll()
          }
        },
    
        next() {
          console.log('next')
          this.saveBtnDisabled = true
          if (!this.courseInfo.id) {
            this.saveData()
            console.log(this.courseInfo.subjectParentId)
          } else {
            this.updateData()
          }
        },
    
        // 保存
        saveData() {
          course.saveCourseInfo(this.courseInfo).then(response => {
            this.$message({
              type: 'success',
              message: '保存成功!'
            })
            return response// 将响应结果传递给then
          }).then(response => {
            this.$router.push({ path: '/course/chapter/' + response.data.courseId })
          }).catch((response) => {
            this.$message({
              type: 'error',
              message: response.message
            })
          })
        },
        
        getLevelAll(){
          subject.getAllSubjectList()
          .then(response =>{
            this.oneLevelSubjectList = response.data.OneSubjectList
          })
        },

        subjectLevelOneChanged(value){
          for(var i =0;i<this.oneLevelSubjectList.length;i++){
            var levelOne = this.oneLevelSubjectList[i]
            if(levelOne.id === value){
              this.twoLevelSubjectList = levelOne.children
              this.courseInfo.subjectId = ''
              
            }
          }
        },
       
      
      updateData() {
        course.updateCourseInfoById(this.courseInfo.id,this.courseInfo)
            .then(response =>{
              this.$message({
              type: 'success',
              message: '修改成功!'})
              this.$router.push({path: '/course/chapter/' + this.courseInfo.id})
            })
            .catch(response=>{
              this.$message({
                type: 'error',
                message: '修改课程信息失败'
                    })
            }) 
      },
      beforeAvatarUpload(file){
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
      },
      handleAvatarSuccess(res,file){
        this.courseInfo.cover = res.data.imgurl
        //console.log(this.courseInfo.cover )
      },

      getIdCourse(id){
        course.getCourseInfoById(id)
          .then(response=>{
            this.courseInfo = response.data.courseInfoForm
            subject.getAllSubjectList()
              .then(response=>{
                this.oneLevelSubjectList =  response.data.OneSubjectList

                for(var i =0;i<this.oneLevelSubjectList.length;i++){
                  var levelOne = this.oneLevelSubjectList[i]
                  if(levelOne.id === this.courseInfo.subjectParentId){
                    this.twoLevelSubjectList = levelOne.children
                    console.log(levelOne.children)
                  }
                }
                
              })
              this.getTeacherList()

          })
      },

    }
  }
    </script>
    <style scoped>
      .tinymce-container {
        line-height: 29px;
      }
      </style>