import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import BoardView from '../views/BoardView.vue'
import UserJoin from '../components/user/UserJoin.vue'
import UserLogin from '../components/user/UserLogin.vue'
import BoardDetail from '../components/board/BoardDetail.vue'
import RegistBoard from '../components/board/RegistBoard.vue'
import ModifyBoard from '../components/board/ModifyBoard.vue'
import AptMain from '@/views/MainMapView.vue'
import Chatbot from '@/views/ChatbotView.vue'
import ChecklistView from '@/views/ChecklistView.vue'

import { storeToRefs } from 'pinia'

import { useUserStore } from '@/stores/user'

// const onlyAuthUser = async (to, from, next) => {
//   const userStore = useUserStore()
//   const { userInfo, isValidToken } = storeToRefs(userStore)
//   const { getUserInfo } = userStore

//   let token = sessionStorage.getItem('accessToken')

//   if (userInfo.value != null && token) {
//     await getUserInfo(token)
//   }
//   if (!isValidToken.value || userInfo.value === null) {
//     next({ name: 'login' })
//   } else {
//     next()
//   }
// }

const router = createRouter({
  scrollBehavior() {
    return { top: 0 }
  },
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/join',
      name: 'join',
      component: UserJoin
    },
    {
      path: '/login',
      name: 'login',
      component: UserLogin
    },
    {
      path: '/user/mypage',
      name: 'mypage',
      component: () => import('@/components/user/MyPage.vue')
    },
    {
      path: '/board',
      name: 'board',
      component: BoardView
    },
    {
      path: '/board/:boardId',
      name: 'boardDetail',
      component: BoardDetail
    },
    {
      path: '/board/regist',
      name: 'registBoard',
      component: RegistBoard
    },
    {
      path: '/board/modify/:boardId',
      name: 'modifyBoard',
      component: ModifyBoard
    },
    // Apartment 조회
    {
      path: '/house',
      name: 'aptMain',
      component: AptMain
    },
    {
      path: '/chat',
      name: 'chatbot',
      component: Chatbot
    },
    {
      path: '/checklist',
      name: 'checklist',
      component: ChecklistView
    }
  ]
})

export default router
