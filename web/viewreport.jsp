<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Report</title>
<%@include file="Style/Header.jsp" %>
<nav class="w3-sidenav w3-light-grey" style="width:150px;float: left">
    <a href="getreport?action=showreport&reportid=${sessionScope.report.reportId}">View main info</a> 
    <a href="#">Print Report*</a>

    <a class="w3-grey" href="#"></a>

    <c:forEach items="${sessionScope.report.reportFloors}" var="floor" varStatus="count">

        <c:if test="${!empty floor.reportRooms}">
            <div class="w3-accordion">

                <a onclick="myAccFunc(<c:out value="${count.count}"/>)" href="#">Floor: ${floor.floorNumber}</a>

                <div id="room${count.count}" class="w3-accordion-content w3-white w3-card-4">

                    <c:forEach items="${floor.reportRooms}" var="room">
                        <a href="getreport?action=reportroom&viewroom=${room.repRoomId}">${room.roomName}</a>
                    </c:forEach>

                </div>
            </div>
        </c:if>

    </c:forEach>

</nav>



<main style="margin-left: 150px; margin-right: 100px;">
    <h1>Report for ${sessionScope.report.buildingName}</h1>
    <form name="PrintForm" action="frontpage" method="POST">
        <input type="hidden" name="page" value="printReport" />  
        <input type="submit" value="Print Report" name="ReportButton" style="background-color: #f44336; font-size: 20px;" />
    </form>

    <c:if test="${requestScope.showroom==true}"><%@include file="reportview/reportroom.jsp" %></c:if>

    <c:if test="${requestScope.showroom==null}"><%@include file="reportview/reportmain.jsp" %></c:if>
</main>
    <%--This javascript is used for making the accordion in the menu. It was found on w3school.com, and is part of the W3.css --%>
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
<%@include file="Style/Footer.jsp" %>



