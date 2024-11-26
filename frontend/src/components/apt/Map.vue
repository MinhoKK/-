<template>
  <div id="map-container" class="flex bg-white dark:bg-gray-900">
    <!-- Map Section -->
    <div id="map" :style="{ height: mapHeight, width: mapWidth }"></div>

    <!-- Geolocation Button -->
    <span @click="locateUser" class="absolute top-8 right-2 z-1 p-2 bg-white text-gray-500 rounded">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="24"
        height="24"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        stroke-width="2"
        stroke-linecap="round"
        stroke-linejoin="round"
        class="lucide lucide-locate-fixed"
      >
        <line x1="2" x2="5" y1="12" y2="12" />
        <line x1="19" x2="22" y1="12" y2="12" />
        <line x1="12" x2="12" y1="2" y2="5" />
        <line x1="12" x2="12" y1="19" y2="22" />
        <circle cx="12" cy="12" r="7" />
        <circle cx="12" cy="12" r="3" />
      </svg>
    </span>

    <!-- DetailSection -->
    <div class="flex justify-center h-screen" :style="{ width: detailInfoWidth }">
      <DetailInfo v-if="showDetail" @close="closeDetailInfo" />
    </div>
    <ErrorComponent v-if="errorMessage !== ''" :message="errorMessage" :onClose="clearError" />
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import 'leaflet.markercluster/dist/MarkerCluster.css'
import 'leaflet.markercluster/dist/MarkerCluster.Default.css'
import 'leaflet.markercluster'
import DetailInfo from '@/components/apt/DetailInfo.vue'
import marker from '/public/js/marker.js'
import FreeDraw, { CREATE, NONE } from 'leaflet-freedraw'
import turf, { convex } from 'turf'
import client from '@/api/client'
import { useMapSwitchStore } from '@/stores/mapSwitch'
import { useAptInfoStore } from '@/stores/aptInfo'
import { useDetailInfoStore } from '@/stores/detailInfo'
import { useTextSearchStore } from '@/stores/textSearch'
import ErrorComponent from '@/components/alert/Error.vue'

const mapSwitchStore = useMapSwitchStore()
const aptInfoStore = useAptInfoStore()
const detailInfoStore = useDetailInfoStore()
const textSearchStore = useTextSearchStore()

const showDetail = ref(false)
const mapWidth = ref('100vw')
const mapHeight = ref('90vh')
const detailInfoWidth = ref('0')
const errorMessage = ref('')

const currLat = ref(37.5051446767402)
const currLon = ref(127.013359062646)

let map = null
let freeDraw = null
let polygonData = []
let polygonJson
let drawnItems

onMounted(() => {
  // 초기 위치 : 반포동
  map = L.map('map', { zoomControl: false }).setView([currLat.value, currLon.value], 15)
  L.tileLayer(
    'http://api.vworld.kr/req/wmts/1.0.0/B79582A8-BF9A-380A-8310-E1F2CD938141/Base/{z}/{y}/{x}.png',
    { attribution: '&copy; OpenStreetMap contributors' }
  ).addTo(map)

  freeDraw = new FreeDraw({ mode: FreeDraw.NONE, leaveModeAfterCreate: false })
  map.addLayer(freeDraw)
  drawnItems = L.featureGroup().addTo(map)

  L.control
    .zoom({
      position: 'bottomleft'
    })
    .addTo(map)
})

watch(
  () => mapSwitchStore.mapMode,
  (newValue) => {
    if (newValue === 'drawMap') {
      if (map.getZoom() < 16) {
        errorMessage.value = '지도를 충분히 확대하세요!'
        mapSwitchStore.mapMode = 'map'
        return
      }

      polygonData = []
      drawnItems.clearLayers()
      freeDraw.mode(CREATE)
      freeDraw.on('markers', (event) => {
        if (event.eventType === 'create' && event.latLngs.length > 0) {
          freeDraw.clear()
          drawnItems.clearLayers()
          let latLngs = event.latLngs[0]
          let polygon = L.polygon(
            latLngs.map((latLng) => [latLng.lat, latLng.lng]),
            { color: 'red', draggable: true }
          ).addTo(map)
          polygon.addTo(drawnItems)
          if (polygonData.length < 1) {
            polygonData = polygon.getLatLngs()[0]
            polygonJson = JSON.stringify(polygon.getLatLngs()[0])
            searchByPolygon()
          }
        }
      })
    } else if (newValue === 'map') {
      freeDraw.clear()
      freeDraw.off('markers')
      polygonData = []
      drawnItems.clearLayers()
      freeDraw.mode(NONE)
    }
  }
)

watch(
  () => textSearchStore.aptData,
  (newValue) => {
    drawnItems.clearLayers()
    const markers = newValue.map((apartment) => {
      const { lat, lon, aptName } = apartment
      const aptMarker = L.marker([lat, lon], { icon: marker(apartment) })
      aptMarker
        .bindTooltip(`${aptName}`, {
          direction: 'top',
          offset: [10, -20],
          permanent: true
        })
        .openTooltip()
      addMarkerEventListener(aptMarker, apartment)

      return aptMarker
    })
    clusterMarkers(markers)
    if (newValue.length > 0) {
      map.setView([newValue[0].lat, newValue[0].lon], map.getZoom())
    }
  }
)

const searchByPolygon = () => {
  client
    .post('http://localhost:8080/house/search-polygon', polygonJson)
    .then((response) => {
      const markers = response.data.map((point) => {
        const aptMarker = L.marker([point.lat, point.lon], { icon: marker(point) })
        aptMarker
          .bindTooltip(`${point.aptName}`, {
            direction: 'top',
            offset: [10, -20],
            permanent: true
          })
          .openTooltip()
        addMarkerEventListener(aptMarker, point)
        return aptMarker
      })
      clusterMarkers(markers, false)
    })
    .catch((error) => {
      console.error('Error:', error)
    })
}

const clusterMarkers = (markers, convex = true) => {
  const clusterGroup = L.markerClusterGroup()
  markers.forEach((marker) => {
    clusterGroup.addLayer(marker)
  })
  drawnItems.addLayer(clusterGroup)

  if (convex && markers.length >= 2) {
    const markerGeoJSON = markers.map((marker) => ({
      type: 'Feature',
      geometry: {
        type: 'Point',
        coordinates: [marker.getLatLng().lng, marker.getLatLng().lat]
      }
    }))
    const hull = turf.convex(turf.featureCollection(markerGeoJSON))
    L.geoJSON(hull).addTo(drawnItems)
  }
}

const searchDetailByCode = (aptId) => {
  return client
    .get(`http://localhost:8080/house/detail/${aptId}`)
    .then((response) => response.data)
    .catch((error) => {
      console.error('Error:', error)
      throw error
    })
}

const addMarkerEventListener = (aptMarker, point) => {
  aptMarker.on('click', async () => {
    try {
      const dealInfo = await searchDetailByCode(point.aptId)
      const currentZoom = map.getZoom()
      map.setView([point.lat, point.lon], currentZoom)
      showDetailInfo(point, dealInfo)
    } catch (error) {
      console.error('Error:', error)
    }
  })
}

const showDetailInfo = (point, dealInfoData) => {
  aptInfoStore.setAptInfo(point)
  detailInfoStore.setDetailInfo(dealInfoData)
  showDetail.value = true
  mapWidth.value = '65vw'
  detailInfoWidth.value = '35vw'
  const currentZoom = map.getZoom()
  map.setView([point.lat, point.lon], currentZoom)
}

const closeDetailInfo = () => {
  showDetail.value = false
  mapWidth.value = '100vw'
  detailInfoWidth.value = '0'
  const point = aptInfoStore.aptInfo
  map.setView([point.lat, point.lon], map.getZoom())
}

const clearError = () => {
  errorMessage.value = ''
}

// Locate user function
const locateUser = () => {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude, longitude } = position.coords
        currLat.value = latitude
        currLon.value = longitude
        map.setView([currLat.value, currLon.value], 15)
      },
      (error) => {
        errorMessage.value = 'Unable to retrieve your location'
        console.error('Geolocation error:', error)
      }
    )
  } else {
    errorMessage.value = 'Geolocation is not supported by this browser.'
  }
}
</script>

<style scoped>
@import 'tailwindcss/base';
@import 'tailwindcss/components';
@import 'tailwindcss/utilities';

#map-container {
  display: flex;
  width: 100vw;
}

#map {
  z-index: 1;
}

button {
  position: absolute;
  top: 1rem;
  right: 1rem;
  padding: 0.5rem 1rem;
  background-color: #1d4ed8;
  color: white;
  border: none;
  border-radius: 0.375rem;
  cursor: pointer;
  z-index: 1;
}

button:hover {
  background-color: #2563eb;
}
</style>

<!-- Marker Style지정 -->
<style>
.custom-marker {
  position: absolute;
  display: inline-block;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: white;
  text-align: center;

  color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  border-radius: 8px; /* 테두리를 부드럽게 만듭니다. */
}

.custom-marker > .area {
  font-size: 11px;
  flex-grow: 4;
  display: flex;
  align-items: center;
  justify-content: center;
  color: black;
  background-color: #80dfff;
  font-weight: bold;
  border-radius: 8px 8px 0 0;
  transition: transform 0.3s ease; /* 호버 시 부드러운 애니메이션을 위한 transition */
}

.custom-marker:hover > .area {
  color: white;
  font-weight: 500;
  background-color: rgb(41, 164, 206);
  transform: scale(1.1); /* 호버 시 크기를 확대 */
  transition: transform 0.3s ease; /* 호버 시 부드러운 애니메이션을 위한 transition */
}

.custom-marker > .price {
  font-size: 12px;
  flex-grow: 6;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: white;
  color: black;
  border-radius: 0 0 8px 8px;
  transition: transform 0.3s ease; /* 호버 시 부드러운 애니메이션을 위한 transition */
}

.custom-marker:hover > .price {
  transform: scale(1.1); /* 호버 시 크기를 확대 */
  transition: transform 0.3s ease; /* 호버 시 부드러운 애니메이션을 위한 transition */
}
</style>
