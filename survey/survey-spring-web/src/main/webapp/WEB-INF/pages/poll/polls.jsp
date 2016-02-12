<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel='stylesheet'
	href='/survey-spring-web/webjars/bootstrap/3.3.6/css/bootstrap.min.css'></link>
</head>
<body>
	<script type="text/javascript"
		src="/survey-spring-web/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<!-- <script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script> -->

	<table class="table table-striped">
		<thead>
			<tr>
				<th><spring:message code="pollName" /></th>
<%-- 				<th><spring:message code="owner" /></th> --%>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${polls}" var="poll" varStatus="status">
				<tr>
					<td>${poll.name}</td>
<%-- 					<td>${poll.owner}</td> --%>
					<td>
						<form:form class="form-horizontal" method="POST"> 
							<a class="btn btn-primary" href="/survey-spring-web/poll/${poll.name}">Edit</a>
							<input class="btn btn-primary" type="submit" value="Delete" formaction="/survey-spring-web/poll/delete/${poll.name}"/>
						</form:form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form:form action="/survey-spring-web/poll/new" method="GET">
		<input class="btn btn-primary" type="submit" value="Add Poll" />
	</form:form>
</body>
</html>