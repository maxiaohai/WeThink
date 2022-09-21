import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export const constantRoutes = [
  {
    path: '/',
    redirect: '/comment',
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    meta: { title: 'login' },
  },
  {
    path: '/register',
    component: () => import('@/views/register/index'),
    meta: { title: 'register' },
  },
  {
    path: '/comment',
    component: () => import('@/views/comment/index'),
    meta: { title: 'comment' },
  },
]

const createRouter = () => new Router({
  // mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher
}

export default router
