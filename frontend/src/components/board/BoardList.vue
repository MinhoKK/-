<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { listBoard } from '@/api/board'

const router = useRouter()

const boards = ref([])
const filter = ref('all')

const getBoards = async () => {
  await listBoard(
    filter.value,
    ({ data }) => {
      boards.value = data
    },
    (error) => {
      console.error(error)
    }
  )
}

const RegistBoard = () => {
  router.push({
    name: 'registBoard'
  })
}

const setFilter = (newFilter) => {
  filter.value = newFilter
  console.log('바뀐 필터 값:' + filter.value)
  getBoards()
}

onMounted(() => {
  getBoards()
})

const highestHitPointBoard = computed(() => {
  return boards.value.reduce((max, boardItem) => {
    return boardItem.board.hitPoint > (max.board?.hitPoint || 0) ? boardItem : max
  }, {})
})
</script>

<template>
  <!-- 글 작성하기 버튼 -->
  <div class="flex justify-center gap-4 mb-4">
    <!-- 글 작성하기 버튼 -->
    <div
      class="center relative inline-block select-none whitespace-nowrap rounded-lg bg-gray-300 py-2 px-3.5 align-baseline font-sans text-xs font-bold uppercase leading-none text-white"
    >
      <button class="mt-px font-noto-sans" @click="RegistBoard">글 작성하기</button>
    </div>
    <!-- 전체 게시글 버튼 -->
    <div
      class="center relative inline-block select-none whitespace-nowrap rounded-lg bg-gray-300 py-2 px-3.5 align-baseline font-sans text-xs font-bold uppercase leading-none text-white"
    >
      <button class="mt-px font-noto-sans" @click="setFilter('all')">전체 게시글</button>
    </div>
    <!-- 내 게시글 버튼 -->
    <div
      class="center relative inline-block select-none whitespace-nowrap rounded-lg bg-gray-300 py-2 px-3.5 align-baseline font-sans text-xs font-bold uppercase leading-none text-white"
    >
      <button class="mt-px font-noto-sans" @click="setFilter('my')">내 게시글</button>
    </div>
  </div>
  <!-- component -->
  <div class="flex flex-wrap justify-center gap-4">
    <div
      class="relative flex flex-col w-96 rounded-xl bg-white bg-clip-border text-gray-700 shadow-md font-noto-sans"
      v-for="boardItem in boards"
      :key="boardItem.board.boardId"
    >
      <!-- Highlight Icon for the board with highest hitPoint -->

      <svg
        v-if="boardItem === highestHitPointBoard"
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        stroke-width="1.5"
        stroke="gold"
        class="w-8 h-8 absolute top-2 left-2 z-1"
      >
        <path
          d="M7.21 15 2.66 7.14a2 2 0 0 1 .13-2.2L4.4 2.8A2 2 0 0 1 6 2h12a2 2 0 0 1 1.6.8l1.6 2.14a2 2 0 0 1 .14 2.2L16.79 15"
        />
        <path d="M11 12 5.12 2.2" />
        <path d="m13 12 5.88-9.8" />
        <path d="M8 7h8" />
        <circle cx="12" cy="17" r="5" />
        <path d="M12 18v-2h-.5" />
      </svg>
      <!-- 이미지 -->
      <div class="relative h-64 overflow-hidden rounded-t-xl bg-white bg-clip-border">
        <img
          v-if="boardItem.files.length > 0"
          :src="boardItem.files[0]"
          alt="Attached Image"
          class="h-full w-full object-cover"
        />
        <!-- 파일이 없을 경우 기본 이미지 표시 -->
        <img
          v-else
          src="https://ssafy-whereismyhouse.s3.eu-north-1.amazonaws.com/board/default_board_image.jpg"
          alt="Default Image"
          class="h-full w-full object-cover"
        />
      </div>
      <!-- 내용 -->
      <div class="p-6">
        <div class="mb-2 flex items-center justify-between">
          <p
            class="block font-sans text-base font-medium leading-relaxed text-blue-gray-900 antialiased font-noto-sans"
          >
            {{ boardItem.board.subject }}
          </p>
          <span
            class="text-gray-400 mr-3 inline-flex items-center lg:ml-auto md:ml-0 ml-auto leading-none text-sm pr-3 py-0 border-r-2 border-gray-200"
          >
            <svg
              class="w-4 h-4 mr-1"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z"
              />
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"
              />
            </svg>
            {{ boardItem.board.hitPoint }}
          </span>
          <span class="text-gray-400 inline-flex items-center leading-none text-sm">
            <svg
              class="w-4 h-4 mr-1"
              stroke="currentColor"
              stroke-width="2"
              fill="none"
              stroke-linecap="round"
              stroke-linejoin="round"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M8.625 12a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H8.25m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0H12m4.125 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm0 0h-.375M21 12c0 4.556-4.03 8.25-9 8.25a9.764 9.764 0 0 1-2.555-.337A5.972 5.972 0 0 1 5.41 20.97a5.969 5.969 0 0 1-.474-.065 4.48 4.48 0 0 0 .978-2.025c.09-.457-.133-.901-.467-1.226C3.93 16.178 3 14.189 3 12c0-4.556 4.03-8.25 9-8.25s9 3.694 9 8.25Z"
              />
            </svg>
            {{ boardItem.commentCount }}
          </span>
        </div>
        <p
          class="block font-sans text-sm font-normal leading-normal text-gray-700 antialiased opacity-75 font-noto-sans"
        >
          {{ boardItem.board.content }}
        </p>
      </div>
      <!-- 자세히보기 버튼 -->
      <div class="p-6 pt-0">
        <RouterLink
          :to="{ name: 'boardDetail', params: { boardId: boardItem.board.boardId } }"
          class="block w-full select-none rounded-lg bg-blue-gray-900/10 py-3 px-6 text-center align-middle font-sans text-xs font-bold uppercase text-white transition-all hover:scale-105 focus:scale-105 focus:opacity-[0.85] active:scale-100 active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none font-noto-sans"
          :style="{ backgroundColor: '#80dfff' }"
        >
          자세히보기
        </RouterLink>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';

.button-bg {
  background-color: #80dfff;
}

.font-noto-sans {
  font-family: 'Noto Sans KR', sans-serif;
}
</style>
