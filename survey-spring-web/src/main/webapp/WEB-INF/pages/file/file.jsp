<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="file"/></title>
<base href="${pageContext.request.contextPath}/"/>
<link rel='stylesheet' href='webjars/bootstrap/3.3.6/css/bootstrap.min.css'></link>
</head>
<body>
	<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript"
        src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <c:import url="../common/menu.jsp"></c:import>

    <form method="POST" enctype="multipart/form-data" action="file/save">
        <spring:message code="fileToUpload" /> <input type="file" name="file" /> <input type="submit">
        <spring:message code="fileUpload" />
    </form>
</body>
</html>