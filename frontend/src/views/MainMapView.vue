<script setup>
import MapNav from '@/components/apt/MapNav.vue'
import Map from '@/components/apt/Map.vue'
import { ref } from 'vue'
</script>

<template>
  <main>
    <div class="map-container">
      <Map />
      <MapNav class="map-nav-overlay" />
    </div>
  </main>
</template>

<style scoped>
.map-container {
  position: relative;
}

.map-nav-overlay {
  position: absolute;
  top: 20px; /* 수정된 부분 */
  left: 10px;
  z-index: 9;
  background-color: white;
  border: 1px solid lightgray;
}
</style>
