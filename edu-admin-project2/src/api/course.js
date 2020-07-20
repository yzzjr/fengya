import request from '@/utils/request'

export default {
  saveCourseInfo(courseInfo){
       return request({
    url: '/eduservice/course',
    method: 'post',
    data: courseInfo
  })
  },

  getAllTeacherList(){
    return request({
      url: '/eduservice/teacher',
      method: 'get',
    })
  },

  //根据课程id查询课程信息
  getCourseInfoById(id){
    return request({
      url: '/eduservice/course/getCourseInfo/'+id,
      method: 'get',
    })
  },

  updateCourseInfoById(id,courseInfo){
    return request({
      url: '/eduservice/course/updateCourseInfo/'+id,
      method: 'post',
      data: courseInfo,
    })
  },

  getCourseList(){
    return request({
      url: '/eduservice/course/listCourse',
      method: 'get',
    })
  },
  getCoursePageList(page,limit,searchObj){
    return request({
        url: '/eduservice/course/moreConditionPageList/'+page + '/'+ limit,
        method: 'post',
        //传递条件对象,如果传递的是json数据，用data，如果不是json，使用params
        data: searchObj
    })
  },

  deleteCourseId(id){
    return request({
      url: '/eduservice/course/deleteCourse/'+id,
      method: 'delete',
    })
  },

  getAllCourseInfo(courseId){

    return request({
      url: '/eduservice/course/getAllCourseInfo/'+courseId,
      method: 'get',
    })
  },

  updateCourseStatus(courseId){

    return request({
      url: '/eduservice/course/publishCourse/'+courseId,
      method: 'get',
    })
  }
}