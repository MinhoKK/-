<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const props = defineProps({
  message: String,
  onClose: Function
})
</script>

<template>
  <div class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
    <div
      class="w-full max-w-sm overflow-hidden rounded-lg bg-white shadow-md duration-300 hover:scale-105 hover:shadow-xl"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="mx-auto mt-8 h-16 w-16 text-green-400"
        viewBox="0 0 20 20"
        fill="currentColor"
      >
        <path
          fill-rule="evenodd"
          d="M6.267 3.455a3.066 3.066 0 001.745-.723 3.066 3.066 0 013.976 0 3.066 3.066 0 001.745.723 3.066 3.066 0 012.812 2.812c.051.643.304 1.254.723 1.745a3.066 3.066 0 010 3.976 3.066 3.066 0 00-.723 1.745 3.066 3.066 0 01-2.812 2.812 3.066 3.066 0 00-1.745.723 3.066 3.066 0 01-3.976 0 3.066 3.066 0 00-1.745-.723 3.066 3.066 0 01-2.812-2.812 3.066 3.066 0 00-.723-1.745 3.066 3.066 0 010-3.976 3.066 3.066 0 00.723-1.745 3.066 3.066 0 012.812-2.812zm7.44 5.252a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
          clip-rule="evenodd"
        />
      </svg>
      <h1 class="mt-2 text-center text-2xl font-bold text-gray-500">Success</h1>
      <p class="my-4 text-center text-sm text-gray-500">{{ message }}</p>
      <div class="space-x-4 bg-gray-100 py-4 text-center">
        <button
          @click="onClose"
          class="inline-block rounded-md bg-red-500 px-10 py-2 font-semibold text-red-100 shadow-md duration-75 hover:bg-red-400"
        >
          닫기
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
</style>
