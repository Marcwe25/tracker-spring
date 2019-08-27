<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<spring:message code="lang.current" var="langCurrent" />
<spring:message code="html.tag"/>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<!-- files for jqplot -->
<script type="text/javascript" src="<c:url value='/resources/libs/jqplot/jquery.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/jqplot/jquery.jqplot.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/libs/jqplot/plugins/jqplot.dateAxisRenderer.js' />"></script>
<link href="<c:url value='/resources/libs/jqplot/jquery.jqplot.min.css'/>" rel="stylesheet" type="text/css" />

<link href="<c:url value="/resources/css/chart.css" />" rel="stylesheet" id="main">


<spring:theme code="stylesheet" var="themeName" />
<spring:theme code="nextTheme" var="nextTheme" />
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" id="main">
<script type="text/javascript" src="<c:url value="/resources/js/list.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/plotChart.js" />"></script>
<script type="text/javascript"> contextPath = '<spring:url value="/"></spring:url>' </script>
<script>
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	 
	$(document).ajaxSend(function(e, xhr, options) {
	    xhr.setRequestHeader(header, token);
	});
	var fromDate = new Date('${pageInfo.from}');
</script>
<title>HISTORY</title>
</head>
<body>
	<div class="container">

		<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<div class="left_col">
				<p>
					<spring:message code="toggle.lang" var="toggleLang"/>
					<spring:message code="toggle.lang.text" var="toggleLangText"/>
				<p><a href="${pageContext.request.contextPath}/chart/${pageInfo.type}?${toggleLang}">${toggleLangText}</a> <a href="${pageContext.request.contextPath}/home"><spring:message code="link.home"/></a></p>
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
			<h1 class="back"><spring:message code="title.chart"/></h1>
			<table>
			<tr>
			<td></td>
			<td>
			<form:form action="filterDate" modelAttribute="userPageProperties" method="POST">
				<p><spring:message code="clomuns.from"/> <form:input type="date" path="from" id="startDate"/> <spring:message code="clomuns.to"/>  <form:input type="date" path="to" id=""/></p>
				
				</form:form></td>
			</tr>
			<tr>
			<td>
			  <c:forEach var="trackObject" items="${listObject.webTrackerObjects}">
                <label><input type="checkbox" name="trObject" value="${trackObject.id}"/>${trackObject.name} ${trackObject.value}</label><br>
                </c:forEach>
                <div class="setButton" id="myApply"><spring:message code="link.set"/></div>
			</td>
			<td>
			 <div id="chart"></div>
			</td>
			</tr>
			</table>

        <div id="chartError">Failed to contact server</div>
       
		</div>
	</div>
</body>
</html>