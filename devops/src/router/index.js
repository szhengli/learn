import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/branch',
    component: Layout,
    redirect: '/branch/query',
    name: 'Branch',
    meta: { title: '分支管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'query',
        name: 'Query',
        component: () => import('@/views/query/index'),
        meta: { title: '分支查询', icon: 'table' }
      },
      {
        path: 'tree',
        name: 'Tree',
        component: () => import('@/views/tree/index'),
        meta: { title: 'Tree', icon: 'tree' }
      }
    ]
  },

  {
    path: '/resource',
    component: Layout,
    redirect: '/resource/service',
    name: 'Resource',
    meta: { title: '资源申请', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'service',
        name: 'Service',
        component: () => import('@/views/resource/service/index'),
        meta: { title: '系统', icon: 'table' }
      },
      {
        path: 'tree',
        name: 'Tree',
        component: () => import('@/views/tree/index'),
        meta: { title: 'Tree', icon: 'tree' }
      }
    ]
  },

  {
    path: '/devops',
    component: Layout,
    redirect: '/devops/change',
    name: 'Devops',
    meta: { title: '运维管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'change',
        name: 'Change',
        component: () => import('@/views/devops/change/index'),
        meta: { title: '变更管理', icon: 'table' }
      },
      {
        path: 'tree',
        name: 'Tree',
        component: () => import('@/views/tree/index'),
        meta: { title: 'CMDB', icon: 'tree' }
      }
    ]
  },

  {
    path: '/test',
    component: Layout,
    redirect: '/test/podlog',
    name: 'Test',
    meta: { title: '测试环境', icon: 'el-icon-view' },
    children: [
      {
        path: 'podlog',
        name: "Podlog",
        component: () => import('@/views/test/podlog/index'),
        meta: { title: '启动日志', icon: 'form' }
      },
      {
        path: 'testkube',
        component: () => import('@/views/test/testkube/index'),
        meta: { title: 'K8S集群', roles: ['admin'] }
      },
      {
        path: 'zookeeper',
        name: "zookeeper",
        component: () => import('@/views/test/zookeeper/index'),
        meta: { title: 'zookeeper', icon: 'form' }
      },
    ]
  },


]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/prod',
    component: Layout,
    redirect: '/prod/v3kue',
    name: 'prod',
    meta: {
      title: '生产环境',
      icon: 'el-icon-view'
    },
    children: [
      {
        path: 'v3kue',
        component: () => import('@/views/prod/v3kube/index'), // Parent router-view
        name: 'prodv3kube',
        meta: { title: 'K8S集群[v3]', roles: ['admin'] },
      },
      {
        path: 'v5kube',
        component: () => import('@/views/prod/v5kube/index'),
        meta: { title: 'K8S集群[v5]', roles: ['admin'] }
      },
      {
        path: 'polardb',
        component: () => import('@/views/prod/polardb/index'),
        meta: { title: 'PolarDB', roles: ['admin'] }
      },
      {
        path: 'netaccess',
        component: () => import('@/views/prod/netaccess/index'),
        meta: { title: '网络访问', roles: ['admin'] }
      },
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        meta: { title: 'External Link', icon: 'link' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
