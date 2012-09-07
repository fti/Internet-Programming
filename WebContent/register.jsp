<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Register Form</title>
<style type="text/css">

#regtable{
	background-color: #B4C6DB;
}
#regtable tr td{

}

</style>
</head>
<body>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<center>
<div style="background-color: red;">
<c:forEach var="err" items="${errors}" >
	${err}<br/>
</c:forEach>
</div>
<form action="/InternetProgramming/register" method="post">

<table  id="regtable">
  
  <tr>
    <td > 
	  <div align="right"><strong><sup>*</sup>First Name :&nbsp;</strong></div>
	</td>
    <td >
	  <input name="name" type="text" size=15 maxlength=45 />
    </td>
  </tr>
  <tr>
    <td height="50" > 
	  <div align="right"><strong><sup>*</sup>Last Name :&nbsp;</strong></div>
	</td>
    <td >
	  <input name="surname" type="text" size=15 maxlength=45 />
    </td>
  </tr>
  <tr>
    <td >
      <div align="right"><strong><sup>*</sup>User Name(e-mail) :&nbsp;</strong></div>
    </td>
    <td >
	  <input name="email" type="text" id="userName"  size=15 maxlength=45 />    
	</td>
  </tr>
  <tr>
    <td height="50" >
      <div align="right"><strong><sup>*</sup>Password :&nbsp;</strong></div>
    </td>
    <td >
	  <input name="password" type="password" size=15  maxlength=15 /> 
	</td>
  </tr>
  <tr>
  <td> <div align="right">Do you want to be a reviewer?</div></td>
 	 <td>
			<input type="radio" name="usertype" value="reviever" /><font color="red">Yes </font>|
			<input type="radio" name="usertype" value="author" checked="checked" /><font color="blue">No </font>
	 </td>
  </tr>
  <tr>  	
  		<td align="center" colspan="2"><input type="submit" value="Register" /></td>
  </tr>
</table>
</form>
</center>
</body>
</html>
