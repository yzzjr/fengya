<template>
    <div class="app-container">
      课程列表
      <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="searchObj.title" placeholder="课程名"/>
        </el-form-item>

        <el-form-item>
            <el-input v-model="searchObj.teacherId" placeholder="讲师id"/>
        </el-form-item>
  
        <el-form-item>
            <el-input v-model="searchObj.subjectParentId" placeholder="一级类别id"/>
        </el-form-item>

        <el-form-item>
            <el-input v-model="searchObj.subjectId" placeholder="二级类别id"/>
        </el-form-item>

  
  
        <el-button type="primary" icon="el-icon-search" @click="getListCourse()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form>
  
  
          <!-- 表格 -->
      <el-table
        v-loading="listLoading"
        :data="list"
        element-loading-text="数据加载中"
        border
        fit
        highlight-current-row>
  
        <el-table-column
          label="序号"
          width="70"
          align="center">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
  
        <el-table-column prop="title" label="课程名称" width="280" />
  
        <el-table-column prop="teacherId" label="讲师id" width="200" />
        <el-table-column prop="subjectParentId" label="一级类别id" width="200" />
        <el-table-column prop="subjectId" label="二级类别id" width="200" />
  
        <el-table-column prop="price" label="价格" />
  
        <el-table-column prop="lessonNum" label="课时时间" width="80"/>

        <el-table-column prop="status" label="课程状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === 'Draft'? 'primary': 'success'">{{scope.row.status==='Draft'?'未发布':'已发布'}}</el-tag>


          </template>


        </el-table-column>
  
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <router-link :to="'/course/info/'+scope.row.id">
              <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
            </router-link>
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
  
      </el-table>
  
     <!-- 分页 -->
       <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getListCourse"
    />
    </div>
  </template>
  
  <script>
  import course from '@/api/course'
  import teacher from '@/api/teacher'
  export default {
      //定义变量和初始值
      data() {
          return {
              listLoading: false, // 是否显示loading信息
              list:null, //每页数据list集合
              total:0, //总记录数
              page:1,//当前页
              limit:10,//每页显示记录数
              searchObj:{}//条件封装对象
          }
      },
      //在页面渲染之前调用，调用具体的某个方法
      created () {
          //调用方法
          this.getListCourse()
      },
      //写具体的方法调用
      methods: {
        removeDataById(id) {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                //调用方法进行删除
                //return 表示后面then还会执行
                return course.deleteCourseId(id)
            }).then(() => {
                //刷新整个页面
                this.getListCourse()
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                })
            }).catch((response)=>{
                //判断点击取消，还是删除失败
                if (response === 'cancel') {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    })
                } else {
                    this.$message({
                        type: 'error',
                        message: '删除失败'
                    })
                }        
            })
        },

         getListCourse(page=1) {
            this.page = page
            this.listLoading = true
            console.log(this.searchObj.title)
            course.getCoursePageList(this.page,this.limit,this.searchObj)
                .then(response => { //如果请求成功，返回状态码20000，执行then里面操作
                   // console.log(response)
                   //每页数据
                   this.list = response.data.rows
                   //总记录数
                   this.total = response.data.total

                   this.listLoading = false
                }) 
                .catch(response => { //如果请求失败，执行catch里面操作
                    console.log(response)
                })
        },
        resetData() {//查询全部讲师
            this.searchObj = {}
            this.getListCourse()
        },
          
      }
  }
  </script>
  