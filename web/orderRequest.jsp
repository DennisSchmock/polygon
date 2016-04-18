<%-- 
    Document   : orderRequest
    Created on : Apr 17, 2016, 11:24:48 PM
    Author     : CJS
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

    <title>Order Request</title>
    <%@include file="Style/Header.jsp" %>
<main>
    <form action="frontpage" method="POST">
    <input type="hidden" name="page" value="selBdgReq" />
    <div class="w3-row-padding">
    <label>Select building:</label>
    <select name="buildings">
        <c:forEach items="${sessionScope.customersBuildings}" var="building">
            <option value="${building.bdgId}">${building.buildingName}</option>
        </c:forEach>  </select>
        <input name="selBdgReq" type="submit" value="GO" /></div>    
    
    
    <div class="w3-row-padding">
     <p>
        Building Name:  ${sessionScope.selectedBuilding.buildingName}<br>
        Address: ${sessionScope.selectedBuilding.streetAddress} ${sessionScope.selectedBuilding.streetNumber}<br>
        Zip Code: ${sessionScope.selectedBuilding.zipCode}</p>
    </div>
    
    </form>
    
    <form name="selectrequest" action="frontpage" method="POST">
        
        <div class="w3-row-padding">
            <label>Select Service Request:</label><br>
        <input type="radio" name="services" value="checkup" />Check-up Building<br>
        <input type="radio" name="services" value="leak" />Leak Detection<br>
        <input type="radio" name="services" value="moisture" />Moisture Testing<br>
        <input type="radio" name="services" value="drying" />Drying Service<br>
        <input type="radio" name="services" value="graffiti" />Graffiti Removal<br>
        <input type="radio" name="services" value="burglary" />Repair and Close after Burglary<br>
        <input type="radio" name="services" value="fire" />Fire Damage Restoration<br>
        <input type="radio" name="services" value="other" />Other: Please specify<br>
        <input type="text" name="otherservice" />
        <br>
        <br>
        <label>Describe the problem:</label><br>
        <textarea name="problemstatement" rows="4" cols="30">
        </textarea>
        <br>
        <br>
        <input type="hidden" name="page" value="orderRequestSubmit" />
        <input name="orderRequest" type="submit" value="Submit" /></div>    
    </form>
    
</main>

<%@include file="Style/Footer.jsp" %>
