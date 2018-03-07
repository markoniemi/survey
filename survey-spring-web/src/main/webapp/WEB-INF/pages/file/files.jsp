<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="files"/></title>
<base href="${pageContext.request.contextPath}/"/>
<link rel='stylesheet'
    href='webjars/bootstrap/3.3.6/css/bootstrap.min.css'></link>
</head>
<body>
	<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript"
        src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

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
                    <td>
                        <form:form class="form-horizontal" method="POST"> 
                            <a class="btn btn-primary" href="file/${file.id}"><span class="glyphicon glyphicon-pencil" ></span></a>
                            <button id="delete" class="btn btn-primary" type="submit" formaction="file/delete/${file.id}">
                            <span class="glyphicon glyphicon-remove" ></span>
                            </button>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <form:form action="file/new" method="GET">
        <input class="btn btn-primary" type="submit" value="<spring:message code="addFile" />" />
    </form:form>
</body>
</html>