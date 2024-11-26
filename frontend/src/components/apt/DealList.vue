<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useDetailInfoStore } from '@/stores/detailInfo'
import { moneyFormat, squareMeterToSquare } from '/public/js/unit'

const detailInfoStore = useDetailInfoStore()
const detailInfo = ref(detailInfoStore.reverseOrder(detailInfoStore.detailInfo))

// 페이지당 항목 수
const itemsPerPage = 8

// 현재 페이지
const currentPage = ref(1)

// 토글 상태 (true: 평방미터, false: 평)
const isSquareMeter = ref(true)

// 현재 페이지의 데이터
const paginatedData = computed(() => {
  const startIndex = (currentPage.value - 1) * itemsPerPage
  const endIndex = startIndex + itemsPerPage
  return detailInfo.value.slice(startIndex, endIndex)
})

// 평방미터와 평을 토글하는 함수
function toggleUnits() {
  isSquareMeter.value = !isSquareMeter.value
}

// 전체 페이지 수
const totalPages = computed(() => Math.ceil(detailInfo.value.length / itemsPerPage))

// 다른 marker을 클릭하면 값 변경되도록
watch(
  () => detailInfoStore.detailInfo,
  (newDetailInfo) => {
    detailInfo.value = detailInfoStore.reverseOrder(newDetailInfo)
    currentPage.value = 1
  }
)
// 페이지를 변경하는 함수
function goToPage(page) {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page
  }
}
</script>
<template>
  <div class="w-[35vw] mx-auto">
    <h2
      class="block text-2xl font-bold leading-snug tracking-normal text-blue-gray-900 antialiased inline-block mt-4 mb-4"
    >
      상세거래
    </h2>
    <span>
      <button @click="toggleUnits" class="toggle-btn inline-block ml-4">
        <span class="underline">{{ isSquareMeter ? '평으로 변환' : '㎡로 변환' }}</span>
      </button>
    </span>
    <table class="table">
      <thead>
        <tr>
          <th>날짜</th>
          <th>거래금액</th>
          <th>면적</th>
          <th>층수</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in paginatedData" :key="item.dealDate">
          <td>{{ item.dealDate }}</td>
          <td>{{ moneyFormat(item.dealAmount) }}</td>
          <td>{{ isSquareMeter ? squareMeterToSquare(item.area) + '평' : item.area + '㎡' }}</td>
          <td>{{ item.floor }}층</td>
        </tr>
      </tbody>
    </table>

    <!-- 페이지네이션 컨트롤 -->
    <div class="pagination justify-center">
      <button
        @click="goToPage(currentPage - 1)"
        :disabled="currentPage === 1"
        class="pagination-btn"
      >
        이전
      </button>
      <span class="pagination-info">{{ currentPage }} / {{ totalPages }}</span>
      <button
        @click="goToPage(currentPage + 1)"
        :disabled="currentPage === totalPages"
        class="pagination-btn"
      >
        다음
      </button>
    </div>
  </div>
</template>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: 10px;
  border: 1px solid #ddd;
}

.pagination {
  margin-top: 20px;
  text-align: center;
}

.pagination-btn {
  margin: 0 5px;
  padding: 8px 12px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.pagination-btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.pagination-info {
  font-size: 16px;
}
</style>
