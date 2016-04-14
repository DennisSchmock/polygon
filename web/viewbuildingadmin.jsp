<%-- 
    Document   : Building added
    Created on : 30.03.2016
    Author     : Daniel Grønbjerg
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" type="text/css" href="Style/Style.css">

<title>New Building added</title>
<%@include file="Style/Header.jsp" %>
<nav class="w3-sidenav w3-light-grey" style="width:150px;float: left">
    <a href="viewreport1?action=showbuilding">Building info</a>
    <a href="viewreport1?action=addfloor">Add Floor</a>
    <a href="viewreport1?action=editbuilding">Edit building</a>
    <a href="#">Floorplan</a>
    <a href="#">Documents</a>
    ---------------

    <c:forEach items="${sessionScope.building.listOfFloors}" var="floor" varStatus="count">

        <div class="w3-accordion">

            <a onclick="myAccFunc(<c:out value="${count.count}"/>)" href="#">Floor: ${floor.floorNumber}</a>

            <div id="room${count.count}" class="w3-accordion-content w3-white w3-card-4">

                <c:forEach items="${floor.listOfRooms}" var="room">
                    <a href="viewreport1?action=viewroom&viewroom=${room.roomId}&floor=${room.floorid}">${room.roomName}</a>
                </c:forEach>
                <b><a href="viewreport1?action=addroom&floor=${floor.floorId}">Add Room</a></b>

            </div>
        </div>

    </c:forEach>


</nav>
<main style="margin-left: 150px; margin-right: 10px;">
    <h1> ${sessionScope.building.buildingName}</h1>
    <c:if test="${requestScope.showBuilding==true}"><%@include file="buildingfragments/viewbuilding.jsp" %></c:if>
    <c:if test="${requestScope.showRoom==true}"><%@include file="buildingfragments/viewroom.jsp" %></c:if>
    <c:if test="${requestScope.addFloor==true}"><%@include file="buildingfragments/addfloor.jsp" %></c:if>
    <c:if test="${requestScope.addRoom==true}"><%@include file="buildingfragments/addroom.jsp" %></c:if>
    <c:if test="${requestScope.editBuilding==true}"><%@include file="buildingfragments/editbuilding.jsp" %></c:if>
    </main>

<%@include file="Style/Footer.jsp" %>
<script>
    function myAccFunc(i) {
        var x = document.getElementById("room" + i);
        if (x.className.indexOf("w3-show") == -1) {
            x.className += " w3-show";
            x.previousElementSibling.className += " w3-green";
        } else {
            x.className = x.className.replace(" w3-show", "");
            x.previousElementSibling.className =
                    x.previousElementSibling.className.replace(" w3-green", "");
        }
    }
</script>
