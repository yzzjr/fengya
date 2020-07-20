<template>
    <div class="app-container">
      <el-form label-width="120px">
        <el-form-item label="讲师名称">
          <el-input v-model="teacher.name"/>
        </el-form-item>
        <el-form-item label="讲师排序">
          <el-input-number v-model="teacher.sort" controls-position="right" min="0"/>
        </el-form-item>
        <el-form-item label="讲师头衔">
          <el-select v-model="teacher.level" clearable placeholder="请选择">
            <!--
              数据类型一定要和取出的json中的一致，否则没法回填
              因此，这里value使用动态绑定的值，保证其数据类型是number
            -->
            <el-option :value=1 label="高级讲师"/>
            <el-option :value=2 label="首席讲师"/>
          </el-select>
        </el-form-item>
        <el-form-item label="讲师资历">
          <el-input v-model="teacher.career"/>
        </el-form-item>
        <el-form-item label="讲师简介">
          <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
        </el-form-item>
  
        <!-- 讲师头像：TODO --> 
        <el-form-item label="讲师头像">

            <!-- 头衔缩略图 -->
            <pan-thumb :image="teacher.avatar"/>
            <!-- 文件上传按钮 -->
            <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
            </el-button>
        
            <!--
        v-show：是否显示上传组件
        :key：类似于id，如果一个页面多个图片上传控件，可以做区分
        :url：后台上传的url地址
        @close：关闭上传组件
        @crop-upload-success：上传成功后的回调 -->
            <image-cropper
                v-show="imagecropperShow"
                :width="300"
                :height="300"
                :key="imagecropperKey"
                :url="BASE_API+'/eduservice/oss/upload'"
                field="file"
                @close="close"
                @crop-upload-success="cropSuccess"/>
        
        </el-form-item>
        <el-form-item>
          <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>

  <script>
        import teacher from '@/api/teacher'
        import ImageCropper from '@/components/ImageCropper'
        import PanThumb from '@/components/PanThumb'
        const defaultForm = {
                name: '',
                sort: 0,
                level: '',
                career: '',
                intro: '',
                avatar: 'https://fengya-teacher.oss-cn-beijing.aliyuncs.com/%E6%8D%95%E8%8E%B7.PNG'
            }
      export default{
        //申明额外组建
        components:{ImageCropper,PanThumb},
        
        data(){
            
            return {
                BASE_API:process.env.BASE_API,
                teacher: defaultForm,
                saveBtnDisabled: false, // 保存按钮是否禁用,
                imagecropperShow: false,
                imagecropperKey:0
            }


        },
        watch:{
                $route(to, from){
                    console.log('watch $route')
                    this.init()
                }
            },
        created(){
            //在页面加载之前判断路由中是否有id值
            this.init()
        },

        methods: {
            cropSuccess(data) {
                console.log(data)
                this.imagecropperShow = false
                this.teacher.avatar = data.imgurl
                this.imagecropperKey = this.imagecropperKey+1
            },

    // 关闭上传组件
            close() {
                this.imagecropperShow=false
                this.imagecropperKey = this.imagecropperKey+1
            },
            
            init(){
                if(this.$route.params && this.$route.params.id){
                const id = this.$route.params.id
                this.getTeacherById(id)
            }else{
                //表单数据清空
                this.teacher = {...defaultForm}
            }
            },
            saveOrUpdate(){
                if(!this.teacher.id){
                    this.saveBtnDisabled = true
                    this.saveTeacher()
                }
                else{

                    this.updateTeacher()
                }
                
                
                
            },

            saveTeacher(){
                //调用后台接口
                teacher.saveTeacher(this.teacher)
                .then(()=>{
                    //请求之后添加
                    this.$message({
                        type: 'success',
                        message: '添加成功!'
                    })
                }).then(()=>{
                    //通过路由调转回到列表
                    this.$router.push({path: '/teacher/list'})
                })
                .catch(()=>{
                    this.$message({
                        type: 'error',
                        message: '添加失败'
                    })
                })
            },

            getTeacherById(id){
                teacher.getTeacherId(id)
                .then(response=>{
                    this.teacher = response.data.eduTeacher
                })
            },
            updateTeacher(){
                teacher.updateTeacherId(this.teacher)
                .then(response=>{
                    this.$message({
                        type: 'success',
                        message: '修改成功!'
                    })
                }).then(()=>{
                    this.$router.push({path: '/teacher/list'})
                }).catch(response=>{
                    this.$message({
                        type: 'error',
                        message: '修改失败'
                    })
                })
            }

        }



      }


  </script>