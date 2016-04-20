<%-- 
    Document   : vieworder
    Created on : Apr 20, 2016, 10:02:03 AM
    Author     : Cherry
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" type="text/css" href="Style/Style.css">

<title>View an Order</title>
<%@include file="Style/Header.jsp" %>

<main>
        
            <div class="w3-row-padding">
                <h2><b> Order Number:</b> ${sessionScope.selectedOrder.orderNumber} </h2> <br>
    <b> Building Name:</b> ${sessionScope.selectedOrder.buildingName}  <br>
    <b> Service Description:</b> ${sessionScope.selectedOrder.serviceDescription} <br>
    <b> Problem Statement:</b> ${sessionScope.selectedOrder.problemStatement}  <br>
    <b> Progress Status:</b> ${sessionScope.selectedOrder.orderStatus} <br>
    <b> Status Description:</b> ${sessionScope.selectedOrder.statDesc} <br>
    <br>
            </div>
    
    <form name="updateOrderStat" action="frontpage" method="POST">
    <input type="hidden" name="page" value="updateStat" />
    <label><b>New status:</b></label>
    <select name="orderstatus" size="1">
        <option value = "1">1-Order has been placed</option>
        <option value = "2">2-Scheduled Request</option>
        <option value = "3">3-Building Site Visit</option>
        <option value = "4">4-Ongoing Request</option>
        <option value = "5">5-Finished</option>
    </select>
    <input type="submit" value="Save Update" name="updateStat" />
    </form>
    
    
</main>


<%@include file="Style/Footer.jsp" %>

