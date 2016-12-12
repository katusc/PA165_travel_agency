<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<my:pagetemplate>
<jsp:attribute name="body">
<form:form method="post" action="${pageContext.request.contextPath}/auth/login/">
    <div class="jumbotron">
            Id: <input type="text" name="id"/><br/>
            Password: <input type="password" name="password"/><br/>
            <input type="submit"/>
    </div>
</form:form>
    </jsp:attribute>
</my:pagetemplate>