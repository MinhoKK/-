<script setup>
import { ref, onMounted } from 'vue'
import { modifyBoard, detailBoard } from '@/api/board'
import { httpStatusCode } from '@/util/http-status'
import { useRouter, useRoute } from 'vue-router'

const route = useRoute()
const router = useRouter()
const boardId = ref(route.params.boardId)

const board = ref({})

const doModify = () => {
  modifyBoard(
    board.value,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        console.log('게시글 수정 완료')
        router.push({ name: 'board', params: { boardId: response.data.boardId } })
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

const goBack = () => {
  router.go(-1)
}

onMounted(() => {
  detailBoard(
    boardId.value,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        console.log('수정할 게시글 데이터 가져오기')
        board.value = response.data.board
      }
    },
    (error) => {
      console.error(error)
    }
  )
})
</script>

<template>
  <div class="container mt-4">
    <div class="row">
      <div class="col-md-8 mx-auto">
        <div class="card">
          <div class="card-header">
            <h2 class="card-title">게시글 수정하기</h2>
          </div>
          <div class="card-body">
            <div class="mb-3">
              <label for="boardId">게시판 번호</label>
              <input
                type="text"
                class="form-control readonly-input"
                id="boardId"
                v-model="board.boardId"
                readonly
              />
            </div>
            <div class="mb-3">
              <label for="userId">작성자</label>
              <input
                type="text"
                class="form-control readonly-input"
                id="userId"
                v-model="board.userId"
                readonly
              />
            </div>
            <div class="mb-3">
              <label for="title">제목</label>
              <input type="text" class="form-control" id="title" v-model="board.subject" />
            </div>
            <div class="mb-3">
              <label for="content">내용</label>
              <textarea
                class="form-control"
                id="content"
                rows="5"
                v-model="board.content"
              ></textarea>
            </div>
            <div class="mb-3">
              <label for="postDate">작성일</label>
              <input
                type="text"
                class="form-control readonly-input"
                id="postDate"
                v-model="board.postDate"
                readonly
              />
            </div>
            <div class="mb-3">
              <label for="hitPoint">조회수</label>
              <input
                type="text"
                class="form-control readonly-input"
                id="hitPoint"
                v-model="board.hitPoint"
                readonly
              />
            </div>
          </div>
          <div class="card-footer d-flex justify-content-end">
            <button type="button" class="btn btn-primary mr-2" @click="doModify()">저장</button>
            <button type="button" class="btn btn-secondary" @click="goBack()">취소</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.readonly-input {
  background-color: #e9ecef;
  color: #495057;
  cursor: not-allowed;
}
</style>
