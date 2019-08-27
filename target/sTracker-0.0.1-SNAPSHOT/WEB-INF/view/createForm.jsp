<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<spring:message code="lang.current" var="langCurrent"/>
<spring:message code="html.tag"/>
<head>
<spring:theme code="stylesheet" var="themeName" />
<spring:theme code="nextTheme" var="nextTheme" />
<spring:message code="css.menuCss" var="menuCss"/>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="/resources/css/createForm.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" id="main">
<link href="<c:url value="/resources/css/${themeName}"/>" rel="stylesheet">
<link href="<c:url value="/resources/css/${menuCss}"/>" rel="stylesheet">
<script src="<c:url value="/resources/js/list.js" />" type="text/javascript"></script>
<script src="<c:url value="/resources/js/createForm.js" />" type="text/javascript"></script>
<c:set value="title.${pageInfo.type}" var="title"></c:set>
<spring:message code="${title}" var="theTitle"/>
<title>
${theTitle}
</title>
<script type="text/javascript">
contextPath = '<spring:url value="/"></spring:url>'
</script>
</head>
<body onload="show(${webTrackerObject.frequencyType})">
	<div class="container">
			<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<div class="left_col">
				<spring:message code="toggle.lang" var="toggleLang"/>
				<p><a href="${pageContext.request.contextPath}/form/${pageInfo.type}Form?id=${webTrackerObject.id}&${toggleLang}"><spring:message code="toggle.lang.text"/></a></p>
			</div>		
			<div class="right_col">
			<spring:message code="link.logout" var="linkLogout"/>
			<spring:message code="link.welcome" var="linkWelcome"/>
				<p class="welcome">${linkWelcome} <security:authentication property="principal.username" /></p>
				<input class="logout" type="submit" value="${linkLogout}"/>
			</div>
		</form:form>
		<div class="varSize">
			<h2 class="back"
				style="padding-top: 10px; padding-bottom: 10px; margin-bottom: 20px; margin-top: 20px;">
				${theTitle}</h2>
			<form:form action="${pageInfo.type}" modelAttribute="webTrackerObject" method="POST">
				<form:hidden path="id" />
				<form:hidden path="lastRecurrence" />
				<form:hidden path="complete" />
				<table style="border-collapse: collapse;">
					<tbody>
						<tr>
							<td><spring:message code="clomuns.name"/></td>
							<td><form:input path="name"></form:input></td>
							<td><form:errors path="name" cssClass="errorClass" /></td>
						</tr>
						<tr>
							<td><spring:message code="clomuns.value"/></td>
							<td><form:input path="value"></form:input></td>
							<td><form:errors path="value" cssClass="errorClass" /></td>
						</tr>
						<tr>
							<td><spring:message code="clomuns.category"/></td>
							<td><form:input path="category"></form:input></td>
						</tr>
						<tr>
							<td><spring:message code="clomuns.target"/></td>
							<td><form:input path="target"></form:input></td>
							<td><form:errors path="target" cssClass="errorClass" /></td>
						</tr>

						<tr>
							<td><spring:message code="clomuns.startOn"/></td>
							<td><form:input type="date" path="nextRecurrence"></form:input></td>
							<td><form:errors path="nextRecurrence" cssClass="errorClass" /></td>
						</tr>

						<tr class="color1Hov">
							<spring:message code="clomuns.important" var="clomunsImportant"/>
							<spring:message code="clomuns.notImportant" var="clomunsNotImportant"/>
							<td colspan="2">
								<form:radiobutton path="important" value="true" label="${clomunsImportant}"></form:radiobutton>
								<form:radiobutton path="important" value="false" label="${clomunsNotImportant}"></form:radiobutton></td>
						</tr>
						<tr class="color1">
							<td colspan="2"><spring:message code="clomuns.frequency"/></td>
						</tr>

						<tr class="color1 color1Hov">
							<td colspan="2">
								<label>
									<form:radiobutton path="frequencyType" value="0" onclick="show(0)"></form:radiobutton>
									<spring:message code="clomuns.every"/><br>
								</label>
							</td>
						</tr>
						<tr class="color1 color1Hov">
							<td colspan="2">
								<label>
									<form:radiobutton path="frequencyType" value="1" onclick="show(1)"></form:radiobutton>
									<spring:message code="clomuns.weekly"/><br>
								</label>
							</td>
						</tr>
						<tr class="color1 color1Hov">
							<td colspan="2">
								<label>
									<form:radiobutton path="frequencyType" value="2" onclick="show(2)"></form:radiobutton>
									<spring:message code="clomuns.monthly"/><br>
								</label>
							</td>
						</tr>
						<tr class="color1" id="f0">
							<td colspan="2">
								<div>
								<spring:message code="clomuns.days" var="clomunsDays"/>
								<spring:message code="clomuns.weeks" var="clomunsWeeks"/>
								<spring:message code="clomuns.months" var="clomunsMonths"/>
									<form:input path="frequency[0][0]" />
									<form:select path="frequency[0][1]">
										<form:option value="DAYS" label="${clomunsDays}"></form:option>
										<form:option value="WEEKS" label="${clomunsWeeks}"></form:option>
										<form:option value="MONTHS" label="${clomunsMonths}"></form:option>
									</form:select>
								</div>
							</td>
							<td><form:errors path="frequency[0][0]" cssClass="errorClass" /></td>
							<td><form:errors path="frequency[0][1]" cssClass="errorClass" /></td>
						</tr>
						<tr class="color1 color1Hov" id="f1">
							<td colspan="2">
								<div>
									<spring:message code="list.daysPrefix" var="listDaysPrefix"/>
									<spring:message code="list.days" var="listDays"/>
									 <c:set var="theDaysPrefix" value="${fn:split(listDaysPrefix,',')}" />
									 <c:set var="theDays" value="${fn:split(listDays,',')}" />
									<c:forEach items="${theDays}" var="dayName" varStatus="key">
										<label><form:checkbox path="frequency[1]" value="${dayName}"/>
										${theDaysPrefix[key.index]}
										</label>
									</c:forEach>
								<td><form:errors path="frequency[1]" cssClass="errorClass" /></td>
								</div>
							</td>
						</tr>


						<tr class="color1 color1Hov" id="f2">
							<td colspan="2">
								<div>
									<ul class="ks-cboxtags">
										<c:forEach begin="1" end="31" varStatus="key">
											<label><form:checkbox path="frequency[2]"
													value="${key.index}" />${key.index}</label>
											<c:out value=""></c:out>
											<c:if test='${key.index%7==0}'>
												<br>
											</c:if>

										</c:forEach>

									</ul>
								</div>
							</td>
						</tr>
						<tr style="background-color: transparent;">
							<td colspan="2">
								<hr>
							</td>
						</tr>
						<tr style="background-color: transparent;">
							<td colspan="2">
							<input type="submit" class="myButton myY">
								<input type="button" class="myButton myN"
								onclick="returnToList()" value="cancel"></td>
						</tr>
					</tbody>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>