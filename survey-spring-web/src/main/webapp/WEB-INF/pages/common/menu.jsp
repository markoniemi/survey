<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<nav class="navbar navbar-light navbar-expand-lg">
    <a class="navbar-brand"><spring:message code="survey" /></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive"
        aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse container" id="navbarResponsive">
        <ul class="navbar-nav lr-auto">
            <li class="nav-item"><a class="nav-link" href="<c:url value="/user/users"/>" id="menu-users"><spring:message
                        code="users" /></a></li>
            <li class="nav-item"><a class="nav-link" href="<c:url value="/file/files"/>" id="menu-files"><spring:message
                        code="files" /></a></li>
            <li class="nav-item"><a class="nav-link" href="<c:url value="/poll/polls"/>" id="menu-polls"><spring:message
                        code="polls" /></a></li>
            <li class="divider"></li>
            <li class="nav-item"><a class="nav-link" href="<c:url value="/about" />"><spring:message
                        code="about" /></a></li>
        </ul>
        <ul class="navbar-nav">
            <li class="nav-item"><a class="nav-link" href="<c:url value="/j_spring_security_logout" />"><span
                        class="fas fa-sign-out-alt" title="<spring:message code="logout"/>"></span></a></li>
        </ul>
    </div>
</nav>
