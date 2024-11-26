import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import 'leaflet.markercluster/dist/MarkerCluster.css'
import 'leaflet.markercluster/dist/MarkerCluster.Default.css'
import 'leaflet.markercluster'
import FreeDraw, { CREATE, NONE } from 'leaflet-freedraw'
import marker from '/public/js/marker.js'
import turf from 'turf'

const mapUtils = {
  initializeMap,
  initializeFreeDraw,
  initializeDrawnItems,
  createMarker,
  createClusterGroup,
  addClusterGroupToMap,
  addMarkerToClusterGroup,
  clusterMarkers
}

// 맵 초기화
function initializeMap(containerId, initialCoordinates, initialZoom) {
  const map = L.map(containerId).setView(initialCoordinates, initialZoom)
  L.tileLayer(
    'http://api.vworld.kr/req/wmts/1.0.0/B79582A8-BF9A-380A-8310-E1F2CD938141/Base/{z}/{y}/{x}.png',
    {
      attribution: '&copy; OpenStreetMap contributors'
    }
  ).addTo(map)
  return map
}

// FreeDraw 초기화
function initializeFreeDraw(mapInstance) {
  const freeDraw = new FreeDraw({
    mode: FreeDraw.NONE,
    leaveModeAfterCreate: false
  })
  mapInstance.addLayer(freeDraw)
  return freeDraw
}

function initializeDrawnItems()  {
    return L.featureGroup()
}

// 마커 그리는 함수 초기화
function createMarker(apartment) {
  const { lat, lon, aptName, aptId } = apartment
  const aptMarker = L.marker([lat, lon], { icon: marker(apartment) })
  aptMarker.bindTooltip(`${aptName}`, {
    direction: 'top',
    offset: [10, -20],
    permanent: true
  }).openTooltip()
  return aptMarker
}

// 클러스터 그룹 그리는 함수 초기화
function createClusterGroup() {
  return L.markerClusterGroup()
}

function addClusterGroupToMap(clusterGroup, mapInstance) {
  clusterGroup.addTo(mapInstance)
}

function addMarkerToClusterGroup(marker, clusterGroup, markerCoordinates) {
  clusterGroup.addLayer(marker)
  markerCoordinates.push([marker.getLatLng().lat, marker.getLatLng().lng])
}

// 1. 이 친구로부터 시작
function clusterMarkers(markers, drawnItems, markerCoordinates) {
  const clusterGroup = createClusterGroup()
  markers.forEach(marker => {
    addMarkerToClusterGroup(marker, clusterGroup, markerCoordinates)
  })
  addClusterGroupToMap(clusterGroup, drawnItems)

  if (markers.length >= 2) {
    const markerGeoJSON = markers.map(marker => ({
      type: 'Feature',
      geometry: {
        type: 'Point',
        coordinates: [marker.getLatLng().lng, marker.getLatLng().lat]
      }
    }))
    const hull = turf.convex(turf.featureCollection(markerGeoJSON))
    return L.geoJSON(hull)
  }
}

export default mapUtils
