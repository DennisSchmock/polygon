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
    <form action="frontpage" method="POST">
    <input type="hidden" name="page" value="selBdg" />
    <div class="w3-row-padding">
    <label>Select building:</label>
    <select name="buildings">
        <c:forEach items="${sessionScope.customersBuildings}" var="building">
            <option>${building.buildingName}</option>
        </c:forEach>  </select>
        <input name="selBdg" type="submit" value="GO" /></div>    
    
    
    <div class="w3-row-padding">
     <p>Building Name:  ${sessionScope.selectedBuilding.buildingName}<br>
        Address: ${sessionScope.selectedBuilding.streetAddress} ${sessionScope.selectedBuilding.streetNumber}<br>
        Zip Code: ${sessionScope.selectedBuilding.zipCode}</p>
    </div>
    
    </form>

   
    <form action="frontpage" method="POST">
            <div class="w3-row-padding">
                <input type="hidden" name="page" value="addFloor" />
                <div class="w3-half"><label>Floor Number:</label><input type="number" name="floornumber" value="" required class="w3-input w3-border"/></div>
                <div class="w3-half"><label>Floor Size:</label><input type="decimal" name="floorsize" value="" class="w3-input w3-border" /></div>
                <div class="w3-half"><label>Number of Rooms:</label><input type="number" name="totalrooms" value="" class="w3-input w3-border" /></div>
                <div class="w3-half"><input name="addFloor" type="submit" value="Add Floor" /></div>

            </div>
    
    
     <div class="w3-row-padding">
                <ul class="w3-ul w3-card-4">
                    <c:forEach items="${sessionScope.newFloor}" var="floor">
                        <li>
                            Floors ${floor.floorNum} 
                            Size ${floor.floorSize}
                            Number of Rooms ${floor.totalRooms}<br>
                        </li>                                                                       
                    </c:forEach>
                </ul>
    </div>
    </form>    
     <form action="frontpage" method="POST">   
         <input type="hidden" name="page" value="continue" />
    <div class="w3-row-padding">
    <div class="w3-label"> <label class="w3-label w3-text-black">Add Floor Plan</label><input type="file" name="floorplan"  class="w3-input w3-border"></div> 
    <div class="w3-label"><input name="submitFlrPlan" type="submit" name="submitflrplan" value="continue"></div>
    </div>
    </form>
</main>

<%@include file="Style/Footer.jsp" %>
