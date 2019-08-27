<%@ page import="org.apache.taglibs.standard.tag.common.xml.ForEachTag"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" 		uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" 	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn"			uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<spring:message code="lang.current" var="langCurrent"/>
<spring:message code="html.tag"/>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>MY LIST</title>
		<spring:theme code="stylesheet" var="themeName" />
		<spring:theme code="nextTheme" var="nextTheme" />
		<spring:message code="css.menuCss" var="menuCss"/>
		<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" id="main">
		<link href="<c:url value="/resources/css/${menuCss}"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/${themeName}"/>" rel="stylesheet">
		<script type="text/javascript" src="<c:url value="/resources/js/list.js" />"></script>
		
		<script type="text/javascript">		
			contextPath = '<spring:url value="/"></spring:url>'
		</script>
	</head>
<body>
	<div class="container">
	<div>
		<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<div class="left_col">
			<spring:message code="toggle.lang" var="toggleLang"/>
				<p><a href="${pageContext.request.contextPath}/view/currentList?${toggleLang}"><spring:message code="toggle.lang.text"/></a></p>
			</div>		
			<div class="right_col">
			<spring:message code="link.logout" var="linkLogout"/>
			<spring:message code="link.welcome" var="linkWelcome"/>
				<p class="welcome">${linkWelcome} <security:authentication property="principal.username" /></p>
				<input class="logout" type="submit" value="${linkLogout}"/>
			</div>
		</form:form>
		<br>
		<div class="topContainer">
		
			<div class="back">
			<h2><spring:message code="title.tracker"/></h2>
			</div>

		<div class="menu1">
			<a href="${pageContext.request.contextPath}/form/createForm"><spring:message code="link.createObject"/></a> | 
			<a href="${pageContext.request.contextPath}/form/setPage"><spring:message code="link.newEvent"/></a> | 
			<a href="switch"><spring:message code="link.${pageInfo.toggleTo}"/></a> |
			<a href="listCategory?category=all"><spring:message code="link.allCategories"/></a> |
			<a href="?theme=${nextTheme}"><spring:message code="link.${nextTheme}"/></a> |
			<a href="history"/><spring:message code="link.history"/></a> |  
			<a href="${pageContext.request.contextPath}/chart/jqPlot"/><spring:message code="title.chart"/></a><br>
		</div>

		</div>
		<div class="fixWindow">
		<table width="100%" class="tableWithHover">
			<thead>
			<tr>
				<th><spring:message code="clomuns.name"/></th>
				<th class="hide"><spring:message code="clomuns.value"/></th>
				<th><spring:message code="clomuns.category"/></th>
				<th><spring:message code="clomuns.complete"/></th>
				<th class="hide"><spring:message code="clomuns.target"/></th>
				<th><spring:message code="clomuns.last"/></th>
				<th class="hide"><spring:message code="clomuns.next"/></th>
				<th class="hide"><spring:message code="clomuns.Type"/></th>
				<th class="hide"><spring:message code="clomuns.frequency"/></th>
				<th></th>
				<th></th>
			</tr>
			</thead>
			<tbody class="bodyFix">
			
			<c:forEach var="object" items="${objectList}" varStatus="stat">
				<tr id=${stat.count}>
					<td>${object.name}</td>
					<td class="hide">${object.value}</td>
					<td onclick="redirect('${object.category}')">${object.category}</td>
					<td>${object.complete}</td>
					<td class="hide">${object.target}</td>
					<td>${object.lastRecurrence}</td>
					<td class="hide">${object.nextRecurrence}</td>
					<td class="hide">
						<c:choose>
							<c:when test="${object.frequencyType!=null&&object.frequencyType==0}"><spring:message code="clomuns.every"/></c:when>
							<c:when test="${object.frequencyType!=null&&object.frequencyType==1}"><spring:message code="clomuns.weekly"/></c:when>
							<c:when test="${object.frequencyType!=null&&object.frequencyType==2}"><spring:message code="clomuns.monthly"/></c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</td>
					<td class="hide">
								<c:choose>
									<c:when test="${object.frequencyType==0}">
										${object.frequency[0][0]} <spring:message code="clomuns.${fn:toLowerCase(object.frequency[0][1])}"/>
									</c:when>
									<c:when test="${object.frequencyType==1}">
										<c:forEach var="item" items="${object.frequency[1]}">
											<spring:message code="${item}"/>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach var="item" items="${object.frequency[object.frequencyType]}">
											${item}
										</c:forEach>
									</c:otherwise>
								</c:choose>
					<form id="del${object.id}" method="delete" action="${pageContext.request.contextPath}/form/delete">
						<input type="hidden" name="id" value="${object.id}"/>
					</form>
					<td class="button update" onclick="update('${object.id}')"></td>
					<td class="button dlete" onclick="document.getElementById('del${object.id}').submit();"></td>
					
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>
	</div>
	</div>
</body>
</html>