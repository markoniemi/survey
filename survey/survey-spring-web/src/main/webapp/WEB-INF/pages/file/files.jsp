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
                <th><spring:message code="filename" /></th>
<%--                <th><spring:message code="owner" /></th> --%>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${files}" var="file" varStatus="status">
                <tr>
                    <td>${file.filename}</td>
<%--                    <td>${file.owner}</td> --%>
                    <td>
                        <form:form class="form-horizontal" method="POST"> 
                            <a class="btn btn-primary" href="/survey-spring-web/file/${file.id}">Edit</a>
                            <input class="btn btn-primary" type="submit" value="Delete" formaction="/survey-spring-web/file/delete/${file.id}"/>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <form:form action="/survey-spring-web/file/new" method="GET">
        <input class="btn btn-primary" type="submit" value="Add File" />
    </form:form>
</body>
</html>