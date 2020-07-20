import request from '@/utils/request'

export default {
  getStatisticsData(day){
       return request({
    url: '/statistics/daily/getStatisticsDay/'+day,
    method: 'get',
  })
  },


  getCountData(searchObj){
    return request({
    url: `/statistics/daily/getCountData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
    method: 'get',
  })
  },      
}