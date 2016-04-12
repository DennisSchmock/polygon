<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Custom Title</title>
<%@include file="/Style/Header.jsp" %>

<script>
    function showDivAttid(){
            document.getElementById("pictureForm").style.display = 'inline';
            document.getElementById("pictureButton").style.display = 'none';
    }
</script>

<main>
    
    <h1> Hello from Report Start</h1>
    
    <h1> ID for the report to be Created: ${sessionScope.reportToBeCreated.reportId}</h1>
    
    Report for Buidling: ${sessionScope.reportBuilding.buildingName} <br>
    With Address: ${sessionScope.reportBuilding.streetAddress}
    
    
    Remarks for Outside:
    
    <br>
    
    <form name="exterior" action="frontpage" method="POST">
    <input type="hidden" name="page" value="ChooseRoom" /> 
   
    <h2>Remarks on Roof:</h2>
    <textarea name="remarksOnRoof" rows="4" cols="20">
    </textarea>
    
    <br>
    
    
    <h2>Remarks on Walls:</h2>
    <textarea name="remarksOnWall" rows="4" cols="20">
    </textarea>
    
    <br>
    
    <div id="pictureButton">
    <input type="button" onclick="javascript:showDivAttid();"  value="Add Picture" />
    </div>
    
    <div id="pictureForm" style="display: none">
    
    Upload Pictures:
    <input type="file" name="outsidePictures" />
    
    <h1>Decription of Pictures:</h1>
    <textarea name="decriptionOfPicture" rows="4" cols="20">
    </textarea>
    
    </div>
    <br>
    <input type="submit" value="Next" name="submitButton" />
    </form>
   
    
    
</main>


<%@include file="/Style/Footer.jsp" %>
