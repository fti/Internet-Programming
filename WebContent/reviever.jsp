<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt" %>
<title>Reviever Page</title>
<style type="text/css">

#regtable{
	background-color: #B4C6DB;
}
#regtable tr td{

}

</style>
</head>
<body style="text-align: center;">
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>
<c_rt:if test='${not empty info}'>
<div style="background-color: red;">
	${info}<br/>
</div>
</c_rt:if>

<div >
	<div>Hello ${user.name} ${user.surname}, Articles are below. you can reviev and email to author.</div>
	<table align="center" border="2">
		<tr><th width="auto">Author</th><th>Article Title</th><th>Author Email</th><th>Download</th></tr>
			<c_rt:forEach var="pageList" items="${pageList}">
			<c_rt:set var="link" value="${pageList.fname }" scope="request"/>
		<tr>
			<td>${pageList.name } ${pageList.surname }</td><td>${pageList.title }</td><td>${pageList.email }</td>
			<td><a href="${pageContext.request.contextPath }/download?link=${pageList.fname }" target="_blank" >Download</a></td> 
		</tr>
			</c_rt:forEach>
	</table>
</div>

<a href="<%=request.getContextPath()%>/logout">Logout</a>
</body>
</html>
