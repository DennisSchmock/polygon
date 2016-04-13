<%-- 
    Document   : Building added
    Created on : 30.03.2016
    Author     : Daniel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>




<div class="w3-row-padding">
    <div class="w3-half">
        <table class="w3-table w3-striped w3-bordered w3-border w3-xlarge">
            <tr><td> Building Name:</td><td>  
                ${sessionScope.building.buildingName} </td></tr>

            <tr><td>Address  </td><td>
                ${sessionScope.building.streetAddress} </td></tr>

            <tr><td> Street Number: </td><td>
                ${sessionScope.building.streetNumber} </td></tr>

            <tr><td> Zip Code: </td><td>
                ${sessionScope.building.zipCode} </td></tr>

            <tr><td> Building Year: </td><td>
                ${sessionScope.building.buildingYear} </td></tr>

            <tr><td> Building Size:  </td><td>
                ${sessionScope.building.buildingSize} </td></tr>

            <tr><td> Use of Building: </td><td> 
                ${sessionScope.building.useOfBuilding} </td></tr>
        </table>
    </div>
    <div class="w3-rest">

            <img class="w3-round" style="width: 300px" src="${pageContext.request.contextPath}/buildingPic/${sessionScope.building.buildingPic}"/>
            <br><br></div>
   

</div>

