<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Add a building</title>
<%@include file="Style/Header.jsp" %>

<main>
    <div class="w3-row-padding">
    <div class="w3-container w3-black">
    <h3>Add building</h3>
    </div><br>

    <form name="Building Form" action="frontpage" method="POST" enctype="multipart/form-data">
        <input type="hidden" name="page" value="newbuilding" />

       <label class="test">Building Name</label> 
        <input type="text" name="buildingName" />
        <br>
        

       <label class="test">Street Address</label>
        <input type="text" name="streetAddress" required />
        
        <br>
       <label class="test">Street Number</label>
        <input type="text" name="streetNumber" required />
        <br>
        
       <label class="test">Zip Code</label>
        <input type="number" name="zipCode" value="" max="9999" required />
        <br>
        
       <label class="test">Building Size (m^2)</label>
        <input type="number" step="0.01" name="buildingSize"  required />
        <br>
        
        <label class="test">Building Image</label>
        <input type="file" name="uploadFile" id="fileChooser" multiple="multiple"/><br/><br/>
        
        
       <label class="test">Building Year</label>
        <input type="number" name="BuildingYear" max="2016" required />
        <br>

       <label class="test">Description of building use</label>
        <textarea name="useOfBuilding" rows="4" cols="20"></textarea>
        <br>
        <span  class="form-field-no-caption"><input type="submit" value="Save Building" name="submitbuilding"  /></span>
    </form>
</div>
</main>


<%@include file="Style/Footer.jsp" %>
