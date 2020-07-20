import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },
  
  {
    path: '/subject',
    component: Layout,
    redirect: '/subject/list',
    name: '分类管理',
    meta: { title: '分类管理', icon: 'example' },
    children: [
      {
        path: 'list',
        name: '分类列表',
        component: () => import('@/views/edu/subject/index'),
        meta: { title: '分类列表', icon: 'table' }
      },
      {
        path: 'save',
        name: '导入分类',
        component: () => import('@/views/edu/subject/import'),
        meta: { title: '导入分类', icon: 'tree' }
      },
      
    ]
  },

  {
    path: '/course',
    component: Layout,
    redirect: '/course/list',
    name: '图书管理',
    meta: { title: '图书管理', icon: 'example' },
    children: [
      {
        path: 'list',
        name: '图书列表',
        component: () => import('@/views/edu/course/index'),
        meta: { title: '图书列表', icon: 'table' }
      },
      {
        path: 'info',
        name: '上架图书',
        component: () => import('@/views/edu/course/info'),
        meta: { title: '上架图书', icon: 'tree' }
      },
      {
        path: 'info/:id',
        name: 'EduCourseInfoEdit',
        component: () => import('@/views/edu/course/info'),
        meta: { title: '编辑图书基本信息', noCache: true },
        hidden: true
      },
      {
        path: 'chapter/:id',
        name: 'EduCourseChapterEdit',
        component: () => import('@/views/edu/course/chapter'),
        meta: { title: '编辑图书大纲', noCache: true },
        hidden: true
      },
      {
        path: 'publish/:id',
        name: 'EduCoursePublishEdit',
        component: () => import('@/views/edu/course/publish'),
        meta: { title: '图书上架', noCache: true },
        hidden: true
      }
      
    ]
  },
  {
    path: '/video',
    component: Layout,
    redirect: '/subject/list',
    name: '空位识别',
    meta: { title: '空位识别', icon: 'example' },
    children: [
      
      {
        path: 'save',
        name: '视频上传',
        component: () => import('@/views/edu/subject/video'),
        meta: { title: '视频上传', icon: 'tree' }
      },
      {
        path: 'save',
        name: 'TODO',
        component: () => import('@/views/edu/subject/video'),
        meta: { title: 'TODO', icon: 'tree' }
      },
      
    ]
  },
  

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
