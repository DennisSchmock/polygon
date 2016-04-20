<%-- 
    Document   : orderslist
    Created on : Apr 20, 2016, 7:39:34 AM
    Author     : Bruger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>List of Orders</title>
    <%@include file="Style/Header.jsp" %>
<main>
    <div class="w3-row-padding">
        <h1 align = "center">List Of Orders</h1>
         <table name ="orders">
             <th align="left">ORDER NUMBER</th>
             <th align="left">BUILDING</th>
             <th align="left">SERVICE DESCRIPTION</th>
             <th align="left">PROGRESS</th>
             <th align="left">STATUS</th>
             
        <c:forEach items="${sessionScope.listOfOrders}" var="order">
            <tr>
            <td><item value="${order.orderNumber}">${order.orderNumber}</item> </td>
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
