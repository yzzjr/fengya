<template>

    <div class="app-container">
  
      <h2 style="text-align: center;">上架新图书</h2>
  
      <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
        <el-step title="填写图书基本信息"/>
        <el-step title="创建图书大纲"/>
        <el-step title="确定上架"/>
      </el-steps>
      
      <el-button type="text" @click ="openAddChapter()">添加章节</el-button>
      <!-- 章节 -->
      <ul class="chanpterList">
          <li
              v-for="chapter in chapterVideoList"
              :key="chapter.id">
              <p>
                  {{ chapter.title }}   
                  <span class="acts">
                    <el-button type ="text" @click="openVideoDialog(chapter.id)">添加小节</el-button>
                    <el-button style ="" type="text" @click="getChapterById(chapter.id)">编辑</el-button>
                    <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
                </span>
              </p>
              <ul class="chanpterList videoList">
                <li
                    v-for="video in chapter.children"
                    :key="video.id">
                    <p>{{ video.title }}
                        <span class="acts">
                            <el-button type="text" @click= "openVideoEditDialog(video.id)">编辑</el-button>
                            <el-button type="text"@click= "removeVideo(video.id)">删除</el-button>
                        </span>
                    </p>
                </li>
            </ul>
              <!-- 视频 -->
              
          </li>
      </ul>
      <div align="center">
          <el-button @click="previous">上一步</el-button>
          <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
      </div>

      <!-- 添加和修改章节表单 -->
      <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
        <el-form :model="chapter" label-width="120px">
            <el-form-item label="章节标题">
                <el-input v-model="chapter.title"/>
            </el-form-item>
            <el-form-item label="章节排序">
                <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
        </div>
      </el-dialog>
<!-- 添加和修改小节表单 -->
      <el-dialog :visible.sync="dialogVideoFormVisible" title="添加小节">
        <el-form :model="video" label-width="120px">
            <el-form-item label="小节标题">
                <el-input v-model="video.title"/>
            </el-form-item>
            <el-form-item label="小节排序">
                <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
            </el-form-item>

             <!-- 视频 -->
             <el-form-item label="上传视频">
              <el-upload
                     :on-success="handleVodUploadSuccess"
                     :on-remove="handleVodRemove"
                     :before-remove="beforeVodRemove"
                     :on-exceed="handleUploadExceed"
                     :file-list="fileList"
                     :action="BASE_API+'/vidservice/vod/upload'"
                     :limit="1"
                     class="upload-demo">
              <el-button size="small" type="primary">上传视频</el-button>
              <el-tooltip placement="right-end">
                  <div slot="content">最大支持1G，<br>
                      支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                      GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                      MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                      SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
                  <i class="el-icon-question"/>
              </el-tooltip>
              </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveOrUpdateVideo">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    
  </template>
  <script>
    import chapter from '@/api/chapter'
    import video from '@/api/video'
  export default {
    data() {
      return {
        saveBtnDisabled: false, // 保存按钮是否禁用
        id:'',
        chapterVideoList: [],
        fileList:[],
        chapter: {
          title: '',
          sort: 0,
          courseId : ''
        },
        video: {
          title: '',
          sort: 0,
          chapterId : '',
          courseId:'',
          videoSourceId:'',
          videoOriginalName:'',
        },
        dialogChapterFormVisible :false,
        dialogVideoFormVisible :false,
        BASE_API:process.env.BASE_API,
      }
    },

    
  
    created() {
      console.log('chapter created')
      this.init()
      this.getChapterVedioId(this.id)
    },
  
    methods: {

      getChapterById(chapterId){
        this.dialogChapterFormVisible = true
        chapter.getChapterInfo(chapterId)
        .then(response=>{
          this.chapter = response.data.eduChapter
        })
      },
      removeChapter(id){
      this.$confirm('此操作将永久删除该章节, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',}) 
      .then(response=>{
          return  chapter.deleteChapter(id)
        })
        .then(()=>{
          this.getChapterVedioId(this.id)
          
          this.$message({
            type: 'success',
            message: '删除章节成功!'
            })
          
        })
    },


      init(){
        if(this.$route.params && this.$route.params.id){
          this.id = this.$route.params.id
          //根据id查询
          
        }
      },
      saveOrUpdate(){
        if(!this.chapter.id){
          
          this.saveChapter()
        }
        else{
          this.updateChapter()
        }
        
      },
      openVideoDialog(chapterId){
        this.dialogVideoFormVisible = true
        this.video.title = ''
        this.video.sort = 0
        this.video.chapterId = chapterId
        this.fileList=[]
      },
      openVideoEditDialog(videoId){
        this.dialogVideoFormVisible = true
        video.getVideoIndo(videoId)
        .then(response=>{
          this.video = response.data.eduVideo
          this.fileList = [{'name':this.video.videoOriginalName}]
        })
      },

      

      //添加章节
      saveChapter(){
        this.chapter.courseId = this.id
        chapter.addChapter(this.chapter)
        .then(response=>{
            this.dialogChapterFormVisible = false
            this.$message({
              type: 'success',
              message: '添加章节成功!'
            })
                this.getChapterVedioId(this.id)
        })
        .catch(response=>{
          this.$message({
            type: 'error',
            message: '添加章节失败'
          })
        })
      },
      updateChapter(){
        chapter.updateChapter(this.chapter)
        .then(response=>{
          this.dialogChapterFormVisible = false
            this.$message({
                    type: 'success',
                    message: '修改章节成功!'
                })
                this.getChapterVedioId(this.id)
        })
        .catch(response=>{
          this.$message({
                        type: 'error',
                        message: '修改章节失败'
                    })
        })
      },

      saveOrUpdateVideo(){
        if(!this.video.id){
           this.addVideo()
        }else{
          this.updateVideo()
        }
        
       
      },
      updateVideo(){
        video.updateVideo(this.video)
        .then(response=>{
          this.dialogVideoFormVisible = false
            this.$message({
                    type: 'success',
                    message: '修改小节成功!'
                })
                this.getChapterVedioId(this.id)
        })
        .catch(response=>{
          this.$message({
              type: 'error',
              message: '修改小节失败'
          })
        })
      },
      //添加小节
      addVideo(){
        this.video.courseId = this.id
        console.log(this.video.courseId)
        video.addVideo(this.video)
        .then(response=>{
          this.dialogVideoFormVisible = false
          this.$message({
                    type: 'success',
                    message: '添加小节成功!'
                })
                this.getChapterVedioId(this.id)
        })
        .catch(response=>{
          this.$message({
            type: 'error',
            message: '添加小节失败'
          })
        })
      },
      removeVideo(videoId){
        this.$confirm('此操作将永久删除该小节, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',}) 
      .then(response=>{
          return  video.deleteVideo(videoId)
        })
        .then(()=>{
          this.getChapterVedioId(this.id)
          this.$message({
            type: 'success',
            message: '删除章节成功!'
            })
          
        })
      },

      getChapterVedioId(id){
        chapter.getAllChapterVideoList(id)
        .then(response=>{
          this.chapterVideoList = response.data.items
        })
        .catch(response=>{
          this.$message({
            type: 'error',
            message: '数据查找失败'
                    })
        })
      },
      openAddChapter(){
        this.dialogChapterFormVisible = true
        this.chapter.title = ''
        this.chapter.sort = 0
        this.chapter.courseId = ''
      },

      previous() {
        console.log('previous')
        this.$router.push({ path: '/course/info/'+this.id })
      },
  
      next() {
        console.log('next')
        this.$router.push({ path: '/course/publish/'+ this.id })
      },
      //上传成功后
      handleVodUploadSuccess(response,file,fileList){
        this.video.videoSourceId = response.data.videoId
        this.video.videoOriginalName = file.name
        console.log(response.data.videoId)
        
      },
      //上传之前的限制
      handleUploadExceed(files,fileList){
        this.message.warning('想要重新上传视频，请先删除已上传的视频')
        
      },

      beforeVodRemove(file,fileList){
        return this.$confirm(`确定移除 ${file.name}？`)
      },

      handleVodRemove(file,fileList){
        video.removeAliyunVideo(this.video.videoSourceId)
        .then(response=>{
          this.$message({
          type: 'success',
          message: '删除视频成功!'
          })
          .catch(response=>{
            this.$message({
            type: 'error',
            message: '删除视频失败'
            })
          })
        })
      }
      //删除云端视频

    }
  }
  </script>
  <style scoped>
    .chanpterList{
        position: relative;
        list-style: none;
        margin: 0;
        padding: 0;
    }
    .chanpterList li{
      position: relative;
    }
    .chanpterList p{
      float: left;
      font-size: 20px;
      margin: 10px 0;
      padding: 10px;
      height: 70px;
      line-height: 50px;
      width: 100%;
      border: 1px solid #DDD;
    }
    .chanpterList .acts {
        float: right;
        font-size: 14px;
    }
    
    .videoList{
      padding-left: 50px;
    }
    .videoList p{
      float: left;
      font-size: 14px;
      margin: 10px 0;
      padding: 10px;
      height: 50px;
      line-height: 30px;
      width: 100%;
      border: 1px dotted #DDD;
    }
    
    </style>