<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" id="main">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Administrators's page</title>
<style type="text/css">
</style>
<script type="text/javascript">
	function makeQuery(query, username) {
		contextPath = '<spring:url value="/"></spring:url>';
		url = contextPath + query + '?username=' + username;
		window.location.href = url;
	}
</script>
</head>
<body>
	<div class="container">
		<form:form action="${pageContext. request. contextPath}/logout" method="POST">
		<div class="left_col">
			<p>welcome administrator</p>
		</div>
		<div class="right_col">
			<input class="logout" type="submit" value="Logout" />
		</div>
		</form:form>
			
		<div class="topContainer">
			<div class="back">
				<h2>TRACKER ADMINISTRATION</h2>
			</div>
			<div class="menu1">
				<a href="<c:url value="/adminPage/generalStat"/>">general</a> | 
				<a href="<c:url value="/adminPage/exceptionStat"/>">exception</a> |
				<a href="<c:url value="/adminPage/unused"/>">code usage</a> | 
				<a href="<c:url value="/adminPage/raw"/>">raw</a> | 
				<a href="<c:url value="/adminPage/peekDays"/>">peek days</a> | 
				<a href="<c:url value="/adminPage/peekHours"/>">peek hours</a> | 
				<a href="<c:url value="/adminPage/users"/>">users</a>
			</div>

			<br> number of users: ${fn:length(webUsers)}

			<table>
				<thead>
					<tr>
						<th>username</th>
						<th>status</th>
						<th>authorities</th>
						<th>disable</th>
						<security:authorize access="hasRole('ADMIN_WRITE')">
						<th>delete</th>
						</security:authorize>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${webUsers}" var="theUser">
						<tr>
							<td>${theUser.username}</td>
							<td><c:if test="${theUser.enabled}">enable</c:if> <c:if
									test="${!theUser.enabled}">disable</c:if></td>
							<td><c:forEach items="${theUser.authorities}"
									var="authority">
									<li>${authority}</li>
								</c:forEach></td>
								<td class="niceButton" onclick="makeQuery('adminPage/disable','${theUser.username}')">
									<c:if test="${theUser.enabled}">disable</c:if>
									<c:if test="${!theUser.enabled}">enable</c:if>
								</td>
							<security:authorize access="hasRole('ADMIN_WRITE')">
							<td class="niceButton" onclick="makeQuery('adminPage/delete','${theUser.username}')">
								delete</td>
							</security:authorize>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>