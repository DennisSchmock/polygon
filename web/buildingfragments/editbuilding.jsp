

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<h1>Edit Building: ${sessionScope.building.buildingName}</h1>
<div class="w3-row-padding">
    <div class="w3-threequarter">
        <form name="Building Form" action="frontpage" method="POST" enctype="multipart/form-data">
            <div class="w3-row-padding">
                <input class="w3-input w3-border" type="hidden" name="page" value="vieweditedbuilding" />

                <div class="w3-half"><label class="w3-label">Building Name</label> 
                    <input class="w3-input w3-border" type="text" name="buildingName" value="${sessionScope.building.buildingName}" /></div>


                <div class="w3-half"><label class="w3-label">Street Address </label>
                    <input class="w3-input w3-border" type="text" name="streetAddress" value="${sessionScope.building.streetAddress}" required /></div>

                <div class="w3-half"><label class="w3-label">Street Number </label>
                    <input class="w3-input w3-border" type="text" name="streetNumber" value="${sessionScope.building.streetNumber}" required /></div>

                <div class="w3-half"> <label class="w3-label">Zip Code </label>
                    <input class="w3-input w3-border" type="number" name="zipCode" max="9900" value="${sessionScope.building.zipCode}"  required /> </div>
                    <%-- Netbeans thinks that this is an String in an number field  --%>

                <div class="w3-half"> <label class="w3-label">Aproximate Building Size (m^2) </label>
                    <input class="w3-input w3-border" type="number" step="0.01" name="buildingSize" max="9999" value="${sessionScope.buildingToBeEdited.buildingSize}"  required /></div>

                <div class="w3-half"><label class="w3-label">Building Year </label>
                    <input class="w3-input w3-border" type="number" name="BuildingYear" max="2016" value="${sessionScope.building.buildingYear}"  required /></div>

                <div class="w3-half"><label class="w3-label">Description of building use</label>
                    <textarea class="w3-input w3-border" name="useOfBuilding" rows="4" cols="20">${sessionScope.building.useOfBuilding}</textarea></div>

                <div class="w3-half"><label class="w3-label">Image of Building:</label>
                    <img src="buildingPic/${sessionScope.building.buildingPic}"  width="300"/></div>
                <div class="w3-half"><label class="w3-label">Add Image</label>

                    <input class="w3-input w3-border" type="file" name="uploadFile" id="fileChooser"/>
                   
                    <input class="w3-button" type="submit" value="Save Building" name="submitbuilding"  /></div>
            </div>
        </form>

    </div>

</div>