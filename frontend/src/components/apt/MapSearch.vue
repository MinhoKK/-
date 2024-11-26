<template>
  <!-- search bar -->
  <div class="ml-4 mb-4" style="width: 400px">
    <div class="relative mt-6 flex justify-start">
      <!-- Adjusted width and alignment -->
      <span class="absolute inset-y-0 left-0 pl-3 flex items-center">
        <svg class="h-5 w-5 text-gray-500" viewBox="0 0 24 24" fill="none">
          <path
            d="M21 21L15 15M17 10C17 13.866 13.866 17 10 17C6.13401 17 3 13.866 3 10C3 6.13401 6.13401 3 10 3C13.866 3 17 6.13401 17 10Z"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </span>

      <input
        class="w-full border rounded-md pl-10 pr-4 py-2 focus:border-blue-500 focus:outline-none focus:shadow-outline"
        type="text"
        placeholder="지역을 검색해보세요"
        v-model="searchQuery"
        @keyup.enter="onSearch"
        :disabled="mapStatus == 'drawMap'"
      />
      <!-- search-result -->
      <div class="search-results w-full" v-if="searchDongResults.length > 0">
        <ul class="list-group">
          <li
            class="list-group-item search-item"
            v-for="result in searchDongResults"
            :key="result.legalDongFullCode"
            v-html="highlightMatch(searchQuery, result.legalAddress)"
            @click="searchApartmentByDongCode(result.legalDongFullCode)"
          ></li>
        </ul>
      </div>
    </div>
  </div>

  <ErrorComponent v-if="errorMessage !== ''" :message="errorMessage" :onClose="clearError" />
</template>

<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import client from '@/api/client'
import { useMapSwitchStore } from '@/stores/mapSwitch'
import { useTextSearchStore } from '@/stores/textSearch'
import ErrorComponent from '@/components/alert/Error.vue'

const searchQuery = ref('') // 검색어
const { setSearchDongResults, setAptData } = useTextSearchStore()
const mapSwitchStore = useMapSwitchStore()
const errorMessage = ref('')

const searchDongResults = computed(() => {
  return useTextSearchStore().searchDongResults
})
// 초기화
onMounted(() => {
  setSearchDongResults([])
  setAptData([])
})

// 현재 맵의 상태를 파악하는 코드
const mapStatus = computed(() => mapSwitchStore.mapMode)
watch(mapStatus, (newValue) => {
  if (newValue === 'drawMap') {
    clearSearch()
  }
})

watch(searchQuery, async (newValue) => {
  // 검색어가 변경될 때마다 DB에서 검색을 수행하여 결과를 가져옵니다.
  // 이 때, 검색어를 파라미터로 전달하여 서버에서 해당 검색어를 기반으로 검색을 수행합니다.
  if (newValue.length > 1) {
    // Call the searchDong function to search with the search query
    await searchDong(newValue)
  }
})

// Function to highlight matching substring and make it bold
const highlightMatch = (query, text) => {
  if (!query) return text // If query is empty, return original text
  const regex = new RegExp(`(${query.replace(/\s/g, '|')})`, 'gi')
  return text.replace(regex, '<span class="text-primary fw-bold">$1</span>')
}

// Function to search Dong based on Dong code
const searchDong = async (dongCode) => {
  try {
    // Send the Dong code to the server to search
    const response = await client.get(`http://localhost:8080/house/search-dong?query=${dongCode}`)
    // Update search results
    setSearchDongResults(response.data)
  } catch (error) {
    console.error('Error occurred during search:', error)
    // Reset search results if an error occurs
    setSearchDongResults([])
  }
}

// 법정동 코드를 기준으로 아파트 검색
const searchApartmentByDongCode = async (dongCode) => {
  try {
    // Send the Dong code to the server for further processing
    const response = await client.get(`http://localhost:8080/house/search?dongCode=${dongCode}`)
    // 아파트 저장
    if (response.data.length < 1) {
      errorMessage.value = '검색 결과가 없습니다.'
      return
    }
    setAptData(response.data)
    clearSearch()
  } catch (error) {
    console.error('Error occurred while sending Dong code to server:', error)
  }
}

// 에러 메세지 지우가
const clearError = () => {
  errorMessage.value = ''
}

// Function to clear search query and results
const clearSearch = () => {
  searchQuery.value = ''
  // searchDongResults.value = []
  setSearchDongResults([])
}
</script>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';

/* Add hover effect for list items */
.search-item:hover {
  background-color: #e9ecef; /* Change the background color on hover */
  cursor: pointer;
}
.search-results {
  max-height: 250px;
  overflow-y: auto;
  z-index: 1000;
  position: absolute; /* 상대적 위치 대신 절대적 위치를 사용합니다. */
  top: calc(100%); /* 검색 바 아래에 위치하도록 조정합니다. */
}

/* 나머지 스타일은 이전과 동일합니다. */
</style>
