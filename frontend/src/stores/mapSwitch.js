import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useMapSwitchStore = defineStore('mapSwitch', () => {
  const mapMode = ref('map');

  // mapMode 상태를 설정하는 메소드
  function setMapMode(mode) {
    mapMode.value = mode;
  }

  return { mapMode, setMapMode };
})

