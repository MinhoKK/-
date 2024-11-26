<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<h3>내 정보</h3>
<table border="1">
  <tr>
    <td>이름 :</td>
    <td>${userinfo.name}</td>
  </tr>
  <tr>
    <td>아이디 :</td>
    <td>${userinfo.userId}</td>
  </tr>
  <tr>
    <td>주소 :</td>
    <td>${userinfo.address}</td>
  </tr>
</table>

<a href="/user/modify">[정보 수정하기]</a>
<br>
<h3>찜 목록</h3>
<ul id="list-group" class="list-group"></ul>

<script>
  // 마이페이지에 들어가면 fetch로 찜 목록을 불러옴
  window.addEventListener("load", () => {
    fetch("/house/like/get")
      .then((response) => response.json())
      .then((data) => {
        const listGroup = document.getElementById("list-group");
        data.forEach((apt) => {
          const aptCode = apt.aptCode;
          const aptName = apt.apartmentName;

          const newListItem = document.createElement("li");
          newListItem.id = aptCode;
          newListItem.className = "list-group-item d-flex justify-content-between";
       	
          // li 안에 a 요소를 생성합니다.
          const anchor = document.createElement("a");
          anchor.href = "/house/detail?aptCode=" + aptCode;
          anchor.textContent = aptName; // 앵커의 텍스트 설정
          
          const listItemText = document.createTextNode(aptName);
          
          newListItem.appendChild(anchor);


          const deleteButton = document.createElement("button");
          deleteButton.id = `btn${aptCode}`;
          deleteButton.type = "button";
          deleteButton.className = "btn btn-danger";
          deleteButton.textContent = "찜 삭제";

          newListItem.appendChild(deleteButton);

          listGroup.appendChild(newListItem);

          // 삭제 버튼에 이벤트 리스너 추가
          deleteButton.addEventListener("click", () => {
            deleteA(aptCode);
          });
        });
      })
      .catch((error) => {
        console.error("Error fetching like list:", error);
      });
  });

  // 삭제 버튼을 누르면 POST로 삭제 요청을 보냄
  function deleteA(aptCode) {
    fetch("/house/like/delete/" + aptCode, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ aptCode }),
    })
      .then((response) => {
        if (response.ok) {
          console.log(`Successfully deleted ${aptCode}`);
          const item = document.getElementById(aptCode);
          item.remove(); // 삭제 성공 시 목록에서 해당 항목 제거
        } else {
          console.error("Failed to delete like item");
        }
      })
      .catch((error) => {
        console.error("Error deleting like item:", error);
      });
  }
</script>
