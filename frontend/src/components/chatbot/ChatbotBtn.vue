<script setup>
import { ref } from 'vue'
import interior from './interior.vue'

const isChatVisible = ref(false) // 채팅 컴포넌트의 가시성 상태를 관리합니다.

function toggleChatVisibility() {
  isChatVisible.value = !isChatVisible.value // 채팅 컴포넌트의 가시성을 토글합니다.
}
</script>

<template>
  <!-- component -->
  <div class="relative">
    <button
      @click="toggleChatVisibility"
      class="z-10 text-white flex flex-col shrink-0 grow-0 justify-around fixed bottom-0 right-0 rounded-lg mr-1 mb-5 lg:mr-5 lg:mb-5 xl:mr-5 xl:mb-5"
    >
      <div
        class="flex justify-center items-center p-2 rounded-full border-4 border-white bg-blue-600"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
          class="w-8 h-8 lg:w-10 lg:h-10 xl:w-12 xl:h-12 lucide lucide-bot"
        >
          <path d="M12 8V4H8" />
          <rect width="16" height="12" x="4" y="8" rx="2" />
          <path d="M2 14h2" />
          <path d="M20 14h2" />
          <path d="M15 13v2" />
          <path d="M9 13v2" />
        </svg>
      </div>
    </button>
    <div
      v-show="isChatVisible"
      class="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center z-1"
    >
      <interior />
    </div>
  </div>
</template>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
</style>
