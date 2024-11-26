<script setup>
import router from '@/router'
import { ref } from 'vue'
import { registBoard } from '@/api/board'
import { httpStatusCode } from '@/util/http-status'
import ErrorComponent from '@/components/alert/Error.vue'
import { Vue3Lottie } from 'vue3-lottie'
import loading3 from '/src/assets/json/loading3.json'

const board = ref({
  userId: JSON.parse(sessionStorage.getItem('userStore')).userInfo.userId,
  subject: null,
  content: null
})

const errorMessage = ref('')
const isLoading = ref(false)

const files = ref([])

const handleFileChange = (event) => {
  files.value = Array.from(event.target.files)
}

const regist = () => {
  isLoading.value = true
  registBoard(
    board.value,
    files.value,
    (response) => {
      router.push({ name: 'boardDetail', params: { boardId: response.data.boardId } })
      isLoading.value = false
    },
    (error) => {
      errorMessage.value = error.response.data.error
      isLoading.value = false
    }
  )
}

const clearError = () => {
  errorMessage.value = ''
}
</script>

<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card">
          <div class="card-header">게시글 작성</div>
          <div class="card-body">
            <div class="form-group">
              <label for="subject">게시글 제목</label>
              <input
                type="text"
                class="form-control"
                id="subject"
                name="subject"
                v-model="board.subject"
                required
              />
            </div>
            <div class="form-group">
              <label for="content">게시글 내용</label>
              <textarea
                class="form-control"
                id="content"
                name="content"
                rows="5"
                v-model="board.content"
                required
              ></textarea>
            </div>
            <div class="form-group">
              <label for="files">파일 업로드</label>
              <input
                type="file"
                class="form-control"
                id="files"
                name="files"
                multiple
                @change="handleFileChange"
              />
            </div>
            <div v-if="isLoading" class="absolute inset-0 flex items-center justify-center">
              <div class="p-4 rounded-lg">
                <Vue3Lottie
                  class="h-96 w-96"
                  :animationData="loading3"
                  :height="200"
                  :width="200"
                  :speed="1"
                />
              </div>
            </div>
            <button type="submit" class="btn btn-primary" @click="regist">생성</button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <ErrorComponent v-if="errorMessage !== ''" :message="errorMessage" :onClose="clearError" />
</template>

<style scoped></style>
