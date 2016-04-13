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
    
    <h1></h1>
    
    <h2> Building Name: </h2> 
    ${sessionScope.building.buildingName} <br>
    
    <h2> Building Name: </h2> 
    ${sessionScope.building.streetAddress} <br>
    
    <h2> Street Number:</h2> 
    ${sessionScope.building.streetNumber} <br>
    
    <h2> Zip Code:</h2> 
    ${sessionScope.building.zipCode} <br>
    
    <h2> Building Year:</h2> 
    ${sessionScope.building.buildingYear} <br>
    
    <h2> Building Size: </h2> 
    ${sessionScope.building.buildingSize} <br>
    
    <h2> Use of Building: </h2> 
    ${sessionScope.building.useOfBuilding} <br>
    
    
    <h2> Image of Building: </h2> 
    <img src="${pageContext.request.contextPath}/buildingPic/${sessionScope.building.buildingPic}"/>
    <br>
    

    <br><form action="frontpage" method="POST">
        <input type="hidden" name="buildingidEdit" value="${sessionScope.building.bdgId}" />
        <input type="hidden" name="page" value="editBuilding" />
        <input type="text" name="customer_id" value="${sessionScope.building.bdgId}" />
    <input type="submit" value="Edit building" />
    </form>
    <br>
    
    
</main>


<%@include file="Style/Footer.jsp" %>
