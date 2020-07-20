import request from '@/utils/request'

export default {
  getAllSubjectList(){
       return request({
    url: '/eduservice/subject',
    method: 'get',

  })
  },

  addSubjectOne(subject){
    return request({
        url: '/eduservice/subject/addOneLevel',
        method: 'post',
        data: subject
      })
  },

  addSubjectTwo(subject){
    return request({
        url: '/eduservice/subject/addTwoLevel',
        method: 'post',
        data: subject
      })
  },


  removeSubjectById(id){
    return request({
        url: '/eduservice/subject/'+id,
        method: 'delete',
    
      })
  }
  
   
}