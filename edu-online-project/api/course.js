import request from '@/utils/request'

export default {
    getCourseListPage(page, limit) {   
      return request({
        url: `/eduservice/frontCourse/`+page+'/'+limit,
        method: 'get'
      })
    },

    getCourseInfoId(id){
        return request({
            url: `/eduservice/frontCourse/`+id,
            method: 'get'
          })
    },
    makeOrder(payInfo) {   
      return request({
        url: `/eduservice/AliPay/precreate`,
        method: 'post',
        data: payInfo
      })
    },

    checkOrder(payCheckInfo){
      return request({
        url: `/eduservice/AliPay/checkStatus`,
        method: 'post',
        data: payCheckInfo
      })
    }


  } 