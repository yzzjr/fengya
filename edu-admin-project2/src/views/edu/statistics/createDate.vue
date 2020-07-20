<script src="//unpkg.com/vue/dist/vue.js"></script>
<script src="//unpkg.com/element-ui@2.13.0/lib/index.js"></script>
<template>
    <div class="app-container">
      <!--表单-->
      <el-form :inline="true" class="demo-form-inline">
  
        <el-form-item label="日期">
          <el-date-picker
            v-model="day"
            type="date"
            placeholder="选择要统计的日期"
            value-format="yyyy-MM-dd" 
            :picker-options="pickerOptions"/>
        </el-form-item>
        <el-button
          :disabled="btnDisabled"
          type="primary"
          @click="createDate()">生成</el-button>
      </el-form>
  
    </div>
    
  </template>

  <script>

      import sta from '@/api/statistics'
      export default{
        data(){
            return {
                day: '',
            }
        },

        create(){

        },

        methods:{
            createDate(){
                
                sta.getStatisticsData(this.day)
                    .then(Response=>{
                        this.$message({
                        type: 'success',
                        message: '生成成功'
                        })
                    })

            }
        }



      }


  </script>