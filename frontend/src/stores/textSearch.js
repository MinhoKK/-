import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useTextSearchStore = defineStore('textSearch', () => {
  const aptData = ref([]);
  const searchDongResults = ref([]); // 검색 결과

  // 아파트 데이터 넣는 함수
  function setAptData(data) {
    aptData.value = data;
  }

  function setSearchDongResults(data) {
    searchDongResults.value = data;
  }

  return { aptData, searchDongResults, setAptData, setSearchDongResults };
})

