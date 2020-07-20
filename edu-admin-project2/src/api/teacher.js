import request from '@/utils/request'

export default{
    getTeacherPageList(page,limit,searchObj){
        return request({
            url: '/eduservice/teacher/moreCondtionPageList/'+page + '/'+ limit,
            method: 'post',
            //传递条件对象,如果传递的是json数据，用data，如果不是json，使用params
            data:searchObj
        })
    },
    deleteTeacherId(id){
        return request({
            url: '/eduservice/teacher/'+id,
            method: 'delete'
        })
    },

    //添加教师
    saveTeacher(teacher){
        return request({
            url: '/eduservice/teacher/addTeacher',
            method: 'post',
            data: teacher
        })
    },

    getTeacherId(id){
        return request({
            url: '/eduservice/teacher/getTeacherInfo/'+id,
            method: 'get',
            
        })
    },

    updateTeacherId(teacher){
        return request({
            url: '/eduservice/teacher/updateTeacher',
            method: 'post',
            data: teacher
        })
    }
}
