<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Register Form</title>
<style type="text/css">

#regtable{
	background-color: #B4C6DB;
}
a.cssbutton {border:0;float:left;text-align:center;padding:0;margin:0;cursor:pointer;text-decoration: none;}
	a.cssbutton span {font:bold 12px/23px Verdana; color:#666; display:block; float: left; white-space:nowrap; height:23px; margin-left:1px; padding:0 10px 0 9px;} 

 

</style>
</head>
<body>
<center>
<div>${info }</div>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<div style="background-color: red;">
<c:forEach var="err" items="${errors}" >
	${err}<br/>
</c:forEach>
</div>
<form action="<%=request.getContextPath() %>/login" method=post>

<table  id="regtable">

  <tr><td>
      <div align="right"><strong><sup>*</sup>User Name(e-mail) :&nbsp;</strong></div>
   </td><td>
	  <input name="email" type="text" size=15 maxlength=45 />    
  </td></tr>
  <tr><td height="50" >
      <div align="right"><strong><sup>*</sup>Password :&nbsp;</strong></div>
  </td><td>
	  <input name="password" type="password" size=15  maxlength=15 /> 
  </td></tr>
 <tr><td> 
 	<div align="right">Select your user type:</div>
 </td><td>
			<input type="radio" name="usertype" value="author" checked="checked"/><font color="red">Author Page </font>|
			<input type="radio" name="usertype" value="reviever" /><font color="purple">Reviewer Page </font>|
			<input type="radio" name="usertype" value="editor" /><font color="blue">Editor Page </font>
 </td></tr>
 <tr>  	
  		<td><a class="cssbutton" href="<%=request.getContextPath() %>/register.jsp"><span>Register</span></a></td>
  		<td><div align="left"><input  type="submit" value="Login" style="width: 90px"/></div></td>
 </tr>
</table>
</form>
</center>
</body>
</html>
