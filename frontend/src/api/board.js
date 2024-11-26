import { localAxios } from '@/util/http-common'

const local = localAxios()

function listBoard(filter, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.get(`/board`, { params: { filter } }).then(success).catch(fail)
}

function detailBoard(boardId, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.get(`/board/${boardId}`).then(success).catch(fail)
}

function registBoard(board, files, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')

  const formData = new FormData()
  formData.append('boardRegist', new Blob([JSON.stringify(board)], { type: 'application/json' }))

  files.forEach((file) => {
    formData.append('files', file)
  })

  local
    .post(`/board`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    .then(success)
    .catch(fail)
}

function getModifyBoard(boardId, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.get(`/board/modify/${boardId}`).then(success).catch(fail)
}

function modifyBoard(board, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.put(`/board`, JSON.stringify(board)).then(success).catch(fail)
}

function deleteBoard(boardId, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.delete(`/board/${boardId}`).then(success).catch(fail)
}

export { listBoard, detailBoard, registBoard, getModifyBoard, modifyBoard, deleteBoard }
