<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<%@include file="/WEB-INF/views/common/header.jsp"%>
<div>
	<h3>상세 공지</h3>
	<p><strong>작성자</strong>: ${writer}</p>
	<p><strong>등록일</strong>: ${board.postDate}</p>
	<p><strong>조회수</strong>: ${board.hitPoint}</p>
	<input type="hidden" name="action" value="list"> <br> <b>공지</b>
	<input class="form-control" type="text" name="subject" readonly
		value="${board.subject}"> <br> <b>내용</b>
	<textarea class="form-control" name="content" readonly
		style="height: 300px;">${board.content}</textarea>
	<br>
	<button class="form-control list-group-item-warning mt-3" onclick="window.location.href = '/board/list'">뒤로 가기</button>
</div>


</body>
</html>