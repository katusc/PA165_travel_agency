<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: martin
  Date: 08.12.2016
  Time: 21:45
--%>


<my:pagetemplate title="Excursion detail">
<jsp:attribute name="body">

<table>
    <tr>
        <td>Name</td>
        <td><c:out value="${excursion.name}"/></td>
    </tr>
    <tr>
        <td>Destination</td>
        <td><c:out value="${excursion.destination}"/></td>
    </tr>
    <tr>
        <td>Description</td>
        <td><c:out value="${excursion.description}"/></td>
    </tr>
    <tr>
        <td>Date</td>
        <td><fmt:formatDate value="${excursion.date}" pattern="dd.MM.yyyy HH:mm" /></td>
    </tr>
    <tr>
        <td>Duration</td>
        <td><c:out value="${excursion.duration}"/></td>
        <%--<td><c:out value="${excursion.duration}"/></td>--%>
    </tr>
    <tr>
        <td>Price</td>
        <td><c:out value="${excursion.price}"/> CZK</td>
    </tr>
    <c:if test="${authUser.getIsAdmin()}">
        <tr>
        <td>
            <form method="get" action="${pageContext.request.contextPath}/excursion/edit/${excursion.id}">
                <button type="submit" class="btn btn-primary">Edit</button>
            </form>
        </td>
        <td>
            <form method="post" action="${pageContext.request.contextPath}/excursion/delete/${excursion.id}">
                <button type="submit" class="btn btn-primary">Delete</button>
            </form>
        </td>
        </tr>
    </c:if>
</table>
</jsp:attribute>
</my:pagetemplate>
