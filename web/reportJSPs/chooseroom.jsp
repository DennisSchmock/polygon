<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Custom Title</title>
<%@include file="/Style/Header.jsp" %>

<main>
    
    <h1> Hello from Report Choose Room</h1>
    
    <h1> ID for the report to be Created: ${sessionScope.reportToBeCreated.reportId}</h1>
    
    Report for Buidling: ${sessionScope.reportBuilding.buildingName} <br>
    With Address: ${sessionScope.reportBuilding.streetAddress}
    
    <br>
    
    Choose an already existing room:
    <form name="floorselect" action="frontpage" method="POST">
     <input type="hidden" name="page" value="findRooms" />    
        
    Floor: 
    <select name="Floors">
        <c:forEach items = "${sessionScope.reportBuilding.listOfFloors}" var = "floor">
            <c:forEach items="${floor.listOfRooms}" var = "Room">
                
        <option>${floor.floorNumber}</option>
            </c:forEach>   
        </c:forEach>
    </select>
    </form>
    
    
    
    
    
    
</main>


<%@include file="/Style/Footer.jsp" %>
