<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>  
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="poll"/></title>
<base href="${pageContext.request.contextPath}/"/>
<link rel='stylesheet'
	href='webjars/bootstrap/3.3.6/css/bootstrap.min.css'></link>
</head>
<body>
	<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript"
        src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <c:import url="../common/menu.jsp"></c:import>
    
	<form:form class="form-horizontal" action="poll/save"
		modelAttribute="poll" method="post">
			<input name="id" type="hidden" value="${poll.id}"/>
<!-- 		<fieldset> -->
			<!-- Form Name -->
<!-- 			<legend>User</legend> -->
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-4 control-label" for="name"><spring:message code="pollName"/></label>
				<div class="col-md-4">
                    <form:input id="pollName" path="name" class="form-control input-md"/>
				</div>
			</div>
			<fieldset>
			<c:forEach items="${poll.questions}" var="question" varStatus="status">
				<form:input path="questions[${status.index}].id" type="hidden"/>
				<div class="form-group">
					<label class="col-md-4 control-label" for="text"><spring:message code="questionText"/></label>
					<div class="col-md-4">
	                    <form:input id="questionText" path="questions[${status.index}].text" class="form-control input-md"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label" for="type"><spring:message code="questionType"/></label>
					<div class="col-md-4">
						<form:select path="questions[${status.index}].type" class="form-control input-md">
							<form:options items="${questionTypes}"/>
						</form:select>
					</div>
				</div>
			</c:forEach>
			<div class="form-group">
				<label class="col-md-4 control-label" for=""></label>
				<div class="col-md-4">
					<button id="addQuestion" name="" formaction="poll/addQuestion" class="btn btn-primary"><spring:message code="addQuestion"/></button>
				</div>
			</div>
			</fieldset>
			<!-- Button -->
			<div class="form-group">
				<label class="col-md-4 control-label" for=""></label>
				<div class="col-md-4">
					<button id="savePoll" name="" class="btn btn-primary">
                        <span class="glyphicon glyphicon-ok" ></span>
                    </button>
				</div>
			</div>
<!-- 		</fieldset> -->
	</form:form>
</body>
</html>