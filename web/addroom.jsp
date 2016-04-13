<%-- 
    Document   : addroom
    Created on : Apr 12, 2016, 7:55:40 PM
    Author     : CJS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

    <title>Add Floors</title>
    <%@include file="Style/Header.jsp" %>
<main>
    <div class="w3-row-padding">
     <p>
        Customer:${sessionScope.selectedCustomer.companyName}<br>
        Building Name:  ${sessionScope.selectedBuilding.buildingName}<br>
        Address: ${sessionScope.selectedBuilding.streetAddress} ${sessionScope.selectedBuilding.streetNumber}<br>
        Zip Code: ${sessionScope.selectedBuilding.zipCode}</p>
    </div>
    
    <form action="frontpage" method="POST">
    <input type="hidden" name="page" value="selFlr" />
    <div class="w3-row-padding">
    <label>Select Floor Number:</label>
    <select name="floors">
        <c:forEach items="${sessionScope.floorsList}" var="floor">
            <option value="${floor.floorId}">${floor.floorNumber}</option>
        </c:forEach>  </select>
        <input name="selFlr" type="submit" value="GO" /></div>    
    
    
    <div class="w3-row-padding">
     <p>
        Floor Number:${sessionScope.selectedFloor.floorNumber}<br>
        Size:  ${sessionScope.selectedFloor.floorSize}<br>
        Total Number of Rooms: ${sessionScope.selectedFloor.totalRooms}<br>
    </p>
    </div>
    
    </form>
    
    <form action="frontpage" method="POST">
        <div class="w3-row-padding">
            <input type="hidden" name="page" value="loadRooms" />
            <div class="w3-half"><input name="loadRooms" type="submit" value="Load Rooms" /></div>
        </div>
    </form>

   
    <form action="frontpage" method="POST">
            <div class="w3-row-padding">
                <input type="hidden" name="page" value="addroomsubmit" />
                <div class="w3-half"><label>Room Name:</label><input type="text" name="roomname" value="" required class="w3-input w3-border"/></div><br>
                <div class="w3-half"><input name="addRoom" type="submit" value="Add Room" /></div>

            </div>
    
        <table class="w3-table w3-striped">
            <tr>
            <th>Room Name</th>
            
            </tr>
             
                    <c:forEach items="${sessionScope.roomsList}" var="room">
                      
                        <tr>
                        <td><ol> ${room.roomName}</ol></td><br>
                        </tr>    
                        
                    </c:forEach>
             
            </table>
    </form>    
</main>

<%@include file="Style/Footer.jsp" %>

