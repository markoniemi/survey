<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="navbar navbar-default">
  <div class="container">
    <div class="navbar-header">                                   
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#"><spring:message code="survey"/></a>
        </div>
        <div class="navbar-collapse collapse">  
          <ul class="nav navbar-nav">
            <li ><a href="<c:url value="/user/users"/>" id="menu-users"><spring:message code="users"/></a></li>
            <li><a href="<c:url value="/file/files"/>" id="menu-files"><spring:message code="files"/></a></li>
            <li><a href="<c:url value="/poll/polls"/>" id="menu-polls"><spring:message code="polls"/></a></li>
            <li class="divider"></li>
            <li><a href="<c:url value="/about" />"><spring:message code="about"/></a></li>
          </ul>
        <form class="navbar-form navbar-right">
            <button id="logout" class="btn btn-default" formaction="<c:url value='/j_spring_security_logout' />">
                <span class="glyphicon glyphicon-log-out" title="<spring:message code="logout"/>" ></span>
            </button>
        </form>
        </div>                           
  </div>
</div>    