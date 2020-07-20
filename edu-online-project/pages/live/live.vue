<template>
    <a :href = "'../../../live.html?token='+token">进入直播间</a>
</template>
<script>
    
    export default {
        data(){
            return{
                token : '',
            }
        },

        beforeCreated(){
            this.token = this.$route.query.token
            console.log(this.$route.query.token)
        },

        methods:{
            toLive(){
                window.location.href = "../../../live.html"
            }
        }
    }
</script>