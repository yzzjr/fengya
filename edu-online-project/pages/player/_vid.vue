<template>
    <div>
  
      <!-- 阿里云视频播放器样式 -->
      <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/2.8.1/skins/default/aliplayer-min.css" >
      <!-- 阿里云视频播放器脚本 -->
      <script charset="utf-8" type="text/javascript" src="https://g.alicdn.com/de/prismplayer/2.8.1/aliplayer-min.js"></script>
      <!-- 启用私有加密的防调式 -->
      <script src="https://g.alicdn.com/de/prismplayer/2.8.0/hls/aliplayer-vod-anti-min.js"></script>
      <!--组件-->
      <script type="text/javascript" charset="utf-8" src="https://player.alicdn.com/aliplayer/presentation/js/aliplayercomponents.min.js"></script>
  
      <!-- 定义播放器dom -->
      <div id="J_prismPlayer" class="prism-player" /> 
    </div>
  </template>

  <script>
    import vod from '@/api/vod'
    export default {
        
      layout: 'video',//应用video布局
      asyncData({ params, query,error }) {
        console.log(params.vid)
        
        return vod.getPlayVideoAuth(params.vid,query.token)
        .then(response => {
          console.log(response.data.data)
          return {
            vid: params.vid,
            playAuth: response.data.data.playAuth
          }
        })
      },

      data(){
        token:''
        videoId:''
      },

      created(){
        this.token = this.$route.query.token
        this.videoId = this.$route.params.vid
        
      },

      methods:{
        play(vid,token){
          vod.getPlayVideoAuth(vid1,token)
          .then(response => {
          console.log(response.data.data)
          return {
            vid: vid1,
            playAuth: response.data.data.playAuth
          }
        })
        }
      },

      mounted() {
    
    /* eslint-disable no-undef */
    /*const player = */new Aliplayer({
        id: 'J_prismPlayer',
        vid: this.vid, // 视频id
        playauth: this.playAuth, // 播放凭证
        encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
        width: '100%',
        height: '500px',
        cover: 'http://video.yaohuan.work/image/default/DF3E77AA7D894DA5975068C5F5201D63-6-2.jpg',
        qualitySort: 'asc', // 清晰度排序

        snapshot:true,
        mediaType: 'video', // 返回音频还是视频
        autoplay: false, // 自动播放
        isLive: false, // 直播
        rePlay: false, // 循环播放
        preload: true,
        controlBarVisibility: 'hover', // 控制条的显示方式：鼠标悬停
        useH5Prism: true, // 播放器类型：html5
          components: [
      // 跑马灯组件
        {
          name: 'BulletScreenComponent',
          type: AliPlayerComponent.BulletScreenComponent,
          /** 跑马灯组件三个参数 text, style, bulletPosition
       * text: 跑马灯文字内容
       * style: 跑马灯样式
       * bulletPosition: 跑马灯位置, 可选的值为 'top' (顶部), 'bottom' (底部), 'random' (随机), 不传值默认为 'random'
       */
          args: ['Hello，欢迎来到风雅艺术学校', { fontSize: '16px', color: '#00c1de' }, 'random']
        },
        
        // 弹幕组件
      ],
      //截图组件
      skinLayout:[
        {name: "bigPlayButton", align: "blabs", x: 30, y: 80},
        {
          name: "H5Loading", align: "cc"
        },
        {name: "errorDisplay", align: "tlabs", x: 0, y: 0},
        {name: "infoDisplay"},
        {name:"tooltip", align:"blabs",x: 0, y: 56},
        {name: "thumbnail"},
        {
          name: "controlBar", align: "blabs", x: 0, y: 0,
          children: [
            {name: "progress", align: "blabs", x: 0, y: 44},
            {name: "playButton", align: "tl", x: 15, y: 12},
            {name: "timeDisplay", align: "tl", x: 10, y: 7},
            {name: "fullScreenButton", align: "tr", x: 10, y: 12},
            {name:"subtitle", align:"tr",x:15, y:12},
            {name:"setting", align:"tr",x:15, y:12},
            {name: "volume", align: "tr", x: 15, y: 10},
            {name: "snapshot", align: "tr", x: 5, y: 12},
          ]
        }
      ]
    }, function(player) {
        console.log('播放器创建成功')
        player.on("snapshoted", function(data) {
          var pictureData = data.paramData.base64
          var downloadElement = document.createElement('a')
          downloadElement.setAttribute('href', pictureData)
          var fileName = 'Aliplayer' + Date.now() + '.png'
          downloadElement.setAttribute('download', fileName)
          downloadElement.click()
          pictureData = null
        });
    })
    /* h5截图按钮, 截图成功回调 */
    
    

    



    }

    }
    </script>