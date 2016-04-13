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
    <form name="selectCustomer" action="frontpage" method="POST">
        <input type="hidden" name="page" value="selCust" />
        <div class="w3-row-padding">
        <label>Select customer:</label>
        <select name="owners">
            <c:forEach items="${sessionScope.allCustomers}" var="cus">    
                <option value="${cus.customerId}">${cus.companyName} - ${cus.contactPerson}</option>
            </c:forEach>

        </select>
        <input type="submit" value="GO" name="Submit" />   
        </div>
    </form>


    <form action="frontpage" method="POST">
    <input type="hidden" name="page" value="selBdg" />
    <div class="w3-row-padding">
    <label>Select building:</label>
    <select name="buildings">
        <c:forEach items="${sessionScope.customersBuildings}" var="building">
            <option value="${building.bdgId}">${building.buildingName}</option>
        </c:forEach>  </select>
        <input name="selBdg" type="submit" value="GO" /></div>    
    
    
    <div class="w3-row-padding">
     <p>
        Customer:${sessionScope.selectedCustomer.companyName}<br>
        Building Name:  ${sessionScope.selectedBuilding.buildingName}<br>
        Address: ${sessionScope.selectedBuilding.streetAddress} ${sessionScope.selectedBuilding.streetNumber}<br>
        Zip Code: ${sessionScope.selectedBuilding.zipCode}</p>
    </div>
    
    </form>
    
    <form action="frontpage" method="POST">
        <div class="w3-row-padding">
            <input type="hidden" name="page" value="loadFloors" />
            <div class="w3-half"><input name="loadFloors" type="submit" value="Load Floors" /></div>
        </div>
    </form>

   
    <form action="frontpage" method="POST">
            <div class="w3-row-padding">
                <input type="hidden" name="page" value="addfloorsubmit" />
                <div class="w3-half"><label>Floor Number:</label><input type="number" name="floornumber" value="" required class="w3-input w3-border"/></div>
                <div class="w3-half"><label>Floor Size:</label><input type="decimal" name="floorsize" value="" class="w3-input w3-border" /></div>
                <div class="w3-half"><label>Number of Rooms:</label><input type="number" name="totalrooms" value="" class="w3-input w3-border" /></div>
                <div class="w3-half"><input name="addFloor" type="submit" value="Add Floor" /></div>

            </div>
    
        <table class="w3-table w3-striped">
            <tr>
            <th>Floor Number</th>
            <th>Size</th>
            <th>Number of Rooms</th>
            </tr>
                    <c:forEach items="${sessionScope.floorsList}" var="floor">
                     
                        <tr>
                        <td>${floor.floorNumber} </td>
                        <td>${floor.floorSize}</td>
                        <td>${floor.totalRooms}<br></td>
                        </tr>                                                                   
                    </c:forEach>
    
            </table>
    </form>    
     <form action="frontpage" method="POST">   
         <input type="hidden" name="page" value="continue" />
    <div class="w3-row-padding">
    <div class="w3-label"> <label class="w3-label w3-text-black">Add Floor Plan</label><input type="file" name="floorplan"  class="w3-input w3-border"></div> 
    <div class="w3-label"><input name="continueToRooms" type="submit" name="submitflrplan" value="continue"></div>
    </div>
    </form>
</main>

<%@include file="Style/Footer.jsp" %>
