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
    <div class="w3-row-padding">
    <div class="w3-container w3-black">
    <h3>Buildings associated with ${customer.companyName}</h3>
    </div>
    <ul class="w3-ul w3-card-4 w3-hoverable">

        <c:forEach items="${sessionScope.buildings}" var="building">


            <li> <div class="w3-row-padding"><a href="viewreport1?action=viewbuildingadmin&buildingid=${building.bdgId}"><div class="w3-quarter"><b>   Building: </b>${building.buildingName}</div><div class="w3-quarter"> <b>   Address:</b> ${building.streetAddress} ${building.streetNumber}</div><div class="w3-quarter"> <b>   State:</b>${building.buildingState}</div> </a> 
                </div>
            </li>
        </c:forEach>


    </ul>

</div>
</main>


<%@include file="Style/Footer.jsp" %>
