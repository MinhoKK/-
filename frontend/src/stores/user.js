import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { defineStore } from 'pinia'
import { jwtDecode } from 'jwt-decode'
import { useCookies } from 'vue3-cookies'

import { userJoin, userConfirm, findById, tokenRegeneration, logout } from '@/api/user'
import { httpStatusCode } from '@/util/http-status'

const { cookies } = useCookies()

export const useUserStore = defineStore(
  'userStore',
  () => {
    const router = useRouter()

    let userStoreData = sessionStorage.getItem('userStore')
    let parsedUserStore = null

    if (userStoreData) {
      parsedUserStore = JSON.parse(userStoreData)
    }

    const isLogin = ref(parsedUserStore ? parsedUserStore.isLogin : false)
    const isLoginError = ref(false)
    const userInfo = ref(parsedUserStore ? parsedUserStore.userInfo : null)
    const isValidToken = ref(parsedUserStore ? parsedUserStore.isValidToken : false)
    const storeErrorMessage = ref('')

    const userLogin = async (loginUser) => {
      await userConfirm(
        loginUser,
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let accessToken = response.headers.authorization
            isLogin.value = true
            isLoginError.value = false
            isValidToken.value = true
            sessionStorage.setItem('accessToken', accessToken)
          }
        },
        (error) => {
          console.log('로그인 실패!!!!')
          isLogin.value = false
          isLoginError.value = true
          isValidToken.value = false
          console.error(error)
          storeErrorMessage.value = error.response.data.error
        }
      )
    }

    const getUserInfo = async (token) => {
      let decodeToken = jwtDecode(token)
      console.log(decodeToken)
      await findById(
        // decodeToken.userId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            userInfo.value = response.data
          } else {
            console.log('유저 정보 없음!!!!')
          }
        },
        async (error) => {
          console.error(
            'g[토큰 만료되어 사용 불가능.] : ',
            error.response.status,
            error.response.statusText
          )
          isValidToken.value = false

          await tokenRegenerate()
        }
      )
    }

    const tokenRegenerate = async () => {
      await tokenRegeneration(
        //JSON.stringify(userInfo.value),
        (response) => {
          if (response.status === httpStatusCode.OK) {
            let accessToken = response.headers.authorization
            sessionStorage.setItem('accessToken', accessToken)
            isValidToken.value = true
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === httpStatusCode.UNAUTHORIZED) {
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              userInfo.value.userid,
              (response) => {
                if (response.status === httpStatusCode.OK) {
                  console.log('리프레시 토큰 제거 성공')
                } else {
                  console.log('리프레시 토큰 제거 실패')
                }
                alert('RefreshToken 기간 만료!!! 다시 로그인해 주세요.')
                isLogin.value = false
                userInfo.value = null
                isValidToken.value = false
                router.push({ name: 'user-login' })
              },
              (error) => {
                console.error(error)
                isLogin.value = false
                userInfo.value = null
              }
            )
          }
        }
      )
    }

    const userLogout = async () => {
      console.log('로그아웃 아이디 : ' + userInfo.value.userId)
      await logout(
        (response) => {
          if (response.status === httpStatusCode.NOCONTENT) {
            isLogin.value = false
            userInfo.value = null
            isValidToken.value = false
            sessionStorage.removeItem('accessToken')
            router.push({ name: 'home' })
          } else {
            console.error('유저 정보 없음!!!!')
          }
        },
        (error) => {
          console.log(error)
        }
      )
    }

    return {
      isLogin,
      isLoginError,
      userInfo,
      isValidToken,
      storeErrorMessage,
      userLogin,
      getUserInfo,
      tokenRegenerate,
      userLogout
    }
  },
  { persist: { storage: sessionStorage } }
)
