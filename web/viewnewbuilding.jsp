<%-- 
    Document   : Building added
    Created on : 30.03.2016
    Author     : Daniel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" type="text/css" href="Style/Style.css">

<title>New Building added</title>
<%@include file="Style/Header.jsp" %>

<main>
    
    <h1>Building Added successfully</h1>
    
    <h2> Building Name: </h2> 
    ${sessionScope.newbuilding.buildingName} <br>
    
    <h2> Building Name: </h2> 
    ${sessionScope.newbuilding.streetAddress} <br>
    
    <h2> Street Number:</h2> 
    ${sessionScope.newbuilding.streetNumber} <br>
    
    <h2> Zip Code:</h2> 
    ${sessionScope.newbuilding.zipCode} <br>
    
    <h2> Building Year:</h2> 
    ${sessionScope.newbuilding.buildingYear} <br>
    
    <h2> Building Size: </h2> 
    ${sessionScope.newbuilding.buildingSize} <br>
    
    <h2> Use of Building: </h2> 
    ${sessionScope.newbuilding.useOfBuilding} <br>
    <h2> Image of Building: </h2> 
    <img src="${pageContext.request.contextPath}/buildingPic/${sessionScope.newbuilding.buildingPic}" width="300" />
    <br>
    

    <br>
    <br>
    
    
</main>


<%@include file="Style/Footer.jsp" %>