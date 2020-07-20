import request from '@/utils/request'

export default {
    getUserInfoToken(token) {   
      return request({
        url: `/ucenter/front/member/userInfo/`+token,
        method: 'get'
      })
    },

    memberLogin(login){
        return request({
            url: `/ucenter/front/member/memberLogin`,
            method: 'post',
            data: login,
        })
    },

    memberRegister(registerInfo){
      return request({
        url: `/ucenter/front/member/memberRegister`,
        method: 'post',
        data: registerInfo,
    })
    },

    message(message){
      return request({
        url: `/ucenter/front/member/MessageCheck`,
        method: 'post',
        data: message,
    })
    },
    
  }