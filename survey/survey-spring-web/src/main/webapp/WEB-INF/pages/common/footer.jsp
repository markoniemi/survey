<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="pricipal" property="principal" />
<div style="block" class="footer">
	<spring:message code="version"/><spring:message code="app.version"/>
	<spring:message code="username"/><c:out value="${principal.username}"/>
	<spring:message code="role"/><c:out value="${principal.role}"/>
</div>