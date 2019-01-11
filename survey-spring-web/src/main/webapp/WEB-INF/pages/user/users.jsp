<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="users" /></title>
<base href="${pageContext.request.contextPath}/" />
<link rel='stylesheet' type="text/css" href='webjars/bootstrap/css/bootstrap.min.css'></link>
<link rel='stylesheet' type="text/css" href='webjars/font-awesome/css/all.min.css'></link>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" />
</head>
<body>
    <script type="text/javascript" src="webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/js/bootstrap.bundle.min.js"></script>
    <div class="content">
        <c:import url="../common/menu.jsp"></c:import>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th><spring:message code="username" /></th>
                    <th><spring:message code="email" /></th>
                    <th><spring:message code="role" /></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user" varStatus="status">
                    <tr>
                        <td id="username">${user.username}</td>
                        <td id="email">${user.email}</td>
                        <td id="role"><spring:message code="${user.role}" /></td>
                        <td><form:form class="form-horizontal" method="POST">
                                <a id="edit" class="btn btn-primary" href="user/${user.username}"><span
                                        class="fas fa-edit"></span></a>
                                <button id="delete" class="btn btn-primary" type="submit"
                                    formaction="user/delete/${user.username}">
                                    <span class="fas fa-trash-alt"></span>
                                </button>
                            </form:form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <form:form action="user/new" method="GET">
            <input id="addUser" class="btn btn-primary" type="submit" value="<spring:message code="addUser"/>" />
        </form:form>
    </div>
    <c:import url="../common/footer.jsp"></c:import>
</body>
</html>