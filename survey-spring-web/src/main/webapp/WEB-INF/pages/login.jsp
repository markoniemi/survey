<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="login"/></title>
<base href="${pageContext.request.contextPath}/"/>
<link rel='stylesheet' href='webjars/bootstrap/3.3.6/css/bootstrap.min.css'></link>
</head>
<body>
    <script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript"
        src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<div class="col-sm-6 col-md-4 col-md-offset-4">
    <div class="panel panel-default">
        <div class="panel-body">
            <form name="loginForm" action="<c:url value='j_spring_security_check' />" method="POST">
<!--             <form role="form" ng-init="$root.pageTitle = 'Login'"> -->
                <fieldset>
                    <div class="col-sm-12 col-md-10  col-md-offset-1 ">
                        <div class="form-group">
                            <div class="input-group">
                                <span class="input-group-addon">
                                    <i class="glyphicon glyphicon-user"></i>
                                </span>
                                <input id="j_username" class="form-control" placeholder="<spring:message code="username"/>" name="j_username"
                                    type="text" autofocus />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <span class="input-group-addon">
                                    <i class="glyphicon glyphicon-lock"></i>
                                </span>
                                <input id="j_password" class="form-control" placeholder="<spring:message code="password"/>" name="j_password"
                                    type="password" value="" />
                            </div>
                        </div>
                        <div class="form-group">
                            <button id="loginButton" type="submit" title="<spring:message code="login"/>"
                                class="btn btn-lg btn-primary btn-block"><spring:message code="login"/></button>
                        </div>
                        <div class="form-group">
                            <c:if test="${not empty param.error}">
                                <span class="alert alert-danger" role="alert"><spring:message code="login.error" /></span>
                            </c:if>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>