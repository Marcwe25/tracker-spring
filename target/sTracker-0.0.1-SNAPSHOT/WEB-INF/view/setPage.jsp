<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>	
<%@ taglib prefix="form"		uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>

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
<title>SET PAGE</title>
</head>
<body>
	<div class="container">

		<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<div class="left_col">
				<spring:message code="toggle.lang" var="toggleLang"/>
				<p><a href="${pageContext.request.contextPath}/form/setPage?${toggleLang}"><spring:message code="toggle.lang.text"/></a></p>
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
			<h1 class="back"><spring:message code="title.event" /></h1>
			<form:form modelAttribute="webTrackedObjectListCmd" action="increment" method="post">
				<form:errors path="*" ><div class="errorClass"><spring:message code="attenttion.correction"/></div></form:errors>
				<table>
				
					<c:forEach var="object" items="${webTrackedObjectListCmd.webTrackerObjects}" varStatus="status">
						<tr>
							<form:hidden path="webTrackerObjects[${status.index}].id"></form:hidden>
							<form:hidden path="webTrackerObjects[${status.index}].name"></form:hidden>
							<td>${object.name}</td>
							<td><button class="myButton" type="button" onclick="incr('${object.id}',-1)">-</button></td>
							<td><form:input id="${object.id}" class="setInput" path="webTrackerObjects[${status.index}].amount"/></td>
							<td><button class="myButton" type="button" onclick="incr('${object.id}',1)">+</button></td>
							<form:errors cssClass="errorClass" element="td" path="webTrackerObjects[${status.index}].amount"/>
						</tr>
					</c:forEach>
					
					<tr style="background-color: transparent;">
						<td><input type="submit" class="myButton myY">
							<input type="button" class="myButton myN" onclick="returnToList()" value="cancel"></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>