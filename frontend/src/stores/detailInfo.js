import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useDetailInfoStore = defineStore('detailInfo', () => {
  const detailInfo = ref('null');

  // 거래내역 정보 저장
  function setDetailInfo(data) {
    detailInfo.value = data;
  }

  function reverseOrder (data) {
    return data.slice().reverse();
  }

  return { detailInfo, setDetailInfo, reverseOrder };
})