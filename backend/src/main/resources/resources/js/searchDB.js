let aptBtn = document.querySelector("#aptBtn");
let sortBtn = document.querySelector("#sortBtn");



aptBtn.addEventListener("click", function() {
	let sido = document.querySelector("#sido").value;
	let gugun = document.querySelector("#gugun").value;
	let dong = document.querySelector("#dong").value;
	let year = document.querySelector("#year").value;
	let month = document.querySelector("#month").value;
	
	location.href = "/house/search?sido="+sido+"&gugun="+gugun+"&dong="+dong+"&year="+year+"&month="+month+"&ascending="+"false";
	
	

});



// 정렬 버튼 토글
sortBtn.addEventListener("click", function() {

	// 현재 URL 가져오기
	let currentUrl = window.location.href;
	
	// URL에서 쿼리스트링 가져오기
	let queryString = currentUrl.split('?')[1]; // '?' 이후의 문자열을 가져옴
	
	// URLSearchParams 객체 생성하여 쿼리스트링 파싱하기
	let params = new URLSearchParams(queryString);
	
	// 특정 파라미터 값 가져오기
	let act = params.get('act');
	let sido = params.get('sido');
	let gugun = params.get('gugun');
	let dong = params.get('dong');
	let year = params.get('year');
	let month = params.get('month');
	
	console.log("act:", act);
	console.log("sido:", sido);
	console.log("gugun:", gugun);
	console.log("dong:", dong);
	console.log("year:", year);
	console.log("month:", month);
	
	// 다음 버튼 클릭할 시 체크
	console.log(sortBtn.getAttribute("next-ascending"));
    let nextAscending = (sortBtn.getAttribute("next-ascending") === "true");
	let url = "house?act=search&sido="+sido+"&gugun="+gugun+"&dong="+dong+"&year="+year+"&month="+month;

    
    // 만약 버튼을 클릭했을때 오름차순을 한다면
    if (nextAscending === true) {
        
        location.href = url + "&ascending="+"true";
        
        sortBtn.setAttribute("next-ascending", "false"); // 속성 변경
        sortBtn.innerText = "가격 오름차순 정렬하기"; // 버튼 텍스트 변경
        
    // 내림차순 => 오름차순 정렬
    } else if(nextAscending === false) {
       	
        location.href = url + "&ascending="+"false";
        sortBtn.setAttribute("next-ascending", "true"); // 속성 변경
        sortBtn.innerText = "가격 내림차순 정렬하기"; // 버튼 텍스트 변경
    }
});