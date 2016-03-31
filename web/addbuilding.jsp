<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" type="text/css" href="Style/Style.css">

<title>Add a building</title>
<%@include file="Style/Header.jsp" %>

<main>
    
    <h1>Here you can add a building</h1>
        
    <form name="DummieForm" action="frontpage">
        <input type="hidden" name="page" value="newbuilding" />
        
        (An ID so that you can find it)  Building Name <input type="text" name="buildingName" value="" />
        <br>
        <br>
        
        Street Address <input type="text" name="streetAddress" value="" />
        <br>
        <br>
        Street Number <input type="text" name="streetNumber" value="" />
        <br>
        <br>
        Zip Code <input type="text" name="zipCode" value="" />
        <br>
        <br>
        Building Size (m^2) <input type="text" name="buildingSize" value="" />
        <br>
        <br>
        Building Year <input type="text" name="BuildingYear" value="" />
        <br>
        <br>
        Description of building use
        <textarea name="useOfBuilding" rows="4" cols="20">
        </textarea>
        
        <br>
        
        <input type="submit" value="Save Building" name="submitbuilding"  />
        
    </form>
    
    
</main>


<%@include file="Style/Footer.jsp" %>