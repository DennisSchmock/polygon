<%-- 
    Document   : reportheader
    Created on : 10-04-2016, 22:06:00
    Author     : Dennis
--%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">

<div class="w3-container" style="margin-left:120px">
    <h1> ${sessionScope.testBuilding.buildingName}</h1>
    
    ${sessionScope.report.date}
    <c:forEach 
    navn på et rum: ${sessionScope.report.listOfRepRoom[0].roomName}<br>
   
    ${sessionScope.testBuilding.streetAddress} ${sessionScope.testBuilding.streetNumber}
    ${sessionScope.testBuilding.zipCode}
        
</div>
