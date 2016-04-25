<%-- 
    Document   : ordersuccess
    Created on : Apr 18, 2016, 9:29:47 AM
    Author     : Cherry
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

    <title>Order Success</title>
    <%@include file="Style/Header.jsp" %>
<main>
    <form action="frontpage" method="POST">
    <div class="w3-row-padding" align = "center">
        <h1>Thank you for your order!</h1>  
        <i>An email of your order request was forwarded to noreply.polygonproject@gmail.com</i><br>
        <i>You can check order progress in your Order History.</i>
    </div>
        <br>
        <br>
        <br>
        <br>
    <div class="w3-row-padding" align = "center">
     <p>
        For instant assistance, please call 70 11 00 44 <br>
        (24 Hours on call service)
     </p>
    </div>
    </form>
    
</main>

<%@include file="Style/Footer.jsp" %>

