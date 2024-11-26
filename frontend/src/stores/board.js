import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { defineStore } from 'pinia'

import {
  listBoard,
  detailBoard,
  registBoard,
  getModifyBoard,
  modifyBoard,
  deleteBoard
} from '@/api/board'
import { httpStatusCode } from '@/util/http-status'

export const useBoardStore = defineStore(
  'boardStore',
  () => {
    const router = useRouter()

    const boardList = ref([])
    const board = ref({})

    const doListBoard = async () => {
      await listBoard(
        (response) => {
          if (response.status === httpStatusCode.OK) {
            boardList.value = response.data
          }
        },
        (error) => {
          console.log('공지사항 리스트 조회 실패')
          console.error(error)
        }
      )
    }

    const doDetailBoard = async (boardId) => {
      await detailBoard(
        boardId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            console.log('게시글 상세 조회 성공')
            board.value = response.data
          }
        },
        (error) => {
          console.error(error)
        }
      )
    }

    const doRegistBoard = async (board) => {
      await registBoard(
        board,
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            console.log('게시글 생성 성공')
            router.push({ name: 'board', params: { boardId: response.data.boardId } })
          }
        },
        (error) => {
          console.error(error)
        }
      )
    }

    const doModifyBoard = async (board) => {
      await modifyBoard(
        board,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            console.log('게시글 수정 완료')
            console.log(board)
            console.log(board.value)
            console.log(response.data)
            router.push({ name: 'board', params: { boardId: response.data.boardId } })
          }
        },
        (error) => {
          console.error(error)
        }
      )
    }

    const doDeleteBoard = async (boardId) => {
      await deleteBoard(
        boardId,
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

    return {
      boardList,
      board,
      doListBoard,
      doDetailBoard,
      doDeleteBoard,
      doRegistBoard,
      doModifyBoard
    }
  },
  { persist: { storage: sessionStorage } }
)
