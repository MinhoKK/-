<script setup>
import MapSearch from '@/components/apt/MapSearch.vue'
import MapSwitchButton from '@/components/apt/MapSwitchButton.vue'
</script>

<template>
  <div class="z-1 navbar-map bg-transparent border-0">
    <MapSearch class="search" />
    <MapSwitchButton class="ml-4 bg-stone-50" />
  </div>
</template>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';

.navbar-map {
  display: flex;

  /* background-color: lightblue; */
}
/* .search {
  max-width: 500px;
} */
</style>
