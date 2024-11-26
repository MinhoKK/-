<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { detailBoard, deleteBoard } from '@/api/board'
import { listComment } from '@/api/comment'
import { httpStatusCode } from '@/util/http-status'
import CommentList from './comment/CommentList.vue'

const router = useRouter()
const route = useRoute()
const boardId = ref(route.params.boardId)

const board = ref({})
const files = ref([])
const comments = ref([])
const userId = JSON.parse(sessionStorage.getItem('userStore')).userInfo.userId

const isMyBoard = ref(board.value.userId === userId)

const getBoard = async () => {
  await detailBoard(
    boardId.value,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        board.value = response.data.board
        files.value = response.data.files
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

const getComments = async () => {
  await listComment(
    boardId.value,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        console.log('게시글 댓글 조회 성공')
        comments.value = response.data
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

const doDelete = async () => {
  if (confirm('게시글을 삭제하시겠습니가?')) {
    await deleteBoard(
      boardId.value,
      (response) => {
        if (response.status === httpStatusCode.NOCONTENT) {
          console.log('게시글 삭제 성공')
          router.push({ name: 'board' })
        }
      },
      (error) => {
        console.error(error)
      }
    )
  }
}

const modifyBoard = async () => {
  await router.push({
    name: 'modifyBoard',
    params: { boardId: boardId.value }
  })
}

watch(board, (newBoard) => {
  if (newBoard.userId === userId) {
    isMyBoard.value = true
  } else {
    isMyBoard.value = false
  }
})

onMounted(() => {
  getBoard()
  getComments()
})

const getFormattedDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleString('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}
</script>

<template>
  <!-- component -->
  <div class="flex justify-center items-center min-h-screen mt-4">
    <div
      class="relative flex w-full max-w-3xl flex-col rounded-xl bg-clip-border text-gray-700 shadow-md border-2 border-gray-300"
    >
      <h2 class="text-2xl font-bold text-center border-b-2 border-gray-300 pb-4">
        <span>{{ board.subject }}</span>
        <div class="text-sm text-gray-500">
          <span
            class="text-gray-400 mr-3 inline-flex items-center lg:ml-auto md:ml-0 ml-auto leading-none text-sm pr-3 py-0 border-r-2 border-gray-200"
          >
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
                d="M17.982 18.725A7.488 7.488 0 0 0 12 15.75a7.488 7.488 0 0 0-5.982 2.975m11.963 0a9 9 0 1 0-11.963 0m11.963 0A8.966 8.966 0 0 1 12 21a8.966 8.966 0 0 1-5.982-2.275M15 9.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"
              />
            </svg>
            {{ board.userId }}
          </span>
          <span
            class="text-gray-400 mr-3 inline-flex items-center lg:ml-auto md:ml-0 ml-auto leading-none text-sm pr-3 py-0 border-r-2 border-gray-200"
          >
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
                d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 0 1 2.25-2.25h13.5A2.25 2.25 0 0 1 21 7.5v11.25m-18 0A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75m-18 0v-7.5A2.25 2.25 0 0 1 5.25 9h13.5A2.25 2.25 0 0 1 21 11.25v7.5m-9-6h.008v.008H12v-.008ZM12 15h.008v.008H12V15Zm0 2.25h.008v.008H12v-.008ZM9.75 15h.008v.008H9.75V15Zm0 2.25h.008v.008H9.75v-.008ZM7.5 15h.008v.008H7.5V15Zm0 2.25h.008v.008H7.5v-.008Zm6.75-4.5h.008v.008h-.008v-.008Zm0 2.25h.008v.008h-.008V15Zm0 2.25h.008v.008h-.008v-.008Zm2.25-4.5h.008v.008H16.5v-.008Zm0 2.25h.008v.008H16.5V15Z"
              />
            </svg>
            {{ getFormattedDate(board.postDate) }}
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
                d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z"
              />
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"
              />
            </svg>
            {{ board.hitPoint }}
          </span>
        </div>
      </h2>
      <!-- Bootstrap carousel -->
      <div
        v-if="files && files.length > 0"
        id="imageCarousel"
        class="carousel slide"
        data-bs-ride="carousel"
      >
        <div class="carousel-inner border-b-1 border-gray-300 p-2">
          <div
            v-for="(file, index) in files"
            :key="index"
            :class="{ 'carousel-item': true, active: index === 0 }"
          >
            <img :src="file" class="d-block w-100 object-cover" alt="Attached Image" />
          </div>
        </div>
        <button
          class="carousel-control-prev"
          type="button"
          data-bs-target="#imageCarousel"
          data-bs-slide="prev"
        >
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button
          class="carousel-control-next"
          type="button"
          data-bs-target="#imageCarousel"
          data-bs-slide="next"
        >
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
      <!-- 기본 이미지 (첨부 파일 없는 경우)-->
      <div v-else class="border-b-1 border-gray-300 p-2">
        <img
          src="https://ssafy-whereismyhouse.s3.eu-north-1.amazonaws.com/board/default_board_image.jpg"
          alt="Default Image"
          class="h-full w-full object-cover"
        />
      </div>
      <div class="p-6 border-t-2 border-gray-300">
        <p
          class="block font-sans text-sm font-normal leading-normal text-gray-700 antialiased opacity-75"
        >
          {{ board.content }}
        </p>
        <div class="p-6 flex justify-end space-x-2">
          <div v-if="isMyBoard" class="space-x-2">
            <button
              @click="modifyBoard"
              class="px-2 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M11.42 15.17 17.25 21A2.652 2.652 0 0 0 21 17.25l-5.877-5.877M11.42 15.17l2.496-3.03c.317-.384.74-.626 1.208-.766M11.42 15.17l-4.655 5.653a2.548 2.548 0 1 1-3.586-3.586l6.837-5.63m5.108-.233c.55-.164 1.163-.188 1.743-.14a4.5 4.5 0 0 0 4.486-6.336l-3.276 3.277a3.004 3.004 0 0 1-2.25-2.25l3.276-3.276a4.5 4.5 0 0 0-6.336 4.486c.091 1.076-.071 2.264-.904 2.95l-.102.085m-1.745 1.437L5.909 7.5H4.5L2.25 3.75l1.5-1.5L7.5 4.5v1.409l4.26 4.26m-1.745 1.437 1.745-1.437m6.615 8.206L15.75 15.75M4.867 19.125h.008v.008h-.008v-.008Z"
                />
              </svg>
            </button>
            <button
              @click="doDelete"
              class="px-2 py-2 bg-red-500 text-white rounded-md hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-offset-2"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"
                />
              </svg>
            </button>
          </div>
          <router-link
            :to="{ name: 'board' }"
            class="px-2 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-400 focus:ring-offset-2"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="w-6 h-6"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M8.25 6.75h12M8.25 12h12m-12 5.25h12M3.75 6.75h.007v.008H3.75V6.75Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0ZM3.75 12h.007v.008H3.75V12Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm-.375 5.25h.007v.008H3.75v-.008Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Z"
              />
            </svg>
          </router-link>
        </div>
      </div>
      <div>
        <CommentList
          :comments="comments"
          :boardId="boardId"
          :getComments="getComments"
          :userId="userId"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
</style>
