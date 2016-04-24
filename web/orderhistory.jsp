<%-- 
    Document   : orderhistory
    Created on : Apr 19, 2016, 11:31:29 AM
    Author     : Bruger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Order History</title>
    <%@include file="Style/Header.jsp" %>
<main>
    <div class="w3-row-padding">
        <h1 align = "center">Order History</h1>
         <table name ="orders">
             <th align="left">DATE</th>
             <th align="left">BUILDING</th>
             <th align="left">SERVICE DESCRIPTION</th>
             <th align="left">PROGRESS</th>
             <th align="left">STATUS</th>
        <c:forEach items="${sessionScope.listOfOrders}" var="order">
            <tr>
            <td><item value="${order.orderNumber}">${order.orderDate}</item> </td>
             <td>${order.buildingName}</td>
            <td>${order.serviceDescription}</td>
            <td><progress value="${order.orderStatus}" max="5"></progress></td>
            <td>${order.statDesc}</td><br>
            </tr>
        </c:forEach> 
         </table>
    </div>
</main>

<%@include file="Style/Footer.jsp" %>
