<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'
import { useUserStore } from '@/stores/user'
import { userConfirm } from '@/api/user'
import { httpStatusCode } from '@/util/http-status'
import ErrorComponent from '@/components/alert/Error.vue'

const router = useRouter()

const userStore = useUserStore()

const { isLogin, isLoginError, isValidToken } = storeToRefs(userStore)
const { userLogin, getUserInfo } = userStore

const loginUser = ref({
  userId: '',
  password: ''
})

const errorMessage = ref('')

const login = async () => {
  await userConfirm(
    loginUser.value,
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
      isLogin.value = false
      isLoginError.value = true
      isValidToken.value = false
      errorMessage.value = error.response.data.error
    }
  )

  let accessToken = sessionStorage.getItem('accessToken')
  if (isLogin.value) {
    getUserInfo(accessToken)
    router.replace('/')
  }
}

const clearError = () => {
  errorMessage.value = ''
}
</script>

<template>
  <div class="bg-white dark:bg-gray-900">
    <div class="flex justify-center h-screen">
      <div
        class="hidden bg-cover lg:block lg:w-2/3"
        style="
          background-image: url(https://ssafy-whereismyhouse.s3.eu-north-1.amazonaws.com/board/default_board_image.jpg);
        "
      >
        <div class="flex items-center h-full px-20 bg-gray-900 bg-opacity-40">
          <div>
            <h2 class="text-4xl font-bold text-white">CheckInHome</h2>
            <p class="max-w-xl mt-3 text-gray-300">
              집을 찾고 꾸미는 과정까지의 여정을 책임집니다.
            </p>
          </div>
        </div>
      </div>

      <div class="flex items-center w-full max-w-md px-6 mx-auto lg:w-2/6">
        <div class="flex-1">
          <div class="text-center">
            <h2 class="text-4xl font-bold text-center text-gray-700 dark:text-white">로그인</h2>
            <p class="mt-3 text-gray-500 dark:text-gray-300">당신의 정보를 입력해주세요.</p>
          </div>

          <div class="mt-8">
            <form class="mt-8 mb-2">
              <div class="mb-4 flex flex-col gap-6">
                <div class="relative">
                  <input
                    v-model="loginUser.userId"
                    type="text"
                    class="peer h-full w-full rounded-md border border-blue-gray-200 bg-transparent px-3 py-3 text-sm text-blue-gray-700 outline-none transition-all placeholder-shown:border-blue-gray-200 focus:border-2 focus:border-blue-300"
                    placeholder=" "
                  />
                  <label
                    class="before:content[' '] after:content[' '] pointer-events-none absolute left-0 -top-1.5 flex h-full w-full select-none text-[11px] font-normal leading-tight text-blue-gray-400 transition-all before:pointer-events-none before:mt-[6.5px] before:mr-1 before:box-border before:block before:h-1.5 before:w-2.5 before:rounded-tl-md before:border-t before:border-l before:border-blue-gray-200 before:transition-all after:pointer-events-none after:mt-[6.5px] after:ml-1 after:box-border after:block after:h-1.5 after:w-2.5 after:flex-grow after:rounded-tr-md after:border-t after:border-r after:border-blue-gray-200 after:transition-all peer-placeholder-shown:text-sm peer-placeholder-shown:leading-[4.1] peer-placeholder-shown:text-blue-gray-500 peer-placeholder-shown:before:border-transparent peer-placeholder-shown:after:border-transparent peer-focus:text-[11px] peer-focus:leading-tight peer-focus:text-blue-300 peer-focus:before:border-t-2 peer-focus:before:border-l-2 peer-focus:before:!border-blue-300 peer-focus:after:border-t-2 peer-focus:after:border-r-2 peer-focus:after:!border-blue-300 peer-disabled:text-transparent peer-disabled:before:border-transparent peer-disabled:after:border-transparent peer-disabled:peer-placeholder-shown:text-blue-gray-500"
                  >
                    아이디
                  </label>
                </div>
                <div class="relative">
                  <input
                    v-model="loginUser.password"
                    type="password"
                    class="peer h-full w-full rounded-md border border-blue-gray-200 bg-transparent px-3 py-3 text-sm text-blue-gray-700 outline-none transition-all placeholder-shown:border-blue-gray-200 focus:border-2 focus:border-blue-300"
                    placeholder=" "
                  />
                  <label
                    class="before:content[' '] after:content[' '] pointer-events-none absolute left-0 -top-1.5 flex h-full w-full select-none text-[11px] font-normal leading-tight text-blue-gray-400 transition-all before:pointer-events-none before:mt-[6.5px] before:mr-1 before:box-border before:block before:h-1.5 before:w-2.5 before:rounded-tl-md before:border-t before:border-l before:border-blue-gray-200 before:transition-all after:pointer-events-none after:mt-[6.5px] after:ml-1 after:box-border after:block after:h-1.5 after:w-2.5 after:flex-grow after:rounded-tr-md after:border-t after:border-r after:border-blue-gray-200 after:transition-all peer-placeholder-shown:text-sm peer-placeholder-shown:leading-[4.1] peer-placeholder-shown:text-blue-gray-500 peer-placeholder-shown:before:border-transparent peer-placeholder-shown:after:border-transparent peer-focus:text-[11px] peer-focus:leading-tight peer-focus:text-blue-300 peer-focus:before:border-t-2 peer-focus:before:border-l-2 peer-focus:before:!border-blue-300 peer-focus:after:border-t-2 peer-focus:after:border-r-2 peer-focus:after:!border-blue-300 peer-disabled:text-transparent peer-disabled:before:border-transparent peer-disabled:after:border-transparent peer-disabled:peer-placeholder-shown:text-blue-gray-500"
                  >
                    비밀번호
                  </label>
                </div>
              </div>
              <button
                class="mt-6 w-full rounded-lg bg-blue-300 py-3 text-center text-xs font-bold uppercase text-white transition-all hover:shadow-lg hover:shadow-blue-300/40 focus:opacity-[0.85]"
                type="button"
                @click="login"
              >
                로그인하기
              </button>
            </form>

            <p class="mt-6 text-sm text-center text-gray-400">
              계정이 없으신가요?
              <a href="#" class="text-blue-500 focus:outline-none focus:underline hover:underline"
                >회원가입</a
              >
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <ErrorComponent v-if="errorMessage !== ''" :message="errorMessage" :onClose="clearError" />
</template>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
</style>
