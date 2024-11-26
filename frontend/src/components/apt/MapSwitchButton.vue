<script setup>
// mapSwitch store 사용
import { ref, computed } from 'vue'
import { useMapSwitchStore } from '@/stores/mapSwitch'

// mapSwitch store 인스턴스 생성
const mapSwitchStore = useMapSwitchStore()
const buttonTitle = ref('그리기')
// mapMode 변수 정의
let mapMode = computed(() => mapSwitchStore.mapMode)

// MapNav 컴포넌트에서 버튼 클릭 시 호출될 함수
const toggleMapMode = () => {
  const newMode = mapMode.value === 'map' ? 'drawMap' : 'map'
  const newButtonTitle = newMode === 'map' ? '그리기' : '그리기 취소'

  mapSwitchStore.setMapMode(newMode)
  buttonTitle.value = newButtonTitle
  mapMode.value = newMode
}
</script>

<template>
  <!-- 이미지 버튼으로 변경 -->
  <img
    v-if="mapMode === 'map'"
    src="https://ssafy-whereismyhouse.s3.eu-north-1.amazonaws.com/button/button_draw.png"
    alt="그리기 버튼"
    class="image-button"
    @click="toggleMapMode"
  />
  <img
    v-else
    src="https://ssafy-whereismyhouse.s3.eu-north-1.amazonaws.com/button/button_draw_cancel.png"
    alt="그리기 취소 버튼"
    class="image-button"
    @click="toggleMapMode"
  />
</template>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
/* 이미지 버튼 스타일 */
.image-button {
  width: 40px; /* 이미지의 너비 */
  height: 40px; /* 이미지의 높이 */
  cursor: pointer;
  margin: auto;
  margin-right: 10px;
  border-radius: 4px;
  padding: 3px;
}
</style>
