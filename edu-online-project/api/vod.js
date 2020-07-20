import request from '@/utils/request'

export default {
    getPlayVideoAuth(vid,token) {   
      return request({
        url: `/vidservice/frontVideo/getPlayAuth/`+vid,
        method: 'post',
        data: token
      })
    },
    
    memberCheck(checkInfo){
      return request({
        url: `/vidservice/frontVideo/validate`,
        method: 'post',
        data: checkInfo
      })

    }

    
  }