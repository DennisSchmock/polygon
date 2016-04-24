<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Display Images</title>
<%@include file="Style/Header.jsp" %>

<main>
    
  
    
    <table class="w3-table w3-striped">
        <thead>
            <tr>
            <th>Building Name</th>
            <th>Building Address</th>
            <th>Building State</th>
           
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${sessionScope.listOfBuildings}" var="building">
                
            <tr>
                
            <td><a href="viewreport1?action=viewbuildingadmin&buildingid=${building.bdgId}">${building.buildingName}</a></td>
            <td><a href="viewreport1?action=viewbuildingadmin&buildingid=${building.bdgId}">${building.streetAddress}</a></td>
            <td><a href="viewreport1?action=viewbuildingadmin&buildingid=${building.bdgId}">${building.buildingState}</a></td>
           
            </tr>
            </c:forEach>
        </tbody>
    </table>

    
    
</main>


<%@include file="Style/Footer.jsp" %>