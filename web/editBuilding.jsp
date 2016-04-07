<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Edit Building</title>
<%@include file="Style/Header.jsp" %>

<main>
    
    <h1>Edit Building: ${sessionScope.buildingToBeEdited.buildingName}</h1>
    
    <form name="Building Form" action="frontpage" method="POST">
        <input type="hidden" name="page" value="vieweditedbuilding" />

        <label>(An ID so that you can find it)  Building Name</label> 
        <input type="text" name="buildingName" value="${sessionScope.buildingToBeEdited.buildingName}" />
        <br>
        

        <label>Street Address </label>
        <input type="text" name="streetAddress" value="${sessionScope.buildingToBeEdited.streetAddress}" required />
        
        <br>
        <label>Street Number </label>
        <input type="text" name="streetNumber" value="${sessionScope.buildingToBeEdited.streetNumber}" required />
        <br>
        
        <label>Zip Code </label>
        <input type="number" name="zipCode" max="9900" value="${sessionScope.buildingToBeEdited.zipCode}"  required /> 
        <%-- Netbeans thinks that this is an String in an number field  --%>
        <br>
        
        <label>Building Size (m^2) </label>
        <input type="number" step="0.01" name="buildingSize" max="9999" value="${sessionScope.buildingToBeEdited.buildingSize}"  required />
        <br>
        
        <label>Building Year </label>
        <input type="number" name="BuildingYear" max="2016" value="${sessionScope.buildingToBeEdited.buildingYear}"  required />
        <br>

        <label>Description of building use</label>
        <textarea name="useOfBuilding" rows="4" cols="20">${sessionScope.buildingToBeEdited.useOfBuilding}</textarea>
        <br>
        <span  class="form-field-no-caption"><input type="submit" value="Save Building" name="submitbuilding"  /></span>

    </form>

    
    
</main>


<%@include file="Style/Footer.jsp" %>