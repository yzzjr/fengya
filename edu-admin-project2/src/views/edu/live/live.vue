<template>
    
        
        <a href="static/live.html"  >进入直播间</a>
      

</template>

<script>
        export default{


                methods:{
                toLive(){
                        let routeUrl = this.$router.resolve({
                        path: "static/live.html",
                        });
                        window.open(routeUrl.href, '_blank');
                        this.$router.go(0);
                }
        }
        }
        


</script>