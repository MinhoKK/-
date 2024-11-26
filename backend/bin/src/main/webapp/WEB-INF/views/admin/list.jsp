<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/views/common/header.jsp"%>

<div class="col-md-7">
	<form class="d-flex" id="form-search" method="get" action="/admin/userList">
		<input type="hidden" name="pgno" value="1"/>
			<select
				name="key"
				id="key"
				class="form-select form-select-sm me-1 w-50"
				aria-label="검색조건" >
                  <option selected>검색조건</option>
                  <option value="address">주소</option>
                  <option value="role">역할</option>
			</select>
			<div class="input-group input-group-sm">
                  <input type="text" name="word" id="word" class="form-control" placeholder="검색어..." />
                  <input type="submit" value="수정완료">
            </div>
	</form>
</div>

<a class="btn btn-primary mt-3" href="/admin/regist/user">회원등록</a>

<table class="table">
	<thead>
		<tr>
			<th scope="col">아이디</th>
			<th scope="col">비밀번호</th>
			<th scope="col">이름</th>
			<th scope="col">주소</th>
			<th scope="col">역할</th>
			<th scope="col"></th>
			<th scope="col"></th>
		</tr>
	</thead>
	<tbody>
		<tr>

			<c:forEach items="${userList}" var="user">
				<tr>
					<td>${user.userId}</td>
					<td>${user.password}</td>
					<td>${user.name}</td>
					<td>${user.address}</td>
					<td>${user.role}</td>
					<td><a class="btn btn-primary" href="/admin/update/user/${user.userId}">수정</a></td>
					<td><a id = "delete-btn" class="btn btn-danger" href="/admin/delete/user/${user.userId}">삭제</a></td>
				</tr>
			</c:forEach>
		</tr>
	</tbody>
</table>


<%@include file="/WEB-INF/views/common/footer.jsp"%>

<script>	
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

