<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="polls" /></title>
<base href="${pageContext.request.contextPath}/" />
<link rel='stylesheet' type="text/css" href='webjars/bootstrap/css/bootstrap.min.css'></link>
<link rel='stylesheet' type="text/css" href='webjars/font-awesome/css/all.min.css'></link>
</head>
<body>
    <script type="text/javascript" src="webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/js/bootstrap.bundle.min.js"></script>

    <c:import url="../common/menu.jsp"></c:import>

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
                    <td><form:form class="form-horizontal" method="POST">
                            <a id="edit" class="btn btn-primary" href="poll/${poll.id}"><span class="fas fa-edit"></span></a>
                            <button id="delete" class="btn btn-primary" type="submit"
                                formaction="poll/delete/${poll.id}">
                                <span class="fas fa-trash-alt"></span>
                            </button>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <form:form action="poll/new" method="GET">
        <input id="addPoll" class="btn btn-primary" type="submit" value="Add Poll" />
    </form:form>
</body>
</html>