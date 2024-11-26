<script setup>
import { ref } from 'vue'
import { RouterLink } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { useBoardStore } from '@/stores/board'
import { storeToRefs } from 'pinia'

const userStore = useUserStore()
const boardStore = useBoardStore()

const { isLogin, userInfo } = storeToRefs(userStore)
const { boardList, board } = storeToRefs(boardStore)

const logout = () => {
  userStore.userLogout()
  boardList.value = []
  board.value = {}
}

const refresh = () => {
  console.log('토큰 재발급 실행')
  userStore.tokenRegenerate()
}

const activeTab = ref('home')
</script>

<template>
  <nav class="fixed top-0 left-0 w-full z-10 bg-white">
    <div
      class="container flex items-center justify-center p-6 mx-auto text-gray-600 capitalize dark:text-gray-300"
    >
      <RouterLink
        :to="{ name: 'home' }"
        @click="activeTab = 'home'"
        :class="[
          'border-b-2 mx-1.5 sm:mx-6',
          activeTab === 'home'
            ? 'text-blue-500 border-blue-500'
            : 'border-transparent hover:text-gray-800 dark:hover:text-gray-200 hover:border-blue-500'
        ]"
        exact
        >메인</RouterLink
      >

      <RouterLink
        :to="{ name: 'aptMain' }"
        @click="activeTab = 'aptMain'"
        :class="[
          'border-b-2 mx-1.5 sm:mx-6',
          activeTab === 'aptMain'
            ? 'text-blue-500 border-blue-500'
            : 'border-transparent hover:text-gray-800 dark:hover:text-gray-200 hover:border-blue-500'
        ]"
        >매물 검색</RouterLink
      >

      <template v-if="userInfo !== null">
        <RouterLink
          :to="{ name: 'checklist' }"
          @click="activeTab = 'checklist'"
          :class="[
            'border-b-2 mx-1.5 sm:mx-6',
            activeTab === 'checklist'
              ? 'text-blue-500 border-blue-500'
              : 'border-transparent hover:text-gray-800 dark:hover:text-gray-200 hover:border-blue-500'
          ]"
          >체크리스트</RouterLink
        >
        <RouterLink
          :to="{ name: 'board' }"
          @click="activeTab = 'board'"
          :class="[
            'border-b-2 mx-1.5 sm:mx-6',
            activeTab === 'board'
              ? 'text-blue-500 border-blue-500'
              : 'border-transparent hover:text-gray-800 dark:hover:text-gray-200 hover:border-blue-500'
          ]"
          >매물 게시판</RouterLink
        >
        <RouterLink
          :to="{ name: 'mypage' }"
          @click="activeTab = 'mypage'"
          :class="[
            'border-b-2 mx-1.5 sm:mx-6',
            activeTab === 'mypage'
              ? 'text-blue-500 border-blue-500'
              : 'border-transparent hover:text-gray-800 dark:hover:text-gray-200 hover:border-blue-500'
          ]"
          >마이페이지</RouterLink
        >

        <router-link
          to="/"
          @click.prevent="logout"
          class="border-b-2 border-transparent hover:text-gray-800 dark:hover:text-gray-200 hover:border-blue-500 mx-1.5 sm:mx-6"
          >{{ userInfo.name }}님 로그아웃</router-link
        >
      </template>

      <template v-if="userInfo === null">
        <RouterLink
          :to="{ name: 'join' }"
          @click="activeTab = 'join'"
          :class="[
            'border-b-2 mx-1.5 sm:mx-6',
            activeTab === 'join'
              ? 'text-blue-500 border-blue-500'
              : 'border-transparent hover:text-gray-800 dark:hover:text-gray-200 hover:border-blue-500'
          ]"
          >회원가입</RouterLink
        >

        <RouterLink
          :to="{ name: 'login' }"
          @click="activeTab = 'login'"
          :class="[
            'border-b-2 mx-1.5 sm:mx-6',
            activeTab === 'login'
              ? 'text-blue-500 border-blue-500'
              : 'border-transparent hover:text-gray-800 dark:hover:text-gray-200 hover:border-blue-500'
          ]"
          >로그인</RouterLink
        >
      </template>
    </div>
  </nav>
</template>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
</style>
