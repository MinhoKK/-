<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/views/common/header.jsp"%>

<c:if test="${userinfo.role eq 'ADMIN'}">
	<a class="btn btn-primary" href="/board/regist">글쓰기</a>
</c:if>

<input type="text" id = "name" name = "name" autocomplete="off">
<a class="btn btn-primary float-right" id = "submit_button" name = "submit_button" value="검색">검색</a>


<c:if test="${not empty sessionScope.loginInfo}">
</c:if>


<table class="table">
	<thead>
		<tr>
			<th scope="col">글번호</th>
			<th scope="col">작성자</th>
			<th scope="col">제목</th>
			<th scope="col">작성시간</th>
			<th scope="col">조회수</th>
			<th scope="col"></th>
			<th scope="col"></th>
		</tr>
	</thead>
	<tbody>
		<tr>

			<c:forEach items="${boards}" var="board">
				<tr>
					<td>${board.boardId}</td>
					<td>${board.userId}</td>
					<td><a
						href="/board/${board.boardId }">${board.subject}</a></td>
					<td>${board.postDate}</td>
					<td>${board.hitPoint}</td>
					<td><a class="btn btn-primary" href="/board/modify/${board.boardId}">수정</a></td>
					<td><a id = "delete-btn" class="btn btn-danger" href="/board/delete/${board.boardId}">삭제</a></td>
				</tr>
			</c:forEach>
		</tr>
	</tbody>
</table>


<%@include file="/WEB-INF/views/common/footer.jsp"%>
<script>
	const name = document.querySelector('#name');
	const link = document.querySelector("#submit_button");
	name.addEventListener("keyup", () => {
		// console.log(name.value);
		// 키를 누를때마다 uri가 변경, 이후 버튼을 누르면 해당 uri로 이동
		link.href = "${root}/board?action=search&name=" + name.value;
	});
	
	const deleteButtons = document.querySelectorAll('#delete-btn');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            if (!confirm("정말 삭제하시겠습니까?")) {
            	event.preventDefault();
            }
        });
    });
</script>
</div>
</body>

