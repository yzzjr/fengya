<template>
  <div class="main">
    <div class="title">
      <a href="/login">登录</a>
      <span>·</span>
      <a class="active" href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <form action="register">
        <div class="input-prepend restyle">
          <input type="text" placeholder="你的昵称" v-model="registerInfo.nickname">
          <i class="iconfont icon-user"/>
        </div>
        <div class="input-prepend restyle no-radius">
          <input type="text" placeholder="手机号" v-model="registerInfo.mobile">
          <i class="iconfont icon-phone"/>
        </div>
        <div class="input-prepend restyle no-radius">
          <input type="text" placeholder="验证码" v-model="registerInfo.checkNum">
          <input type="button" class="sign-up-button" value="获取验证码"  @click="sendMessage">
          <i class="iconfont icon-phone"/>
        </div>
        <div class="input-prepend">
          <input type="password" placeholder="设置密码" v-model="registerInfo.password">
          <i class="iconfont icon-password"/>
        </div>
        <div class="input-prepend">
          <input type="password" placeholder="再次输入密码" v-model="checkPwd">
          <i class="iconfont icon-password"/>
        </div>
        <div class="btn">
          <input type="button" class="sign-up-button" value="注册"  @click="userRegister">
        </div>
        <p class="sign-up-msg">
          点击 “注册” 即表示您同意并愿意遵守简书
          <br>
          <a target="_blank" href="http://www.jianshu.com/p/c44d171298ce">用户协议</a>
          和
          <a target="_blank" href="http://www.jianshu.com/p/2ov8x3">隐私政策</a> 。
        </p>
      </form>
      <!-- 更多注册方式 -->
      
    </div>
  </div>
</template>

<script>
import '~/assets/css/sign.css'
import member from '@/api/member'
import Vue from 'vue';
import Element from 'element-ui';
Vue.use(Element, { size: 'small', zIndex: 3000 });
const defaultregisterInfo= {
        nickname:'',
        password:'',
        mobile:'',
        checkNum:''
      }
export default {
  layout: 'sign',
  data(){
    return{
      registerInfo: defaultregisterInfo,
      checkPwd:'',
      message:{
        messageMobile:''
      }
    }
  },

  created() {
      console.log('chapter created')
      this.init()
    },

  methods:{
    userRegister(){
      return member.memberRegister(this.registerInfo)
      .then(response=>{
            if(response.data.code == 20001 && response.data.data.result == 1){
              this.$message({
              type: 'error',
              message: '验证码错误'
              })
            }
            else if(response.data.code == 20000){
              this.$message({
              type: 'success',
              message: '注册成功!' 
            })
            this.$router.replace({ path: '/login'})
            }
            else{
              this.$message({
              type: 'error',
              message: '系统出错'
              })
            }
          })
    },

    sendMessage(){
      console.log(this.registerInfo.mobile)
      this.message.messageMobile = this.registerInfo.mobile
      return member.message(this.message)
      .then(response=>{
            this.$message({
              type: 'success',
              message: '验证码已发送!' 
            })
          })
          .catch(response=>{
            this.$message({
            type: 'error',
            message: '系统错误'
          })
          })
          
    },

    init(){
      this.registerInfo = {...defaultregisterInfo}
    }
  },

}
</script>