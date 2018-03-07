<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>  
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="user"/></title>
<base href="${pageContext.request.contextPath}/"/>
<link rel='stylesheet'
	href='webjars/bootstrap/3.3.6/css/bootstrap.min.css'></link>
</head>
<body>
	<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript"
        src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <c:import url="../common/menu.jsp"></c:import>
    
	<form:form class="form-horizontal" action="user/save"
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
                    <form:errors path="username" class="text-danger"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-4 control-label" for="password"><spring:message code="password"/></label>
				<div class="col-md-4">
					<input id="password" name="password" type="password"
						class="form-control input-md" value="${user.password}">
                    <form:errors path="password" class="text-danger"/>
				</div>
			</div>
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="email"><spring:message code="email"/></label>
				<div class="col-md-4">
					<input id="email" name="email" type="text"
						class="form-control input-md" value="${user.email}">
                    <form:errors path="email" class="text-danger"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-4 control-label" for="role"><spring:message code="role"/></label>
				<div class="col-md-4">
                    <form:select id="role" name="role" path="role" class="form-control input-md">
                        <form:options items="${roles}" />
                    </form:select>
                    <form:errors path="role" class="text-danger"/>
				</div>
			</div>
            <div class="form-group">
                <div class="col-md-4">
                    <form:errors path="*" class="alert alert-danger" role="alert" />
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