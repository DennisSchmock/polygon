<%-- 
    Document   : orderslist
    Created on : Apr 20, 2016, 7:39:34 AM
    Author     : Bruger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

    <%@include file="Style/Header.jsp" %>
<main>
    <div class="w3-row-padding"><div class="w3-full w3-container">
        This is sorted by the order status progress.<br>
            Click the order number to see order details and update the status of an order.
            </div>
        <div class="w3-full">
         <table class="w3-table">
          
             <th align="left">ORDER NUMBER</th>
             <th align="left">BUILDING</th>
             <th align="left">SERVICE DESCRIPTION</th>
             <th align="left">PROGRESS</th>
             <th align="left">STATUS</th>
             
        <c:forEach items="${sessionScope.listOfOrders}" var="order">
            <tr><td><a href="frontpage?page=vieworder&ordernumber=${order.orderNumber}">${order.orderNumber}</a></td>
            <td>${order.buildingName}</td>
            <td>${order.serviceDescription}</td>
            <td><progress value="${order.orderStatus}" max="5"></progress></td>
            <td>${order.statDesc}</td>
            </tr>
        </c:forEach> 
         </table>
            </div>
    </div>
</main>

<%@include file="Style/Footer.jsp" %>
