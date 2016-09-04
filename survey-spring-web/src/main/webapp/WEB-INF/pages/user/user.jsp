<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>  
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="user"/></title>
<link rel='stylesheet'
	href='/survey-spring-web/webjars/bootstrap/3.3.6/css/bootstrap.min.css'></link>
</head>
<body>
	<script type="text/javascript" src="/survey-spring-web/webjars/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript"
        src="/survey-spring-web/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <c:import url="/WEB-INF/pages/common/menu.jsp"></c:import>
    
	<form:form class="form-horizontal" action="save"
		modelAttribute="user" method="post">
			<input name="id" type="hidden" value="${user.id}"/>
<!-- 		<fieldset> -->
			<!-- Form Name -->
<!-- 			<legend>User</legend> -->
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="username"><spring:message code="username"/></label>
				<div class="col-md-4">
                    <form:input path="username" readonly="${! empty user.id}" class="form-control input-md"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-4 control-label" for="password"><spring:message code="password"/></label>
				<div class="col-md-4">
					<input id="password" name="password" type="password"
						class="form-control input-md" value="${user.password}">
				</div>
			</div>
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="email"><spring:message code="email"/></label>
				<div class="col-md-4">
					<input id="email" name="email" type="text"
						class="form-control input-md" value="${user.email}">

				</div>
			</div>
			<div class="form-group">
				<label class="col-md-4 control-label" for="role"><spring:message code="role"/></label>
				<div class="col-md-4">
                    <form:select id="role" name="role" path="role" class="form-control input-md">
                        <form:options items="${roles}" />
                    </form:select>
				</div>
			</div>
			<!-- Button -->
			<div class="form-group">
				<label class="col-md-4 control-label" for=""></label>
				<div class="col-md-4">
					<button id="submit" name="" class="btn btn-primary">
                        <span class="glyphicon glyphicon-ok" ></span>
                    </button>
				</div>
			</div>
<!-- 		</fieldset> -->
	</form:form>
</body>
</html>