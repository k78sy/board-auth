import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import SignIn from '../views/user/SignIn.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/signup',
      component: () => import('../views/user/SignUp.vue'),
    },
    {
      path: '/signin',
      component: SignIn
    },
    {
      path: '/board/write',
      component: () => import('../views/board/BoardWriteMod.vue'),
    },
  ],
})

export default router
