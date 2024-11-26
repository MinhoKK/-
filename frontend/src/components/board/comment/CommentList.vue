<script setup>
import { ref } from 'vue'
import { registComment, deleteComment } from '@/api/comment'
import { httpStatusCode } from '@/util/http-status'

const props = defineProps({
  comments: Array,
  boardId: Number,
  getComments: Function,
  userId: String
})

const newComment = ref({
  boardId: props.boardId,
  commentContent: ''
})

const doRegistComment = async () => {
  await registComment(
    newComment.value,
    (response) => {
      if (response.status === httpStatusCode.CREATE) {
        console.log('게시글 댓글 생성')
        props.getComments()
        newComment.value.commentContent = ''
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

const doDeleteComment = async (commentId) => {
  await deleteComment(
    commentId,
    (response) => {
      if (response.status === httpStatusCode.NOCONTENT) {
        console.log('게시글 댓글 삭제')
        props.getComments()
      }
    },
    (error) => {
      console.error(error)
    }
  )
}
</script>

<template>
  <div class="px-6 pb-4">
    <h3 class="text-lg font-semibold mb-2">댓글</h3>
    <ul>
      <li v-for="comment in comments" :key="comment.id" class="mb-4">
        <div class="relative border border-gray-200 rounded-lg p-4">
          <p class="mt-2">{{ comment.commentContent }}</p>
          <p class="absolute bottom-0 right-0 text-xs text-gray-400">
            {{ comment.userId }} | {{ comment.commentCreateDate }}
            <span
              v-if="props.userId === comment.userId"
              @click="doDeleteComment(comment.commentId)"
              class="text-sm text-gray-400 text-xs cursor-pointer hover:text-red-600"
            >
              삭제
            </span>
          </p>
        </div>
      </li>
    </ul>
    <!-- Comment Input -->
    <div class="mt-4">
      <textarea
        v-model="newComment.commentContent"
        class="w-full p-2 border border-gray-300 rounded-md"
        rows="3"
        placeholder="댓글을 남겨주세요."
      ></textarea>
      <button @click="doRegistComment" class="mt-2 px-4 py-2 bg-blue-500 text-white rounded-md">
        작성
      </button>
    </div>
  </div>
</template>

<style scoped>
/* .card {
  border: 1px solid #ddd;
  border-radius: 5px;
}

.card-body {
  padding: 15px;
}

.card-text {
  font-size: 1rem;
}

.text-muted {
  font-size: 0.875rem;
}

.delete-btn {
  cursor: pointer;
}

.delete-btn:hover {
  color: red;
} */
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
</style>
