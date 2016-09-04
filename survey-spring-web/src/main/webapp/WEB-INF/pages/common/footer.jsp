<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="username" />
<div class="footer" >
	<div class="footer-text">
    <spring:message code="version" /><spring:message code="app.version" />
	<spring:message code="username" /><c:out value="${username}" />
    </div>
</div>