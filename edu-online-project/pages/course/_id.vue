<template>
    <div id="aCoursesList" class="bg-fa of">

      <el-dialog
              title="请扫描二维码进行购买"
              :visible.sync="dialogVisible"
              width="30%"
              :before-close="handleClose">
              <span>
                <img :src="address"  class="dis c-v-pic" align="center">
              </span>
              <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
              </span>
            </el-dialog>
      <!-- /课程详情 开始 -->
      <section class="container">
        <section class="path-wrap txtOf hLh30">
          <a href="#" title class="c-999 fsize14">首页</a>
          \
          <a href="#" title class="c-999 fsize14">课程列表</a>
          \
          <span class="c-333 fsize14">{{courseInfo.subjectLevelOne}}</span>
          \
          <span class="c-333 fsize14">{{courseInfo.subjectLevelTwo}}</span>
        </section>
        <div>
          <article class="c-v-pic-wrap" style="height: 357px;">
            <section class="p-h-video-box" id="videoPlay">
              <img :src="courseInfo.cover" :alt="courseInfo.title" class="dis c-v-pic">
            </section>
          </article>
          <aside class="c-attr-wrap">
            <section class="ml20 mr15">
              <h2 class="hLh30 txtOf mt15">
                <span class="c-fff fsize24">{{courseInfo.title}}</span>
              </h2>
              <section class="c-attr-jg">
                <span class="c-fff">价格：</span>
                <b class="c-yellow" style="font-size:24px;">￥{{courseInfo.price}}</b>
              </section>
              <section class="c-attr-mt c-attr-undis">
                <span class="c-fff fsize14">主讲： {{courseInfo.teacherName}}&nbsp;&nbsp;&nbsp;</span>
              </section>
              <section class="c-attr-mt of">
                <span class="ml10 vam">
                  <em class="icon18 scIcon"></em>
                  <a class="c-fff vam" title="收藏" href="#" >收藏</a>
                </span>
              </section>
              <section class="c-attr-mt">
                <a href="#playVideos" title="立即观看" class="comm-btn c-btn-3" >立即观看</a>
              </section>
              <section class="c-attr-mt">
                <a @click="createOrder"   title="购买课程" class="comm-btn c-btn-3" style="cursor:pointer;">购买课程</a>
              </section>
            </section>
          </aside>
          <aside class="thr-attr-box">
            <ol class="thr-attr-ol clearfix">
              <li>
                <p>&nbsp;</p>
                <aside>
                  <span class="c-fff f-fM">购买数</span>
                  <br>
                  <h6 class="c-fff f-fM mt10">{{courseInfo.buyCount}}</h6>
                </aside>
              </li>
              <li>
                <p>&nbsp;</p>
                <aside>
                  <span class="c-fff f-fM">课时数</span>
                  <br>
                  <h6 class="c-fff f-fM mt10">{{courseInfo.lessonNum}}</h6>
                </aside>
              </li>
              <li>
                <p>&nbsp;</p>
                <aside>
                  <span class="c-fff f-fM">浏览数</span>
                  <br>
                  <h6 class="c-fff f-fM mt10">{{courseInfo.viewCount}}</h6>
                </aside>
              </li>
            </ol>
          </aside>
          <div class="clear"></div>
        </div>
        <!-- /课程封面介绍 -->
        <div class="mt20 c-infor-box">
          <article class="fl col-7">
            <section class="mr30">
              <div class="i-box">
                <div>
                  <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                    <a name="c-i" class="current" title="课程详情">课程详情</a>
                  </section>
                </div>
                <article class="ml10 mr10 pt20">
                  <div>
                    <h6 class="c-i-content c-infor-title">
                      <span>课程介绍</span>
                    </h6>
                    <div class="course-txt-body-wrap">
                      <section class="course-txt-body">
                        <p v-html="courseInfo.description">
                          {{courseInfo.description}}
                        </p>
                      </section>
                    </div>
                  </div>
                  <!-- /课程介绍 -->
                  <div class="mt50">
                    <h6 class="c-g-content c-infor-title">
                      <span>课程大纲</span>
                    </h6>
                    <section class="mt20">
                      <div class="lh-menu-wrap">
                        <menu id="lh-menu" class="lh-menu mt10 mr10">
                          <ul id="playVideos">
                            <!-- 文件目录 -->
                            <li class="lh-menu-stair" v-for="(chapter,index) in chapterVideoList" :key="index">
                              <a href="javascript: void(0)" :title="chapter.title" class="current-1">
                                <em class="lh-menu-i-1 icon18 mr10"></em>{{chapter.title}}
                              </a>
                              <ol class="lh-menu-ol" style="display: block;" >
                                <li class="lh-menu-second ml30"  v-for="(video,index) in chapter.children" :key="index">
                                  <a
                                    :title="video.title"
                                    target="_blank"
                                    @click="gotoPlay(video.videoSourceId)">
                                    <span class="fr" >
                                      <i class="free-icon vam mr10" style="cursor:pointer;">开始学习</i>
                                    </span>
                                    <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                  </a>
                                </li>
                               
                              </ol>
                            </li>
                          </ul>
                        </menu>
                      </div>
                    </section>
                  </div>
                  <!-- /课程大纲 -->
                </article>
              </div>
            </section>
          </article>
          <aside class="fl col-3">
            
            <div class="i-box">
              
              <div>
                <section class="c-infor-tabTitle c-tab-title">
                  <a title href="javascript:void(0)">主讲讲师</a>
                </section>
                <section class="stud-act-list">
                  <ul style="height: auto;">
                    <li>
                      <div class="u-face">
                        <a href="#">
                          <img :src="courseInfo.avatar" width="50" height="50" alt>
                        </a>
                      </div>
                      <section class="hLh30 txtOf">
                        <a class="c-333 fsize16 fl" href="#">{{courseInfo.teacherName}}</a>
                      </section>
                      <section class="hLh20 txtOf">
                        <span class="c-999">{{courseInfo.teacherName}}</span>
                      </section>
                    </li>
                  </ul>
                </section>
              </div>
            </div>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
      <!-- /课程详情 结束 -->
    </div>
  </template>
  
  <script>

  import course from "@/api/course"
  import vid from "@/api/vod"
  import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'



  export default {
    asyncData({params,error}){
      return course.getCourseInfoId(params.id)
        .then(response=>{
          
          return {courseInfo: response.data.data.courseInfo,
            chapterVideoList: response.data.data.chapterVideoList
            }
        })
    },

    data(){
      return{
        dialogVisible: false,
        
        token:'',
      orderId:'',
      courseId:'',
      address:'',
      payInfo:{
        amount:'',
        courseId:'',
        storeName:'',
        token:''
      },
      checkInfo:{
        videoSourceId:'',
        token:''
      },
      payCheckInfo:{
        orderId:''
      }
      }
      
    },

    created(){
      
      this.token = this.$route.query.token
      this.courseId = this.$route.params.id
      console.log(this.courseInfo.price)
      this.storeName = this.courseInfo.title
      this.payInfo.token = this.$route.query.token
      this.payInfo.courseId = this.courseId
      this.payInfo.storeName = this.courseInfo.title
      this.payInfo.amount = this.courseInfo.price
      this.checkInfo.token = this.token
      console.log(this.payInfo)
    },

    methods:{

      createOrder(){
        return course.makeOrder(this.payInfo)
          .then(response=>{
            this.address = response.data.data.address
            this.payCheckInfo.orderId = response.data.data.orderId
            if(response.data.code === 20001 && response.data.data.result ==3){
              this.$message({
                type: 'error',
                message: '请先登录再购买课程!'
              })
              this.$router.push({path: '/login'})
            }
            else{
              console.log(this.address)
              this.dialogVisible=true
              this.myInterval = window.setInterval(() => {
                let _this = this
                setTimeout(_this.checkstatus(), 6)
              }, 6000)
            }
            
          })
      },
      handleClose(done) {
        this.$confirm('确认退出交易？')
          .then(_ => {
            done();
            this.destroyed()
          })
          .catch(_ => {});
      },

      gotoPlay(vSourceId){
        this.checkInfo.videoSourceId = vSourceId
        vid.memberCheck(this.checkInfo)
        .then((response) =>{
            if (response.data.code === 20001 && response.data.data.result == 1){
              this.$message({
                type: 'error',
                message: '请先购买课程后再学习!'
              })
              this.createOrder() 
            } else {
              this.$router.push({path: '/player/' + this.checkInfo.videoSourceId+'?token='+this.token})
         
            }
          })
      },
      destroyed(){
        clearInterval(this.myInterval)
        this.myInterval = null
      },

      checkstatus(){
        course.checkOrder(this.payCheckInfo)
        .then(response=>{
          if(response.data.code == 20000){
            this.dialogVisible=true
            this.destroyed()
            this.$message({
              type: 'success',
              message: '支付成功'
            })
          }else{
            this.$message({
              type: 'error',
              message: '订单超时后即将关闭，请您尽快完成支付!'
            })
          }
        })
      }
    }



  };
  </script>
  