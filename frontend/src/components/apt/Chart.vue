<template>
  <div class="w-[35vw] mx-auto">
    <h2 class="text-2xl font-bold mb-4 mt-4">그래프</h2>
    <div id="control-container" class="mb-4 flex space-x-2 items-center">
      <div id="period-button-container" class="flex space-x-2">
        <button
          @click="setActivePeriod('all')"
          :class="{
            'bg-green-500 text-white': activePeriod === 'all',
            'bg-transparent text-black border': activePeriod !== 'all'
          }"
          class="px-4 py-2 border rounded"
        >
          전체
        </button>
        <button
          @click="setActivePeriod('1year')"
          :class="{
            'bg-green-500 text-white': activePeriod === '1year',
            'bg-transparent text-black border': activePeriod !== '1year'
          }"
          class="px-4 py-2 border rounded"
        >
          1년
        </button>
        <button
          @click="setActivePeriod('3years')"
          :class="{
            'bg-green-500 text-white': activePeriod === '3years',
            'bg-transparent text-black border': activePeriod !== '3years'
          }"
          class="px-4 py-2 border rounded"
        >
          3년
        </button>
        <button
          @click="setActivePeriod('5years')"
          :class="{
            'bg-green-500 text-white': activePeriod === '5years',
            'bg-transparent text-black border': activePeriod !== '5years'
          }"
          class="px-4 py-2 border rounded"
        >
          5년
        </button>
      </div>
      <div id="area-select-container" class="ml-4">
        <select
          @change="handleAreaChange"
          v-model="activeArea"
          class="px-4 py-2 border rounded bg-transparent"
        >
          <option v-for="area in areas" :key="area" :value="area">{{ area }}㎡</option>
        </select>
      </div>
    </div>
    <div class="w-full overflow-x-auto">
      <canvas id="myChart" width="300px"></canvas>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import Chart from 'chart.js/auto'
import { useDetailInfoStore } from '@/stores/detailInfo'

const detailInfoStore = useDetailInfoStore()
const detailInfo = ref(detailInfoStore.detailInfo)

const activePeriod = ref('all') // 현재 활성화된 기간
const activeArea = ref(null) // 현재 활성화된 평수
let myChart = null

const areas = ref([])

// 차트 데이터 생성
const chartData = ref({
  labels: [],
  datasets: [
    {
      label: '실거래가(원)',
      data: [],
      borderColor: '#80dfff',
      tension: 0.1
    }
  ]
})

// JSON 데이터를 차트 데이터로 변환
function generateChartData() {
  chartData.value.labels = []
  chartData.value.datasets[0].data = []
  detailInfo.value.forEach((item) => {
    chartData.value.labels.push(item.dealDate)
    chartData.value.datasets[0].data.push(item.dealAmount)
  })
  // 날짜 데이터를 오름차순으로 정렬
  chartData.value.labels.sort((a, b) => new Date(a) - new Date(b))
}
// aptInfoStore의 detailInfo가 변경될 때마다 detailInfo 업데이트
watch(
  () => detailInfoStore.detailInfo,
  (newDetailInfo) => {
    detailInfo.value = newDetailInfo

    generateChartData()
    updateAreaOptions() // 평수 옵션 업데이트

    const smallestArea = Math.min(...detailInfo.value.map((item) => item.area))

    activePeriod.value = 'all'
    activeArea.value = smallestArea
    filterChartData(activePeriod.value, activeArea.value)
  }
)

// DOM이 완전히 로드된 후에 차트 생성
onMounted(() => {
  const canvas = document.getElementById('myChart')
  const ctx = canvas.getContext('2d')
  myChart = new Chart(ctx, {
    type: 'line',
    data: chartData.value,
    options: {
      // 추가된 options 객체
      scales: {
        x: {
          suggestedMax: 10 // x 축에 최대 표시할 레이블 개수
        }
      }
    }
  })
  generateChartData() // 차트 데이터 생성
  updateAreaOptions() // 평수 옵션 업데이트

  const smallestArea = Math.min(...detailInfo.value.map((item) => item.area))
  activeArea.value = smallestArea

  // 초기값 설정
  filterChartData(activePeriod.value, activeArea.value)
})

// 버튼 클릭 시 데이터 필터링 및 차트 업데이트 함수
function filterChartData(period, area) {
  activePeriod.value = period
  activeArea.value = area
  const today = new Date()
  const endDate = new Date()
  const chartLabels = []
  const chartData = []

  // 기간에 따른 데이터 필터링
  switch (period) {
    case 'all':
      detailInfo.value.forEach((item) => {
        const dealDate = item.dealDate
        if (item.area == area) {
          chartLabels.push(dealDate)
          chartData.push(item.dealAmount)
        }
      })
      break
    case '1year':
      endDate.setFullYear(today.getFullYear() - 1)
      detailInfo.value.forEach((item) => {
        const dealDate = item.dealDate
        if (new Date(dealDate) >= endDate && item.area == area) {
          chartLabels.push(dealDate)
          chartData.push(item.dealAmount)
        }
      })
      break
    case '3years':
      endDate.setFullYear(today.getFullYear() - 3)
      detailInfo.value.forEach((item) => {
        const dealDate = item.dealDate
        if (new Date(dealDate) >= endDate && item.area == area) {
          chartLabels.push(dealDate)
          chartData.push(item.dealAmount)
        }
      })
      break
    case '5years':
      endDate.setFullYear(today.getFullYear() - 5)

      detailInfo.value.forEach((item) => {
        const dealDate = item.dealDate
        if (new Date(dealDate) >= endDate && item.area == area) {
          chartLabels.push(dealDate)
          chartData.push(item.dealAmount)
        }
      })
      break
    default:
      break
  }

  // 차트 업데이트
  myChart.data.labels = chartLabels
  myChart.data.datasets[0].data = chartData
  myChart.update()
}

// 평수 옵션 업데이트 함수
function updateAreaOptions() {
  areas.value = Array.from(new Set(detailInfo.value.map((item) => item.area))) // 중복되지 않는 평수 목록 생성
  areas.value.sort((a, b) => a - b) // 평수를 오름차순으로 정렬
}

// 이벤트 핸들러 함수
function handlePeriodChange(event) {
  setActivePeriod(event.target.value)
}

function handleAreaChange(event) {
  setActiveArea(event.target.value)
}

// 활성화된 기간 설정
function setActivePeriod(period) {
  filterChartData(period, activeArea.value)
}

// 활성화된 평수 설정
function setActiveArea(area) {
  filterChartData(activePeriod.value, area)
}
</script>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
</style>
