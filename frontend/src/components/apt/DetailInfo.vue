<template>
  <div class="z-1 relative w-[35vw] flex w-full flex-col p-6 apt-info">
    <div class="mb-3 flex items-center justify-between">
      <div class="flex items-center justify-between">
        <h2
          class="block text-2xl font-bold leading-snug tracking-normal text-blue-gray-900 antialiased"
        >
          {{ aptInfo.aptName }}
        </h2>
        <!-- 찜 버튼 -->
        <button v-if="isUserLoggedIn" @click="doLike">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
            :fill="isLiked === 'on' ? 'red' : 'lightgrey'"
            class="h-6 w-6 ml-2 mt-1"
          >
            <path
              d="M12 21.35l-1.077-1.009C5.789 16.465 2 12.561 2 8.25 2 5.042 4.715 2.25 8 2.25c2.133 0 4.18 1.564 4.859 2.812C13.82 3.814 15.867 2.25 18 2.25c3.285 0 6 2.792 6 6 0 4.311-3.789 8.215-8.923 12.091L12 21.35z"
            />
          </svg>
        </button>
      </div>

      <button
        type="button"
        class="bg-white rounded-md p-2 inline-flex items-center justify-center text-gray-400 hover:text-gray-500 hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-indigo-500"
      >
        <span class="sr-only">Close menu</span>
        <!-- 종료 -->
        <svg
          @click="closeDetailInfo"
          class="h-6 w-6"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
          aria-hidden="true"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M6 18L18 6M6 6l12 12"
          />
        </svg>
      </button>
    </div>
    <p class="block font-sans text-base font-light leading-relaxed text-gray-700 antialiased">
      <strong>건축년도: </strong> {{ aptInfo.constructionYear }}년
    </p>
    <p class="block font-sans text-base font-light leading-relaxed text-gray-700 antialiased">
      <strong>도로명주소:</strong> {{ aptInfo.roadAddress }}
    </p>
    <p class="block font-sans text-base font-light leading-relaxed text-gray-700 antialiased">
      <strong>대표가:</strong> {{ moneyFormat(aptInfo.summaryAmount) }}
    </p>
    <p class="block font-sans text-base font-light leading-relaxed text-gray-700 antialiased">
      <strong>대표 면적:</strong> {{ aptInfo.summaryArea }} 제곱미터
    </p>
    <div class="mt-8 inline-flex flex-wrap items-center gap-3">
      <!-- 시세그래프 -->
      <span
        :class="{ 'btn-primary': activeComponent === 'chart' }"
        @click="showComponent('chart')"
        type="button"
        data-tooltip-target="money"
        class="cursor-pointer rounded-full border border-blue-500/5 bg-blue-500/5 p-3 text-blue-500 transition-colors hover:border-blue-500 hover:bg-blue-100"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
          class="lucide lucide-line-chart"
        >
          <path d="M3 3v18h18" />
          <path d="m19 9-5 5-4-4-3 3" />
        </svg>
      </span>
      <!-- 상세정보 -->
      <span
        :class="{ 'btn-primary': activeComponent === 'dealList' }"
        @click="showComponent('dealList')"
        type="button"
        data-tooltip-target="wifi"
        class="cursor-pointer rounded-full border border-blue-500/5 bg-blue-500/5 p-3 text-blue-500 transition-colors hover:border-blue-500 hover:bg-blue-100"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
          class="lucide lucide-file-search-2"
        >
          <path d="M15 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V7Z" />
          <path d="M14 2v4a2 2 0 0 0 2 2h4" />
          <circle cx="11.5" cy="14.5" r="2.5" />
          <path d="M13.3 16.3 15 18" />
        </svg>
      </span>
      <!-- 편의시설조회 -->
      <span
        :class="{ 'btn-primary': activeComponent === 'facilityList' }"
        @click="showComponent('facilityList')"
        type="button"
        data-tooltip-target="bedrooms"
        class="cursor-pointer rounded-full border border-blue-500/5 bg-blue-500/5 p-3 text-blue-500 transition-colors hover:border-blue-500 hover:bg-blue-100"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
          class="lucide lucide-map-pinned"
        >
          <path d="M18 8c0 4.5-6 9-6 9s-6-4.5-6-9a6 6 0 0 1 12 0" />
          <circle cx="12" cy="8" r="2" />
          <path
            d="M8.835 14H5a1 1 0 0 0-.9.7l-2 6c-.1.1-.1.2-.1.3 0 .6.4 1 1 1h18c.6 0 1-.4 1-1 0-.1 0-.2-.1-.3l-2-6a1 1 0 0 0-.9-.7h-3.835"
          />
        </svg>
      </span>
      <!-- 체크리스트 -->
      <span
        v-if="isUserLoggedIn"
        :class="{ 'btn-primary': activeComponent === 'checklistApt' }"
        @click="showComponent('checklistApt')"
        type="button"
        data-tooltip-target="bedrooms"
        class="cursor-pointer rounded-full border border-blue-500/5 bg-blue-500/5 p-3 text-blue-500 transition-colors hover:border-blue-500 hover:bg-blue-100"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          fill="currentColor"
          class="w-6 h-6"
        >
          <path
            d="M21.731 2.269a2.625 2.625 0 0 0-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 0 0 0-3.712ZM19.513 8.199l-3.712-3.712-8.4 8.4a5.25 5.25 0 0 0-1.32 2.214l-.8 2.685a.75.75 0 0 0 .933.933l2.685-.8a5.25 5.25 0 0 0 2.214-1.32l8.4-8.4Z"
          />
          <path
            d="M5.25 5.25a3 3 0 0 0-3 3v10.5a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3V13.5a.75.75 0 0 0-1.5 0v5.25a1.5 1.5 0 0 1-1.5 1.5H5.25a1.5 1.5 0 0 1-1.5-1.5V8.25a1.5 1.5 0 0 1 1.5-1.5h5.25a.75.75 0 0 0 0-1.5H5.25Z"
          />
        </svg>
      </span>
    </div>
    <div class="mx-auto">
      <!-- 하위 컴포넌트의 높이를 300px로 고정 -->
      <Chart class="overflow-hidden" v-if="activeComponent === 'chart'" />
      <DealList v-if="activeComponent === 'dealList'" />
      <FacilityList v-if="activeComponent === 'facilityList'" />
      <ChecklistApt v-if="activeComponent === 'checklistApt'" :aptId="aptInfo.aptId" />
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits, computed, watch, onMounted } from 'vue'
import { httpStatusCode } from '@/util/http-status'
import Chart from '@/components/apt/Chart.vue'
import DealList from '@/components/apt/DealList.vue'
import FacilityList from '@/components/apt/FacilityList.vue'
import ChecklistApt from '@/components/checklist/ChecklistApt.vue'
import { useAptInfoStore } from '@/stores/aptInfo'
import { useUserStore } from '@/stores/user'

// like 함수를 올바르게 import
import { like, isLike } from '@/api/apt'
import { moneyFormat } from '/public/js/unit'

const aptInfoStore = useAptInfoStore()
const { setAptInfo } = useAptInfoStore()
const { userInfo } = useUserStore()
const aptInfo = computed(() => aptInfoStore.aptInfo)

// 버튼 클릭에 따라 보여질 컴포넌트 설정
const activeComponent = ref('chart')

// 찜하기 버튼 관리하는 클래스
const isUserLoggedIn = computed(() => userInfo !== null && userInfo !== undefined)
const isLiked = ref()

onMounted(() => {
  checkLike()
})

// aptInfoStore의 aptInfo가 변경될 때마다 aptInfo 업데이트
watch(
  () => aptInfoStore.aptInfo,
  (newAptInfo) => {
    checkLike()
    setAptInfo(newAptInfo)
  }
)

// DetailInfo 종료
const emits = defineEmits(['close', 'deleteLike'])
const closeDetailInfo = () => {
  emits('close')
}
const deleteLike = () => {
  emits('deleteLike')
}

// 선택한 컴포넌트를 보여주는 함수
const showComponent = (component) => {
  activeComponent.value = component
}

// 찜하기 버튼
const doLike = async () => {
  const userId = userInfo.userId
  const aptId = aptInfo.value.aptId
  const likeState = isLiked.value

  await like(
    { userId, aptId, like: likeState },
    (response) => {
      if (response.status === httpStatusCode.CREATE) {
        console.log(aptId)
        console.log('찜 클릭')
        isLiked.value = isLiked.value === 'on' ? 'off' : 'on'

        // 찜 취소 시 deleteLike 이벤트 호출
        if (isLiked.value === 'off') {
          deleteLike()
        }
      }
    },
    (error) => {
      console.error(error)
    }
  )
}
// 상세페이지에 들어왔을 때, 이 아파트가 찜이 되어있는지 확인
const checkLike = async () => {
  const aptId = aptInfo.value.aptId
  await isLike(
    aptId,
    (response) => {
      console.log(response)
      isLiked.value = response.data === false ? 'off' : 'on'
    },
    (error) => {
      console.error(error)
    }
  )
}
</script>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
/* .detail-container {
  padding: 10px;
} */

.apt-info {
  border: 1px solid #ccc;
  background-color: white;
  margin-bottom: 20px;
}
.btn-primary {
  background-color: #4070ff; /* 파랑 */
  border-color: #4070ff;
  color: #fff;
}
</style>
