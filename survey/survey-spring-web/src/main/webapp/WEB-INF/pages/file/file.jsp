<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel='stylesheet' href='/survey-spring-web/webjars/bootstrap/3.3.6/css/bootstrap.min.css'></link>
</head>
<body>
    <form method="POST" enctype="multipart/form-data" action="save">
        File to upload: <input type="file" name="file" /> <input type="submit">
        <spring:message code="fileUpload" />
    </form>
</body>
</html>