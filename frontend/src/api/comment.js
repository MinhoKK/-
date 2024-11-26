import { localAxios } from '@/util/http-common'

const local = localAxios()

function listComment(boardId, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.get(`/comment/${boardId}`).then(success).catch(fail)
}

function registComment(comment, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.post(`/comment`, JSON.stringify(comment)).then(success).catch(fail)
}

function deleteComment(commentId, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.delete(`/comment/${commentId}`).then(success).catch(fail)
}

export { listComment, registComment, deleteComment }
