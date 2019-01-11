<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="files" /></title>
<base href="${pageContext.request.contextPath}/" />
<link rel='stylesheet' type="text/css" href='webjars/bootstrap/css/bootstrap.min.css'></link>
<link rel='stylesheet' type="text/css" href='webjars/font-awesome/css/all.min.css'></link>
</head>
<body>
    <script type="text/javascript" src="webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/js/bootstrap.min.js"></script>

    <c:import url="../common/menu.jsp"></c:import>

    <table class="table table-striped">
        <thead>
            <tr>
                <th><spring:message code="filename" /></th>
                <th><spring:message code="owner" /></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${files}" var="file" varStatus="status">
                <tr>
                    <td><a href="${file.id}">${file.filename}</a></td>
                    <td>${file.owner.username}</td>
                    <td><form:form class="form-horizontal" method="POST">
                            <a id="edit" class="btn btn-primary" href="file/${file.id}"><span class="fas fa-edit"></span></a>
                            <button id="delete" class="btn btn-primary" type="submit"
                                formaction="file/delete/${file.id}">
                                <span class="fas fa-trash-alt"></span>
                            </button>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <form:form action="file/new" method="GET">
        <input id="addFile" class="btn btn-primary" type="submit" value="<spring:message code="addFile" />" />
    </form:form>
</body>
</html>