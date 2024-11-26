
import { localAxios, localNoAuthAxios } from '@/util/http-common'

const local = localAxios()
const localNoAuth = localNoAuthAxios();

// 찜하기
function like(likeInfo, success, fail) {
    local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
    local.post(`/like/apt`, JSON.stringify(likeInfo)).then(success).catch(fail)
}

// 찜 되었나 체크
function isLike(aptId, success, fail) {
    local.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken')
    local.get('/like/check', {
      params: {
        aptId: aptId
      }
    }).then(success).catch(fail);
  }

function searchByPolygon(polygonJson, success, fail) {
    localNoAuth.get(`/house/search-polygon`, polygonJson).then(success).catch(fail)
}

function searchDetailByCode(aptId, success, fail) {
    localNoAuth.get(`house/detail/${aptId}`).then(success).catch(fail)
}


// polygon 데이터를 통해서 주택 데이터 얻기 위한 함수를 async/await 형식으로 변경합니다.
// const searchByPolygon = async () => {
//     try {
//       const response = await axios.post('http://localhost:8080/house/search-polygon', polygonJson)
//       response.data.forEach(point => {
//         const aptMarker = L.marker([point.lat, point.lon], { icon: marker(point) }).addTo(drawnItems)
//         aptMarker
//           .bindTooltip(`${point.aptName}`, {
//             direction: 'top',
//             offset: [10, -20],
//             permanent: true
//           })
//           .openTooltip()
//         // Marker에 클릭 이벤트를 부여하기 위해
//         addMarkerEventListener(aptMarker, point)
//       })
//     } catch (error) {
//       console.error('Error:', error)
//     }
//   }

export { like, isLike, searchByPolygon, searchDetailByCode }
