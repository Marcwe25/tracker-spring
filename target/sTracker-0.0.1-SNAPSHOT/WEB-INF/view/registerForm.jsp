<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Registration Form</title>
<link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet" type="text/css" id="main">

</head>
<body>
	<div class="container">
	<div class="smallerContainer">
	
	<div class="back topContainer">
	<h1>registration form</h1>
</div>
	<form:form action="${pageContext.request.contextPath}/register/applyRegistration" modelAttribute="newUser"
		method="POST">
		<table>
			<tr>
				<td>username</td>
				<td><form:input path="username" /></td>
				<td><form:errors path="username" cssClass="errorClass" style="background-color: #e4e4e4;"/></td>
			</tr>
			<tr>
				<td>password</td>
				<td><form:password path="password" /></td>
				<td><form:errors path="password" cssClass="errorClass" style="background-color: #e4e4e4;"/></td>
			</tr>
			<tr>
				<td>confirm password</td>
				<td><form:password path="confirmpassword" /></td>
				<td><form:errors path="confirmpassword" cssClass="errorClass" style="background-color: #e4e4e4;"/></td>
			</tr>
		</table>
		<br>
		<input type="submit" class="myButton myY">
		<input type="button" class="myButton myN" value="cancel" onclick="window.location='${pageContext.request.contextPath}/home'">
	</form:form>
	</div>
	</div>
</body>
</html>