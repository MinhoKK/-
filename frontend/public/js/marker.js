import L from 'leaflet'

// 평방미터를 평수로 변환하는 함수
const squareMeterToSquare = (squareMeter) => {
  const square = squareMeter / 3.3058 // 1평 = 3.3058m^2
  return square.toFixed(2) // 소수점 한 자리까지 반올림하여 반환
}
// 돈을 억원 또는 만원 단위로 변경하는 함수
const moneyFormat = (amount) => {
  // 1억 이상
  if (amount >= 100000000) {
    return `${(amount / 100000000).toFixed(1)}억`
  } else {
    return `${Math.floor(amount / 10000)}만` // 만원 단위로 변환하여 반환
  }
}

// createCustomIcon 함수 내에서 사용할 HTML을 생성하는 함수
const createHTMLContent = (data) => {
  const square = squareMeterToSquare(data.summaryArea) // 평수로 변환
  const amount = moneyFormat(data.summaryAmount) // 돈 형식 변경

  return `
    <div class="area">${square}평</div>
    <div class="price">${amount}</div>
  `
}

// createCustomIcon 함수 수정
const createCustomIcon = (data) => {
  const customIcon = L.divIcon({
    className: 'custom-marker',
    html: createHTMLContent(data), // 변경된 HTML을 생성하는 함수 사용
    iconSize: [60, 40],
    iconAnchor: [20, 15]
  })

  return customIcon
}

export default createCustomIcon
