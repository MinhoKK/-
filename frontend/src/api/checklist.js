import { localAxios } from '@/util/http-common'

const local = localAxios()

function checklistView(success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.get(`/checklist`).then(success).catch(fail)
}

function registItem(item, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.post(`/checklist/item`, JSON.stringify(item)).then(success).catch(fail)
}

function deleteItem(itemId, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.delete(`/checklist/item/${itemId}`).then(success).catch(fail)
}

function changeItemChecked(itemId, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.patch(`/checklist/apt/item/checked/${itemId}`).then(success).catch(fail)
}

function registChecklistApt(aptId, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.post(`/checklist/apt/${aptId}`).then(success).catch(fail)
}

function checklistAptView(checklistAptId, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.get(`/checklist/apt/item/${checklistAptId}`).then(success).catch(fail)
}

function checklistApt(aptId, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.get(`/checklist/apt/${aptId}`).then(success).catch(fail)
}

function deletechecklistApt(checklistAptId, success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.delete(`/checklist/apt/${checklistAptId}`).then(success).catch(fail)
}

function findMyChecklist(success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  local.get(`/checklist/mypage`).then(success).catch(fail)
}

export {
  checklistView,
  registItem,
  deleteItem,
  changeItemChecked,
  registChecklistApt,
  checklistAptView,
  checklistApt,
  deletechecklistApt,
  findMyChecklist
}
