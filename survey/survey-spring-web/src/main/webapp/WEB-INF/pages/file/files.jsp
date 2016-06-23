<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="files"/></title>
<link rel='stylesheet'
    href='/survey-spring-web/webjars/bootstrap/3.3.6/css/bootstrap.min.css'></link>
</head>
<body>
	<script type="text/javascript" src="/survey-spring-web/webjars/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript"
        src="/survey-spring-web/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <c:import url="/WEB-INF/pages/common/menu.jsp"></c:import>
    
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
                    <td><a href="${file.id}">${file.filename}</a></td>
<%--                    <td>${file.owner}</td> --%>
                    <td>
                        <form:form class="form-horizontal" method="POST"> 
                            <a class="btn btn-primary" href="/survey-spring-web/file/${file.id}"><span class="glyphicon glyphicon-pencil" ></span></a>
                            <button id="delete" class="btn btn-primary" type="submit" formaction="/survey-spring-web/file/delete/${file.id}">
                            <span class="glyphicon glyphicon-remove" ></span>
                            </button>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <form:form action="/survey-spring-web/file/new" method="GET">
        <input class="btn btn-primary" type="submit" value="<spring:message code="addFile" />" />
    </form:form>
</body>
</html>