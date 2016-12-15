<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: jakub_kremlacek
--%>
<my:pagetemplate title="Create a trip">
<jsp:attribute name="body">
<form:form method="post" action="${pageContext.request.contextPath}/trip/create" modelAttribute="newTrip">
    <table class="table">
        <tr>
            <td>Name</td>
        </tr>
        <tr>
            <td>Destination</td>
        </tr>
        <tr>
            <td>Description</td>
        </tr>
        <tr>
            <td>Date From</td>
            <td>
                <form:label path="dateFrom"/>
                <c:set var="now" value="<%=new java.util.Date()%>"/>
                <form:input path="dateFrom" class="date" type="text"
                            value="" required="required"/>
                <br>
                Format: dd.MM.yyyy, HH:mm
            </td>
        </tr>
        <tr>
            <td>Date To</td>
            <td>
                <form:label path="dateTo"/>
                <c:set var="now" value="<%=new java.util.Date()%>"/>
                <form:input path="dateTo" class="date" type="text"
                            value="" required="required"/>
                <br>
                Format: dd.MM.yyyy, HH:mm
            </td>
        </tr>
        <tr>
            <td>Price</td>
        </tr>
    </table>
    <button class="btn btn-primary" type="submit">Create Trip</button>
</form:form>
</jsp:attribute>
</my:pagetemplate>