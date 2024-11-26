<script setup>
import { ref, onMounted, watch } from 'vue'
import { useAptInfoStore } from '@/stores/aptInfo'
import { storeToRefs } from 'pinia'
import client from '@/api/client'
import L from 'leaflet'

// Import Leaflet CSS
import 'leaflet/dist/leaflet.css'

const aptInfoStore = useAptInfoStore()

const { aptLat, aptLon } = storeToRefs(aptInfoStore)

const facilities = ref([]) // 편의시설 목록
const facilityTypes = ['병원', '세탁', '편의점', '지하철', '학교']

const activeFacilities = ref({
  병원: true,
  세탁: true,
  편의점: true,
  지하철: true,
  학교: true
})

const searchFacility = async (lat, lon) => {
  try {
    const response = await client.get(`http://localhost:8080/facility/search?lat=${lat}&lon=${lon}`)
    facilities.value = response.data
    updateMarkers() // searchFacility 완료 후 updateMarkers 호출
  } catch (error) {
    console.error('Error occurred during search:', error)
  }
}

let detailMap
let markersLayer

onMounted(() => {
  // 요청
  detailMap = L.map('detail-map', {
    center: [aptLat.value, aptLon.value],
    zoom: 16,
    zoomControl: false, // Disable zoom controls
    scrollWheelZoom: false, // Disable zoom with mouse wheel
    doubleClickZoom: false, // Disable zoom with double click
    boxZoom: false, // Disable zoom with box selection
    touchZoom: false // Disable zoom with touch
  })

  L.tileLayer(
    'http://api.vworld.kr/req/wmts/1.0.0/B79582A8-BF9A-380A-8310-E1F2CD938141/Base/{z}/{y}/{x}.png'
  ).addTo(detailMap)

  markersLayer = L.layerGroup().addTo(detailMap)
  searchFacility(aptLat.value, aptLon.value)
})

// apt가 업데이트 되었을 때 실행
watch([aptLat, aptLon], ([newLat, newLon]) => {
  searchFacility(newLat, newLon)
  resetActiveFacilities()
  detailMap.setView([newLat, newLon])
})

// facilities가 업데이트 되었을 때 실행
watch(facilities, () => {
  updateMarkers()
})

// 마커의 스타일 정의
const markerStyles = {
  병원: 'https://ssafy-whereismyhouse.s3.eu-north-1.amazonaws.com/marker/marker_hospital.png',
  세탁: 'https://ssafy-whereismyhouse.s3.eu-north-1.amazonaws.com/marker/marker_laundry.png',
  편의점: 'https://ssafy-whereismyhouse.s3.eu-north-1.amazonaws.com/marker/marker_combini.png',
  학교: 'https://ssafy-whereismyhouse.s3.eu-north-1.amazonaws.com/marker/marker_school.png',
  지하철: 'https://ssafy-whereismyhouse.s3.eu-north-1.amazonaws.com/marker/marker_subway.png'
}

// 편의시설 버튼 초기화
const resetActiveFacilities = () => {
  // activeFacilities를 초기화합니다.
  activeFacilities.value = {
    병원: true,
    세탁: true,
    편의점: true,
    지하철: true,
    학교: true
  }
}
// 지도의 편의시설 마커 업데이트
const updateMarkers = () => {
  markersLayer.clearLayers() // Clear existing markers

  const homeMarker = L.circle([aptLat.value, aptLon.value], {
    // fillColor: '#f03',
    fillOpacity: 0.2,
    radius: 100
  })
  homeMarker.bindPopup('집') // Bind popup
  markersLayer.addLayer(homeMarker) // Add home marker to the layer

  // Add facility markers
  facilities.value.forEach((facility) => {
    if (activeFacilities.value[facility.category]) {
      const marker = L.marker([facility.lat, facility.lon], {
        icon: L.icon({
          iconUrl: markerStyles[facility.category],
          iconSize: [64, 64],
          iconAnchor: [16, 32],
          popupAnchor: [0, -32]
        })
      })

      marker.bindPopup(facility.facilityName)
      markersLayer.addLayer(marker)
    }
  })
}

const toggleFacility = (type) => {
  activeFacilities.value[type] = !activeFacilities.value[type]
  updateMarkers()
}
</script>

<template>
  <div class="w-[35vw] mx-auto">
    <h2 class="text-2xl font-semibold mt-4 mb-4">주변 편의시설</h2>
    <div id="detail-map" class="h-96 mb-8 map-width"></div>
    <div class="flex justify-right">
      <button
        v-for="type in facilityTypes"
        :key="type"
        :class="{
          'bg-custom-click': activeFacilities[type],
          'bg-gray-300': !activeFacilities[type]
        }"
        @click="toggleFacility(type)"
        class="w-24 px-4 py-2 mr-4 rounded-md"
      >
        {{ type }}
      </button>
    </div>
  </div>
</template>

<style>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';
#detail-map {
  flex: 1;
}
</style>

<style scoped>
#map-container {
  display: flex;
}
#detail-map {
  flex: 1;
}

.bg-custom-click {
  background-color: #80dfff;
  color: black;
}
</style>
