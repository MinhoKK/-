  
  <script setup>
  import { ref } from 'vue';
  
  const selectedPrice = ref('');
  const selectedArea = ref('');
  </script>
  
<template>
    <div>
      <label>매매가격:</label>
      <select v-model="selectedPrice">
        <option value="">전체</option>
        <option value="0-100">100만원 미만</option>
        <option value="100-200">100만원 - 200만원</option>
        <option value="200-300">200만원 - 300만원</option>
        <!-- 필요에 따라 매매가격 구간을 추가할 수 있습니다 -->
      </select>
  
      <label>면적:</label>
      <select v-model="selectedArea">
        <option value="">전체</option>
        <option value="0-50">50m² 미만</option>
        <option value="50-100">50m² - 100m²</option>
        <option value="100-150">100m² - 150m²</option>
        <!-- 필요에 따라 면적 구간을 추가할 수 있습니다 -->
      </select>
    </div>
  </template>
  
  <style scoped>
  /* 여기에 스타일링을 추가할 수 있습니다 */
  </style>
  