<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<my:pagetemplate title="Reservation">
<jsp:attribute name="body">
<table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>trip</th>
            <th>user</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reservations}" var="reservation">
            <tr>
                <td>${reservation.id}</td>
                <td><a href="${pageContext.request.contextPath}/trip/${reservation.trip.id}"><c:out value="${reservation.trip.name}"/></a></td>
                <td><c:out value="${reservation.user.id}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/reservation/${reservation.id}" class="btn btn-primary">Detail</a>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/reservation/delete/${reservation.id}">
                        <button type="submit" class="btn btn-primary">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pagetemplate>