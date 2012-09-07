<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ taglib prefix="c_rt" uri="http://java.sun.com/jstl/core_rt"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Reviever Form</title>
<style type="text/css">

#regtable{
	background-color: #B4C6DB;
}
#regtable tr td{

}

</style>
</head>
<body style="text-align: center;">
<c_rt:if test="${sessionScope.user == null}">
	<jsp:forward page="/login.jsp" />
</c_rt:if>
<c_rt:if test='${not empty info}'>
<div style="background-color: red;">
	${info}<br/>
</div>
</c_rt:if>

<div>
	<div>Dear ${user.name} ${user.surname}, please enter the title and the full path name of your paper</div>
	<form action="<%=request.getContextPath() %>/author" enctype="multipart/form-data" method="post">
	<div>File Title: <input type="text" name="ftitle" size="30" /><br />
	File Address: <input type="file" name="file"/><br />
	<input type="submit" value="Submit" size="30"/>
	 </div>
	 </form>
</div>
<a href="<%=request.getContextPath()%>/logout">Logout</a>
</body>
</html>
