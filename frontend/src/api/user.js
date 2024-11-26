import { localAxios } from '@/util/http-common'

const local = localAxios()

// 회원가입
async function userJoin(user, success, fail) {
  await local.post(`/user/join`, user).then(success).catch(fail)
}

// 로그인
async function userConfirm(user, success, fail) {
  await local.post(`/user/login`, user).then(success).catch(fail)
}

// 회원 정보 조회
async function findById(success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  await local.get(`/user/info`).then(success).catch(fail)
}

// 토큰 재발급
// axios 요청 시 쿠키에 refreshToken 저장
async function tokenRegeneration(success, fail) {
  await local.post(`/user/refresh`).then(success).catch(fail)
}

// 로그아웃
async function logout(success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  await local.delete(`/user/logout`).then(success).catch(fail)
}

// 마이페이지 조회
async function mypage(success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  await local.get(`/user/mypage`).then(success).catch(fail)
}

// 찜 아파트 조회
async function likeAptList(success, fail) {
  local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
  await local.get(`/user/likes`).then(success).catch(fail)
}

export { userJoin, userConfirm, findById, tokenRegeneration, logout, mypage, likeAptList }
