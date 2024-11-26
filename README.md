# 🏠 Check-In Home - 체크리스트 작성부터 인테리어 추천까지

## 🗒️ 프로젝트 요약

- 주택 검색, 매물 검색, 체크리스트를 활용한 주택 확인, 주택 구매 후 인테리어 추천까지 전 과정을 함께하는 동반자
- 삼성 청년 SW 아카데미(SSAFY) 11기 1학기 관통 프로젝트
- 약 400만개의 아파트 실거래가 데이터, 약 10만개의 편의시설 데이터를 처음부터 직접 수집하여 프로젝트 진행

<br>

## ✔️ 프로젝트 목표


📌 대규모의 데이터 및 다양한 데이터를 다루며 실제 웹서비스에 적용해 보기

📌 Spring Boot와 Vue를 활용한 웹 프로젝트 구현하기

📌 사용자 경험을 고려하여 서비스를 편리하게 이용할 수 있는 다양한 기능 구현하기

<br>

## ✔️ 프로젝트 일정

2024-05-16 ~ 2024-05-23

<br>

## ✔️ 팀원

- 11기 광주캠퍼스 Java 전공반 고민호
- 11기 광주캠퍼스 Java 전공반 박선홍

<br>

## 🛠️ Skills

<div>
<h3>Language</h3>
<img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white">
<img alt="Python" src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white">
</div>


<div>
<h3>Web Service</h3>
<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img alt="MyBatis" src="https://img.shields.io/badge/MyBatis-BF2634?style=for-the-badge&logo=java&logoColor=white">
<img alt="Vue.js" src="https://img.shields.io/badge/Vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white">
<img alt="Tailwind CSS" src="https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white">
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
<img alt="Leaflet" src="https://img.shields.io/badge/Leaflet.js-199900?style=for-the-badge&logo=leaflet&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
</div>

<div>
<h3>Chatbot</h3>
<img alt="Flask" src="https://img.shields.io/badge/Flask-000000?style=for-the-badge&logo=flask&logoColor=white">
<img alt="Hugging Face" src="https://img.shields.io/badge/Hugging_Face-3F51B5?style=for-the-badge&logo=huggingface&logoColor=white">
<img alt="OpenAI" src="https://img.shields.io/badge/OpenAI-00FFD1?style=for-the-badge&logo=openai&logoColor=white">
<img alt="LangChain" src="https://img.shields.io/badge/LangChain-3b5998?style=for-the-badge&logo=&logoColor=white">
<img alt="MongoDB" src="https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white">
</div>

<div>
<h3>Data Collection</h3>
<img alt="Selenium" src="https://img.shields.io/badge/Selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white">
<img alt="Beautiful Soup" src="https://img.shields.io/badge/Beautiful_Soup-4E148C?style=for-the-badge&logo=beautifulsoup&logoColor=white">
</div>



<br>

## 📋 Diagram

### 1.1 Flow Chart
![flowchart drawio](https://github.com/user-attachments/assets/b161c68a-7c4c-44cf-8595-9c1102ccfd76)


### 1.2. ERD
![체크인홈ERD](https://github.com/user-attachments/assets/c430726a-eb34-40cc-8d23-b57f0db7316b)

### 1.3 Architecture
![아키테겇](https://github.com/user-attachments/assets/e7e9a53f-a58a-4305-ad6e-4cf329bed89d)

## 🖥️ Service

### 1) 다양한 검색 기능
- 행정동 기반 자동완성 검색 기능을 통해 사용자가 편리하게 특정 행정동에 어떤 아파트가 있는지 검색 가능하게 함
  - Full-Text Index를 활용해 검색 성능 향상
- 드로잉 기반 검색기능을 통해 사용자가 검색하고 싶은 영역 내 아파트를 검색할 수 있도록 함
  - 네이버 맵, 카카오 맵 API를 활용하지 않고 leaflet.js 오픈소스 지도 라이브러리를 활용하여 구현
  - 추가 지도 skin은 VWorld를 활용함

### 2) 필요한 정보만 담은 상세페이지
- 시세 그래프(기간, 면적 단위로 검색 상세화)
- 거래내역 조회
- 근처 편의시설 조회
   - 종류 : 병원, 편의점, 지하철, 학교, 세탁소(약 10만여개의 데이터)
   - 사용자는 필요한 편의시설만 취사선택하여 조회
- 찜 기능
- 체크리스트 등록

### 3) 체크리스트
- 구매자는 주택을 고를 때 자신만의 기준을 담은 체크리스트를 가지고 집을 탐색
- 따라서 체크리스트를 활용해서 집을 둘러볼 수 있고 즉각적으로 기록할 수 있도록 웹페이지에 체크리스트 구현
- 구매자는 자신만의 체크리스트 템플릿을 꾸미고 저장할 수 있음
- 체크리스트를 작성한 집들을 따로 한 번에 볼 수 있도록 목록 페이지도 만듦

### 4) 매물 게시판
- 판매자가 아파트 매물을 올릴 수 있는 매물 게시판 제작
- 댓글 기능을 통해 직접 소통할 수 있음
- 기본적인 CRUD구현

### 5) 인테리어 추천 챗봇 기능
- 사용자의 요구사항에 가장 알맞는 인테리어를 추천해주는 챗봇 서비스
- RAG기반 LLM을 활용한 챗봇
- 사용 임베딩 모델 : [klue-sroberta-base-continue-learning-by-mnr](https://huggingface.co/bespin-global/klue-sroberta-base-continue-learning-by-mnr)
- 별도의 챗봇 서버를 구축하여 클라이언트와 직접 통신하도록 함
- 약 2700여개의 집꾸미기 컨텐츠를 크롤링하여 구현

### 6) 회원가입, 로그인
- 로그인은 JWT인증 방식을 통해 구현


## ▶️ Video
https://www.youtube.com/watch?v=hl7in7zDqX4&t=193s
