<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" id="main">
<script type="text/javascript" src="<c:url value="/resources/js/viewFunctions.js" />"></script>

<title>Statistic</title>
		<script type="text/javascript">		
			compact=${pageProperty.compact};
			if(compact){setCompact(!compact);}
		</script>
</head>
<body>
	<div class="container">
		<div>
			<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<div class="left_col">
			<p class="welcome">welcome administrator</p>
			</div>
			<div class="right_col">
				<input class="logout" type="submit" value="Logout" />
			</div>
			</form:form>
			
		</div>
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
				<br>
					 
			</div>
			<div>
			<br>
		
			<form:form action="filterDate" modelAttribute="pageProperty" method="POST">
				from<form:input type="date" path="from"/>
				to<form:input type="date" path="to"/>
				<input type="submit" value="filter">
				<a onclick="switchCompact('${pageContext.request.contextPath}/adminPage/')" id="clink">${switchCompact}</a>
				<c:if test="${total!=null}">
					total: ${total}
				</c:if>
			</form:form>
			</div>
		</div>
		<table>
		<c:choose>
			<c:when test="${pageProperty.currentList=='raw'}">
				<c:set scope="request" var="giveLink" value="true"></c:set>
			</c:when>
			<c:otherwise>
				<c:set scope="request" var="giveLink" value="false"></c:set>
			</c:otherwise>
		</c:choose>
			<thead>
				<c:forEach begin="0" end="0" items="${stat}" var="map">
					<c:forEach var="entry" items="${map}">
						<th>
									${entry.key}
						</th>
					</c:forEach>
				</c:forEach>
			</thead>
			<tbody>
				<c:forEach items="${stat}" var="map">
					<tr>
						<c:forEach items="${map}" var="entry">
							<td>${entry.value}</td>
						</c:forEach>
						<c:if test="${giveLink}">
						<td><a href="session/${map['id']}">session</a></td>
						<td><a href="user/${map['id']}">user</a></td>
						</c:if>
					</tr>

				</c:forEach>
			</tbody>
		</table>
		<c:if test="${pageProperty.currentList=='raw'||
						pageProperty.currentList=='session'||
						pageProperty.currentList=='user'}">
			<br>
			<a href="${pageContext.request.contextPath}/adminPage/previous">&lt&ltprevious</a>
			<a href="${pageContext.request.contextPath}/adminPage/next">next&gt&gt</a>
		</c:if>
	</div>
</body>
</html>