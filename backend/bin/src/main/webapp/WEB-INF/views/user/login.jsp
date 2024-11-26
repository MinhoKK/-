<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/views/common/header.jsp"%>

<div class="container p-4">
	<form id="loginForm" method="post" action="" style="height: 300px;">
		<input type="hidden" name="act" value="login"> <b>ID</b> <input
			class="form-control mb-3" type="text" name="userId"> <b>PW</b>
		<input class="form-control mb-3" type="password" name="password">
		<input class="form-control mt-4 list-group-item-primary" type="submit"
			value="LOGIN">
	</form>
</div>
<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>