<template>
    <div class="app-container">
      <el-form label-width="120px">
        <el-form-item label="监控视频上传">
          
  
        </el-form-item>
  
        
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
          title: '22222',
          sort: 0,
          courseId : '2222'
        },
        video: {
          title: '22222',
          sort: 0,
          chapterId : '222222',
          courseId:'222222',
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
      
      
    },
  
    methods: {

     
      
     
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