<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Customer Dashboard</title>
<%@include file="Style/Header.jsp" %>

<main>
    
    <h1>Buildings associated with ${customer.companyName}</h1>
    <ul class="w3-ul w3-card-4">
                
                <c:forEach items="${sessionScope.buildings}" var="building">


                    <li><a href="viewreport1?action=viewbuildingadmin&buildingid=${building.bdgId}">Building: ${building.buildingName} Adress: ${building.streetAddress} ${building.streetNumber}</a> 
                    </li>

                </c:forEach>
            </ul>
    
    
</main>


<%@include file="Style/Footer.jsp" %>
