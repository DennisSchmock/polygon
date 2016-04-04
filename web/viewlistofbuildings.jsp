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
    
    <h1>Buildings for customer: XXXX</h1> <%-- Has to use the customer_id attribute --%>
    
    <table class="w3-table w3-striped">
        <thead>
            <tr>
            <th>Building Name</th>
            <th>Building Address</th>
            <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${sessionScope.listOfBuildings}" var="building">
                
            <tr>
                
            <td>${building.buildingName}</td>
            <td>${building.streetAddress}</td>
            <td> 
                <form name="Edit Building" action="frontpage" method="POST">
                <input type="hidden" name="page" value="editBuilding" />
                <input type="hidden" name="buildingidEdit" value="${building.bdgId}" />  <%-- This saves the building ID as an parameter --%>
                <input type="submit" value="Edit" name="editButton" class="w3-btn w3-small">
                </form>
            </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>

    
    
</main>


<%@include file="Style/Footer.jsp" %>