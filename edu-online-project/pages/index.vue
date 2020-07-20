<template>
  
  <div>
   <!-- 幻灯片 开始 -->
   <el-carousel :interval="4000" type="card" height="400px">
    <el-carousel-item>
      <h3 class="medium" >
      <img src="~/assets/photo/banner/javaEE.jpg">
      </h3>
    </el-carousel-item>
    <el-carousel-item>
      <h3 class="medium" src="~/assets/photo/banner/advertisment1.png">
        <img src="~/assets/photo/banner/ThinkPHP.png" >
      </h3>
    </el-carousel-item>
    <el-carousel-item>
      <h3 class="medium" src="~/assets/photo/banner/advertisment1.png">
        <img src="~/assets/photo/banner/python.jpg">
      </h3>
    </el-carousel-item>
    <el-carousel-item>
      <h3 class="medium" src="~/assets/photo/banner/advertisment1.png">
        <img src="~/assets/photo/banner/ubuntu.jpg">
      </h3>
    </el-carousel-item>
  </el-carousel>
  <!-- <el-carousel :interval="5000" arrow="always" height="400px">
    <el-carousel-item>
      <h3 class="medium" >
        <img src="~/assets/photo/banner/advertisment1.png">
      </h3>
    </el-carousel-item>
    <el-carousel-item>
      <h3 class="medium" >
        <img src="~/assets/photo/banner/javaEE.jpg">
      </h3>
    </el-carousel-item>
    <el-carousel-item>
      <h3 class="medium" >
        <img src="~/assets/photo/banner/javaEE.jpg">
      </h3>
    </el-carousel-item>
    <el-carousel-item>
      <h3 class="medium" >
        <img src="~/assets/photo/banner/javaEE.jpg">
      </h3>
    </el-carousel-item>
  </el-carousel> -->
  
  <!-- 幻灯片 结束 -->
    
     <div id="aCoursesList">
      <!-- 网校课程 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">热门课程</span>
            </h2>
          </header>
          <div>
            <!-- /无数据提示 开始-->
            <section class="no-data-wrap" v-if="data.total==0">
              <em class="icon30 no-data-ico">&nbsp;</em>
              <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
            </section>
            <!-- /无数据提示 结束-->
            <article v-if="data.total>0" class="comm-course-list">
              <ul id="bna" class="of">
                <li v-for="item in data.items" :key="item.id">
                    <div class="cc-l-wrap">
                        <section class="course-img">
                            <img :src="item.cover" class="img-responsive" alt="item.title">
                            <div class="cc-mask">
                                <a :href="'/course/'+item.id+'?token='+token" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                            </div>
                        </section>
                        <h3 class="hLh30 txtOf mt10">
                            <a :href="'/course/'+item.id" :title="item.title" class="course-title fsize18 c-333">{{ item.title }}</a>
                        </h3>
                        <section class="mt10 hLh20 of">
                            <span v-if="Number(item.price) === 0" class="fr jgTag bg-green">
                                <i class="c-fff fsize12 f-fA">免费</i>
                            </span>
                        </section>
                    </div>
                </li>
            </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a :href="'/course/'+'?token='+token" title="全部课程" class="comm-btn c-btn-2">全部课程</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /网校课程 结束 -->
      <!-- 网校名师 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">名师大咖</span>
            </h2>
          </header>
          <div>
            <!-- /无数据提示 开始-->
            <section class="no-data-wrap" v-if="teacherInfo.total==0">
              <em class="icon30 no-data-ico">&nbsp;</em>
              <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
            </section>
            <!-- /无数据提示 结束-->
            <article v-if="teacherInfo.total>0" class="i-teacher-list">
              <ul class="of">
                <li v-for="(item,insex) in teacherInfo.items" :key="index">
                  <section class="i-teach-wrap">
                    <div class="i-teach-pic">
                      <a :href="'/teacher/'+item.id+'?token='+token" v-bind:title="item.name" target="_blank">
                        <img :src="item.avatar" alt>
                      </a>
                    </div>
                    <div class="mt10 hLh30 txtOf tac">
                      <a :href="'/teacher/'+item.id" :title="item.name" target="_blank" class="fsize18 c-666">{{item.name}}</a>
                    </div>
                    <div class="hLh30 txtOf tac">
                      <span class="fsize14 c-999">{{item.career}}</span>
                    </div>
                    <div class="mt15 i-q-txt">
                      <p class="c-999 f-fA">{{item.intro}}</p>
                    </div>
                  </section>
                </li>
                
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a :href="'/teacher/'+'?token='+token" title="全部讲师" class="comm-btn c-btn-2">全部讲师</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /网校名师 结束 -->
    </div>
  </div>
</template>

<script>

import course from "@/api/course"
import teacher from "@/api/teacher"
const token=''
export default {
  data () {
    return {
      swiperOption: {
        //配置分页
        pagination: {
          el: '.swiper-pagination'//分页的dom节点
        },
        //配置导航
        navigation: {
          nextEl: '.swiper-button-next',//下一页dom节点
          prevEl: '.swiper-button-prev'//前一页dom节点
        }
      },
      teacherInfo:{},
      token:'',
    }
  },
  asyncData({params,error}){
      return course.getCourseListPage(1,8)
        .then(response=>{
          console.log(response.data.data)
          return {data: response.data.data}
        })
  },

  created(){
    this.init(),
    this.token = this.$route.query.token
    console.log(this.token)
  },

  methods: {
    gotoPage(page){
        course.getCourseListPage(page, 8).then(response => {
            this.data = response.data.data
        })
     },
     getTeacherInfo(){
      return teacher.getTeacherListPage(1,4)
        .then(response=>{
          this.teacherInfo = response.data.data
          console.log(this.teacherInfo)
        })
    },
    init(){
      this.getTeacherInfo()
    }
  },

    
}
</script>