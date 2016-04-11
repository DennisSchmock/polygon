<%-- 
    Created on : April 11, 2016
    Author     : Cherry
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

    <title>Add Floors</title>
    <%@include file="Style/Header.jsp" %>
<main>
    <div class="w3-row-padding">
    <label>Select building:</label>
    <select name="building">
        <c:forEach items="${sessionScope.listOfBuildings}" var="building">
            <option>${building.buildingName}</option>
        </c:forEach>
    </select>
  
     <p>Building Name:  ${sessionScope.building.buildingName}<br>
        Address: ${sessionScope.building.streetAddress} ${sessionScope.building.streetNumber}<br>
        Zip Code: ${sessionScope.building.zipCode}</p>
    </div>
    
    <form action="frontpage" method="POST">
            <div class="w3-row-padding">
                <input type="hidden" name="page" value="submitAddFloor" />
                <div class="w3-half"><label>Floor Number:</label><input type="number" name="floornumber" value="" required class="w3-input w3-border"/></div>
                <div class="w3-half"><label>Floor Size:</label><input type="number" name="floorsize" value="" class="w3-input w3-border" /></div>
                <div class="w3-half"><input type="submit" value="Add Floor" /></div>

            </div>
    
    
     <div class="w3-row-padding">
                <ul class="w3-ul w3-card-4">
                    <c:forEach items="${sessionScope.floors}" var="building">
                        <li>Floors ${floor.floorNum} Size ${floor.floorSize}<br></li>                                                                       
                    </c:forEach>
                </ul>
    </div>
    <div class="w3-row-padding">
    <div class="w3-label"> <label class="w3-label w3-text-black">Add Floor Plan</label><input type="file" name="floorplan"  class="w3-input w3-border"></div> 
    <div class="w3-label"><input type="submit" name="submitflrplan" value="SAVE"></div>
    </div>
    </form>
</main>

<%@include file="Style/Footer.jsp" %>
