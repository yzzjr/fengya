<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <form action="login">
        <div class="input-prepend restyle">
          <input type="text" placeholder="手机号" v-model="formObj.mobile">
          <i class="iconfont icon-phone"/>
        </div>
        <div class="input-prepend">
          <input type="password" placeholder="密码" v-model="formObj.password">
          <i class="iconfont icon-password"/>
        </div>
        <div class="btn">
          <input type="button" class="sign-in-button" value="登录" @click="login"/>
        </div>
      </form>
      
    </div>

  </div>
</template>




<script>
import '~/assets/css/sign.css'
import member from '@/api/member'

import Vue from 'vue'

import Element from 'element-ui';
Vue.use(Element, { size: 'small', zIndex: 3000 });


const defaultformObj = {
          mobile:'',
          password:'',
        }
export default {
   
    layout: 'sign',
    data(){
　　　　return{
        formObj:defaultformObj,
        token:'',
        BaseUrl:'localhost:3000/'
　　 　}
　　},

created(){
  console.log('login created')
      this.init()
},
  
  methods: {
    login() {
          
           member.memberLogin(this.formObj)
          .then((response) =>{
            if (response.data.code === 20000){
              this.$message({
                type: 'success',
                message: '登陆成功!'
              })
              this.token = response.data.data.token 
              this.$router.push({path: '/'+'?token='+this.token})
            } else {
              console.log(response)
              this.$message({
                type: 'error',
                message: '用户名或密码错误'
              })
            }
          })
        },

    init(){
      this.formObj = {...defaultformObj}
    }
  }

}
</script>