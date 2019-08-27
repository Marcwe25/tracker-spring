<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" id="main">

<title>Welcome to tracker</title><br>
</head>
<body>
<div class="container">

	<div class="topContainer">
		<h1 class="back">Welcome to tracker</h1><br><br> 
		<div class="menu1">
		<a href="${pageContext.request.contextPath}/login">login</a><br><br> 
		<a href="${pageContext.request.contextPath}/register/registerForm">register</a><br><br>
		<div class="footer">
			see admin page with statistic, using username "admin", password "admin" <br><br>
			see user example with data populated, using username "demo", password "demo"<br>
		</div>
		</div>
		<c:if test="${param.error=='wrong'}">
			<p style="color: red;">the link you entered is private, please
				login first</p>
		</c:if>
		<c:if test="${param.error=='DataIntegrity'}">
			<p style="color: red;">there are some technical issues with your account</p>
		</c:if>
		<c:if test="${param.message=='registrationSuccessfull'}">
			<p style="color: green;">you have been successfully registered, login with your username and password</p>
		</c:if>
	</div>
</div>
</body>
</html>