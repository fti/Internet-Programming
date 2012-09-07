<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Editor Page</title>
<style type="text/css">


</style>
</head>
<body style="text-align: center;">

<c_rt:if test="${sessionScope.user == null }">
	<jsp:forward page="/login.jsp" />
</c_rt:if>

<c_rt:if test='${not empty info}'>
<div style="background-color: red;">
	${info}<br/>
</div>
</c_rt:if>

<div>
	<table border="2">
		<tr>
			<th>Author</th><th>Article</th><th>Reviever 1</th><th>Reviever 2</th><th>Assign</th>
		</tr>
		<c_rt:forEach var="file" items="${filelist}">
		<form action="<%=request.getContextPath()%>/editor?method=assign" method="post">
		<tr>
			<td>${file.name} ${file.surname}</td>
			<td>${file.title }</td>
			<td>
			 <select name="rev1">
				<c_rt:forEach var="user" items="${users}">
					<c_rt:if test="${file.email != user.email }">
						<option value="${user.email}">${user.name} ${user.surname}</option>
					</c_rt:if>
				</c_rt:forEach>
			 </select> 
			</td>
			<td> 
			 <select name="rev2">
			   <c_rt:forEach var="user" items="${users}">
					<c_rt:if test="${file.email != user.email }">
						<option value="${user.email}">${user.name} ${user.surname}</option>
					</c_rt:if>
				</c_rt:forEach>
			 </select> 
			</td>
			<td>
			<input type="hidden" name="fid" value="${file.fid}"/>
			<input type="submit" value="Assign"/> </td>
		</tr>
		</form>
		</c_rt:forEach>
	</table>
</div>
<a href="<%=request.getContextPath()%>/logout">Logout</a>
</body>
</html>
