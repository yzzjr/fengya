import request from '@/utils/request'

export default {
    getTeacherListPage(page, limit) {   
      return request({
        url: `/eduservice/frontTeacher/`+page+'/'+limit,
        method: 'get'
      })
    },

    getTeacherInfoId(id){
        return request({
            url: `/eduservice/frontTeacher/`+id,
            method: 'get'
          })
    }
  }