<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<spring:message code="lang.current" var="langCurrent" />
<spring:message code="html.tag"/>

<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<meta name="viewport" content="width=device-width, initial-scale=1">
<spring:theme code="stylesheet" var="themeName" />
<spring:theme code="nextTheme" var="nextTheme" />
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" id="main">
<link href="<c:url value="/resources/css/setPage.css" />" rel="stylesheet">
<script type="text/javascript" src="<c:url value="/resources/js/list.js" />"></script>
<script type="text/javascript"> contextPath = '<spring:url value="/"></spring:url>' </script>
<title>HISTORY</title>
</head>
<body>
	<div class="container">

		<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<div class="left_col">
				<p>
				<spring:message code="toggle.lang" var="toggleLang"/>
				<spring:message code="toggle.lang.text" var="toggleLangText"/>
				<p><a href="${pageContext.request.contextPath}/view/${pageInfo.type}?${toggleLang}">${toggleLangText}</a> <a href="currentList"><spring:message code="link.home"/></a></p>
			</div>
				<div class="right_col">
					<spring:message code="link.logout" var="linkLogout" />
					<spring:message code="link.welcome" var="linkWelcome" />
					<p class="welcome">${linkWelcome}
						<security:authentication property="principal.username" />
					</p>
				<input class="logout" type="submit" value="${linkLogout}" />
			</div>
		</form:form>
		<div class="varSize">
			<h1 class="back"><spring:message code="title.history"/></h1>
			
			<table width="100%">
				<thead >
				<tr style="background-color: #e4e4e4;">
				<td>
				<a href="before"><spring:message code="link.before"/></a>
				</td>
				<td style="background-color: #e4e4e4;padding-top:4px">
				<form:form action="for" modelAttribute="pageInfo"
				method="POST">
				<form:input class="largeInput" type="date" path="from" />
				<spring:message code="link.set" var="lset"/>
				<input class="setButton" type="submit" value="${lset}">
			</form:form>
				</td>
				<td>
				<a href="after"><spring:message code="link.after"/></a>
				</td>
				</tr>
				<tr style="background-color: #778a97;">
				<td><spring:message code="clomuns.time"/></td>
				<td><spring:message code="clomuns.name"/></td>
				<td><spring:message code="clomuns.complete"/></td>
				</tr>
				</thead>
					<c:forEach var="webHistory" items="${history}" varStatus="status">
						<tr>
							<td>${webHistory.formatedDatetime}</td>
							<td>${webHistory.name}</td>
							<td>${webHistory.amount}</td>
						</tr>
					</c:forEach>
				</table>
				<br>

		</div>
	</div>
</body>
</html>