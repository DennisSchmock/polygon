

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


    <h1>Edit Building: ${sessionScope.building.buildingName}</h1>
    <div class="w3-row-padding">
        <div class="w3-half">
    <form name="Building Form" action="frontpage" method="POST" enctype="multipart/form-data">
        <input type="hidden" name="page" value="vieweditedbuilding" />

         <div class="w3-half"><label>(An ID so that you can find it)  Building Name</label> 
         <input type="text" name="buildingName" value="${sessionScope.building.buildingName}" /></div>
        

         <div class="w3-half"><label>Street Address </label>
        <input type="text" name="streetAddress" value="${sessionScope.building.streetAddress}" required /></div>
        
         <div class="w3-half"><label>Street Number </label>
        <input type="text" name="streetNumber" value="${sessionScope.building.streetNumber}" required /></div>
        
        <div class="w3-half"> <label>Zip Code </label>
        <input type="number" name="zipCode" max="9900" value="${sessionScope.building.zipCode}"  required /> </div>
        <%-- Netbeans thinks that this is an String in an number field  --%>
        
        <div class="w3-half"> <label>Aproximate Building Size (m^2) </label>
        <input type="number" step="0.01" name="buildingSize" max="9999" value="${sessionScope.buildingToBeEdited.buildingSize}"  required /></div>
        
         <div class="w3-half"><label>Building Year </label>
        <input type="number" name="BuildingYear" max="2016" value="${sessionScope.building.buildingYear}"  required /></div>
       
         <div class="w3-half"><label>Description of building use</label>
        <textarea name="useOfBuilding" rows="4" cols="20">${sessionScope.building.useOfBuilding}</textarea></div>
        <label>Image of Building:</label>
         <div class="w3-half"><label class="test">Add Image</label>
        
        <input type="file" name="uploadFile" id="fileChooser"/><br/>
        <img src="buildingPic/${sessionScope.building.buildingPic}"  height="300"/></div>
        <span  class="form-field-no-caption"><input type="submit" value="Save Building" name="submitbuilding"  /></span>

    </form>
        </div>
        
        </div>