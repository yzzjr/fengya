import request from '@/utils/request'

export default {
 

  addVideo(video){
    return request({
      url: '/eduservice/video/addVideo',
      method: 'post',
      data: video
    })
  },

  getVideoIndo(videoId){
    return request({
      url: '/eduservice/video/getVideoId/'+videoId,
      method: 'get',
    })
  },

  updateVideo(video){
    return request({
      url: '/eduservice/video/updateVideo',
      method: 'post',
      data: video
    })
  },
  deleteVideo(videoId){
    return request({
      url: '/eduservice/video/deleteVideoId/'+videoId,
      method: 'delete',
    })
  },

  //删除阿里云视频
  removeAliyunVideo(videoId){
    return request({
    url: '/vidservice/vod/'+videoId,
    method: 'delete',
    })
  },

}