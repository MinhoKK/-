import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useAptInfoStore = defineStore('aptInfo', () => {
  const aptInfo = ref('null');
  const aptLat = ref(0.0);
  const aptLon = ref(0.0);

  // 아파트 정보 저장
  function setAptInfo(data) {
    aptInfo.value = data;
    aptLat.value = data.lat;
    aptLon.value = data.lon;
  }

  return { aptInfo, aptLat, aptLon, setAptInfo };
})