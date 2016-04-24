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
        <h2>Select Service Request:</h2>
        <input type="radio" name="services" value="Check-up Building" />Check-up Building<br>
        <input type="radio" name="services" value="Leak Detection" />Leak Detection<br>
        <input type="radio" name="services" value="Moisture Testing" />Moisture Testing<br>
        <input type="radio" name="services" value="Drying Service" />Drying Service<br>
        <input type="radio" name="services" value="Graffiti Removal" />Graffiti Removal<br>
        <input type="radio" name="services" value="Repair and Close after Burglary" />Repair and Close after Burglary<br>
        <input type="radio" name="services" value="Fire Damage Restoration" />Fire Damage Restoration<br>
        <input type="radio" name="services" value="other" />Other: <i>(Please specify)</i><br>
        <input type="text" name="otherservice" />
        <br>
        <br>
        <h3>Describe the problem:</h3><i>(Please state if urgent assistance is needed)</i><br>
        <textarea name="problemstatement" rows="4" cols="30" required>
        </textarea>
        <br>
        <br>
        <input type="hidden" name="page" value="orderRequestSubmit" />
        <input name="orderRequest" type="submit" value="Submit" /></div>    
    </form>
    
</main>

<%@include file="Style/Footer.jsp" %>
