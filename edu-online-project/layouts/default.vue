<template>
  <div class="in-wrap">
    <!-- 公共头引入 -->
    <header id="header">
      <section class="container">
        <h1 id="logo">
          <a href="#" title="风雅艺术学校">
            <img src="~/assets/img/logo.png" width="100%" alt="风雅艺术学校">
          </a>
        </h1>
        <div class="h-r-nsl">
          <ul class="nav">
            <router-link :to="{path:'/',query:{token:token}}" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link :to="{path:'/course',query:{token:token}}" tag="li" active-class="current">
              <a>课程</a>
            </router-link>
            <router-link :to="{path:'/teacher',query:{token:token}}" tag="li" active-class="current">
              <a>名师</a>
            </router-link>
            <router-link :to="{path:'/',query:{token:token}}" tag="li" target="_blank"  @click.native = "flushCom">
              <a>直播</a>
            </router-link>
            
          </ul>
          <!-- / nav -->
          <ul class="h-r-login">
            <li v-if="!loginInfo.id" id="no-login">
                <a href="/login" title="登录">
                    <em class="icon18 login-icon">&nbsp;</em>
                    <span class="vam ml5">登录</span>
                </a>
                |
                <a href="/register" title="注册">
                    <span class="vam ml5">注册</span>
                </a>
            </li>
            <li v-if="loginInfo.id" id="is-login-two" class="h-r-user">
                <a href="#" title>
                    <img
                         :src="loginInfo.avatar"
                         width="30"
                         height="30"
                         class="vam picImg"
                         alt
                         >
                    <span id="userName" class="vam disIb" style="max-width:100%">{{ loginInfo.nickname }}</span>
                </a>
                <a href="/" title="退出" class="ml5">退出</a>
            </li>
            <!-- /未登录显示第1 li；登录后显示第2 li -->
        </ul>
          <aside class="h-r-search">
            <form action="#" method="post">
              <label class="h-r-s-box">
                <input type="text" placeholder="输入你想学的课程" name="queryCourse.courseName" value>
                <button type="submit" class="s-btn">
                  <em class="icon18">&nbsp;</em>
                </button>
              </label>
            </form>
          </aside>
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>
    <!-- /公共头引入 -->
      
    <nuxt/>

    <!-- 公共底引入 -->
    <footer id="footer">
      <section class="container">
        <div class>
          
          <div class="clear"></div>
        </div>
        <div class="b-foot">
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="#" title="关于我们" target="_blank">关于我们</a>|
                <a href="#" title="联系我们" target="_blank">联系我们</a>|
                <a href="#" title="帮助中心" target="_blank">帮助中心</a>|
                <a href="#" title="资源下载" target="_blank">资源下载</a>|
                <span></span>
                <span>Email：info@atguigu.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>©2020课程版权均归风雅艺术学校所有 </span>
              </section>
            </section>
          </section>
          <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wx-icon.png" alt>
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wb-icon.png" alt>
              </span>
            </section>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
    </footer>
    <!-- /公共底引入 -->
  </div>
</template>
<script>
import "~/assets/css/reset.css";
import "~/assets/css/theme.css";
import "~/assets/css/global.css";
import "~/assets/css/web.css";

import member from "@/api/member"
export default {

  data() {
    return {
      token: '',
      loginInfo: {}
    }
  },

  created(){
    this.token = this.$route.query.token
    if (this.token) {
      this.getTokenUserInfo(this.token)
    }
  },

  methods:{
    getTokenUserInfo(){
      member.getUserInfoToken(this.token)
      .then(response=>{
        const loginInfo =  response.data.data.member
        if (loginInfo) { // token未过期
          this.loginInfo = response.data.data.member
        } else {
          this.$router.push({ path: '/' })
        }
      })
    },

    flushCom(){
      let routeUrl = this.$router.resolve({
          path: "live.html",
     })
     window.open(routeUrl .href, '_blank')
      this.$router.go(0);
    }
  }



};
</script>