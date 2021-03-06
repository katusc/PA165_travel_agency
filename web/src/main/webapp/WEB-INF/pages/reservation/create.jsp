<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<my:pagetemplate title="Book a trip and excursions">
<jsp:attribute name="body">
    <h4>Creating reservation to trip <c:out value="${trip.name}"/><br/>
<fmt:formatDate value="${trip.dateFrom}" pattern="dd.MM.yyyy, HH:mm"/> - 
<fmt:formatDate value="${trip.dateTo}" pattern="dd.MM.yyyy, HH:mm"/></h4>

<table class="table">
    <tr>
        <th>
            Trip price
        </th>
        <td class="cash">
            <c:out value="${trip.price} CZK"/>
        </td>
    </tr>
</table>
<br/>
<br/>

<form:form method="post" action="${pageContext.request.contextPath}/reservation/create/${trip.id}" modelAttribute="chosenExcursions">
    
<c:if test="${fn:length(trip.excursions) > 0}">
<table class="table">
    <tr>
        <th>
            Choose excursions for this trip:
        </th>
        <th/>
        <th/>
    </tr>
    <tr>
        <th>
            Excursion name
        </th>
        <th>
            Date & time
        </th>
        <th>
            Duration
        </th>
        <th>
            Price
        </th>
        <th>
        </th>
    </tr>
    <c:forEach items="${trip.excursions}" var="excursion">
        <tr>
            <td>    
                <a target="_blank" href="${pageContext.request.contextPath}/excursion/detail/${excursion.id}"><c:out value="${excursion.name}"/></a>
            </td>
            <td>
                <fmt:formatDate value="${excursion.getDate()}" pattern="dd.MM.yyyy, HH:mm"/>
            </td>
            <td>
                <c:out value="${excursion.getDuration()}"/>
            </td>
            <td class="cashBX">
                <c:out value="${excursion.price} CZK"/>
            </td>
            <td class="checkBX">
                <form:checkbox path="functionList" value="${excursion.id.toString()}" onchange="toggleCheckbox(this, ${excursion.price})"/>
            </td>
        </tr>
    </c:forEach>
    </table>
</c:if>
    <br/>
    <h4>Total reservation price:</h4>
    <h4 id="totalPrice"><c:out value="${trip.price} CZK"/></h4>
    
    <br/>
    <br/>
    <button class="btn btn-success" type="submit">Make reservation</button>
    <script>    
    function toggleCheckbox(checkbox, price) {
        var element = document.getElementById("totalPrice");
        var i = parseFloat(element.innerHTML.substring(0, element.innerHTML.length - 4));
        
        $("#totalPrice").fadeOut(400);
        
        setTimeout(function(){

        if (!checkbox.checked) {
            element.innerHTML = '' + (i - price) + '.00 CZK';
        } else {
            element.innerHTML = '' + (i + price) + '.00 CZK';
        }
        
        $("#totalPrice").fadeIn();

        }, 400); 
        
        
    };
</script>
</form:form>
</jsp:attribute>
</my:pagetemplate>