<template>
  <div class="container mt-4">
    <div class="row">
      <div class="col-md-6 mx-auto">
        <div class="card">
          <div class="card-header bg-light">
            <h2 class="card-title">마이페이지</h2>
          </div>

          <div class="card-body">
            <div class="mb-3"><strong>아이디:</strong> {{ user.userId }}</div>
            <div class="mb-3"><strong>이름:</strong> {{ user.name }}</div>
            <div class="mb-3"><strong>주소:</strong> {{ user.address }}</div>
            <div class="mb-3"><strong>역할:</strong> {{ user.role }}</div>

            <h3 class="mt-4">찜한 아파트</h3>
            <div class="list-group">
              <a
                v-for="apt in aptList"
                :key="apt.aptId"
                href="#"
                class="list-group-item list-group-item-action"
                @click.prevent="openDetailInfo(apt.aptId)"
              >
                <div :id="apt.aptId">{{ apt.aptName }}</div>
                <div>
                  <small>도로명주소 : {{ apt.roadAddress }}</small>
                </div>
              </a>
              <a
                v-if="aptList.length === 0"
                href="#"
                class="list-group-item list-group-item-action disabled"
                >찜한 아파트가 없습니다.</a
              >
            </div>
          </div>
        </div>

        <!-- DetailInfo 모달 -->
        <div
          v-if="showDetail"
          class="z-1 fixed z-10 inset-0 overflow-y-auto flex items-center justify-center"
        >
          <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 sm:block sm:p-0">
            <!-- 모달 내용 -->
            <div class="px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
              <DetailInfo
                :apt="selectedApt"
                @close="closeDetailInfo"
                @deleteLike="deleteLikeHandler"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { mypage, likeAptList } from '@/api/user'
import { searchDetailByCode } from '@/api/apt'
import { ref, onMounted } from 'vue'
import { httpStatusCode } from '@/util/http-status'
import DetailInfo from '@/components/apt/DetailInfo.vue'

import { useAptInfoStore } from '@/stores/aptInfo'
import { useDetailInfoStore } from '@/stores/detailInfo'

const aptInfoStore = useAptInfoStore()
const detailInfoStore = useDetailInfoStore()

const user = ref({})
const aptList = ref([])
const showDetail = ref(false) // 상세정보 여는 칸!
const selectedApt = ref(null)

const getUserInfo = () => {
  mypage(
    (response) => {
      if (response.status === httpStatusCode.OK) {
        user.value = response.data
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

// 마이페이지에서 현재 유저가 찜한 목록을 불러오는 코드
const getLikeAptList = () => {
  likeAptList(
    (response) => {
      if (response.status === httpStatusCode.OK) {
        aptList.value = response.data
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

// ID를 통해 아파트의 개괄적인 정보를 얻어오는 코드
const getAptById = (aptId) => {
  return aptList.value.find((apt) => apt.aptId === aptId)
}

// DetailInfo 컴포넌트를 여는 코드
const openDetailInfo = (aptId) => {
  searchDetailByCode(
    aptId,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        selectedApt.value = response.data
        showDetail.value = true
        const aptInfo = getAptById(aptId)
        aptInfoStore.setAptInfo(aptInfo)
        detailInfoStore.setDetailInfo(response.data)
      }
    },
    (error) => {
      console.error(error)
    }
  )
}

// DetailInfo창을 끄는 코드
const closeDetailInfo = () => {
  showDetail.value = false
  selectedApt.value = null
}

// deleteLike 이벤트 캐치
const deleteLikeHandler = () => {
  // 삭제할 아파트 ID 가져오기
  const deletedAptId = aptInfoStore.aptInfo.aptId
  // 아파트 목록에서 삭제
  aptList.value = aptList.value.filter((apt) => apt.aptId !== deletedAptId)
  // 모달 창 종료
  closeDetailInfo()
}

onMounted(() => {
  getUserInfo()
  getLikeAptList()
})
</script>

<style scoped></style>
