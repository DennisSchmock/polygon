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
            <script type="text/javascript">

function yesnoCheck() {
    if (document.getElementById('yesCheck').checked) {
        document.getElementById('ifYes').style.display = 'block';
    }
    else document.getElementById('ifYes').style.display = 'none';
    
    if (document.getElementById('yesCheck1').checked) {
        document.getElementById('ifYes1').style.display = 'block';
    }
    else document.getElementById('ifYes1').style.display = 'none';
    
    if (document.getElementById('yesCheck2').checked) {
        document.getElementById('ifYes2').style.display = 'block';
    }
    else document.getElementById('ifYes2').style.display = 'none';
    
    if (document.getElementById('yesCheck3').checked) {
        document.getElementById('ifYes3').style.display = 'block';
    }
    else document.getElementById('ifYes3').style.display = 'none';

}

</script>

<main>
    <h1> Hello from Report Inspected Room</h1>
    
    <h1> ID for the report to be Created: ${sessionScope.reportToBeCreated.reportId}</h1>
    
    Report for Buidling: ${sessionScope.reportBuilding.buildingName} <br>
    With Address: ${sessionScope.reportBuilding.streetAddress}
    
    <br>
    
    <form name="ReportRoomForm" action="frontpage" method="POST">
         <input type="hidden" name="page" value="submittedRoom" />   
        
    <br>
   Inspection of This Room:
   
   <br>
   Room Name: ${sessionScope.reportRoomToBeCreated.roomName}   Room Floor: ${sessionScope.reportRoomToBeCreated.roomFloor} 
  
        <br>
        
        <br>
        Examination of Room: <br>
        
            
      <input type="radio" value="NORemarks" checked="checked" onclick="javascript:yesnoCheck();" name="Examination" id="noCheck"> NO Remarks
      <input type="radio" value="Remarks" onclick="javascript:yesnoCheck();" name="Examination" id="yesCheck"> Yes
        
      
       <br>

        
        <br>
        <br>
<div id="ifYes" style="display:none">
    
<br>
<br>
 Examination of Room:
 <br>

       Remarks to floor:
     <textarea name="Floor" rows="2" cols="20"></textarea>
       <br>
     
      Remarks to Windows:
     <textarea name="Window" rows="2" cols="20"></textarea>
      
      <br>
      
       Remarks to Celling:
     <textarea name="Celling" rows="2" cols="20"></textarea>
       
       <br>
       
        Other Remarks:
     <textarea name="Other" rows="2" cols="20"></textarea>
        
        
           
</div>
  <br>
  <br>
        
        
        
        
        Has there been any damage:
        <br>
        
        
            
      <input type="radio" value="NODamages" checked="checked" onclick="javascript:yesnoCheck();" name="damage" id="noCheck"> NO Damage
      <input type="radio" value="Damage" onclick="javascript:yesnoCheck();" name="damage" id="yesCheck1"> Yes
      
        
       <br>

        <br>
        <div id="ifYes1" style="display:none">
            
 <BR>
     When has the damage happend:
     <textarea name="damageTime" rows="2" cols="20"></textarea>
     
     <br>
     
     Where
     <textarea name="damagePlace" rows="2" cols="20"></textarea>
     
     <br>
     
     What has happend:
     <textarea name="damageHappend" rows="2" cols="20"></textarea>
     
     <br>
     
     What is fixed:
     <textarea name="damageReparied" rows="2" cols="20"></textarea>
     
     <br>
     
     What kind of damage
     <input type="text" name="damageType" />
     
     <br>
     
        </div>
     <br>
     
    
   
     <br>
        
          MoistScan:
          <br>
          
              
  <input type="radio" value="NoMoist" checked="checked" onclick="javascript:yesnoCheck();" name="Moist" id="noCheck"> No Moist Scan
   <input type="radio" value="Moist" onclick="javascript:yesnoCheck();" name="Moist" id="yesCheck2"> Yes
          

          <div id="ifYes2" style="display:none">
              
          <br>
        
          <br>
          
        Moist scan Result:
        
        
        <textarea name="moistScanResult" rows="2" cols="20"></textarea>
        
        <br>
        Moist  scan Area:
        
        <textarea name="moistScanArea" rows="2" cols="20"></textarea>
        
        <br>
      </div>
        <br>
        <br>
        <br>
        
        
        Add Recomendation for  Room:
        <br>
    <input type="radio" value="NoRecommendation" checked="checked" onclick="javascript:yesnoCheck();" name="Recommendation" id="noCheck"> NO Recomendation
   <input type="radio" value="Recommendation" onclick="javascript:yesnoCheck();" name="Recommendation" id="yesCheck3"> Yes
    
   <div id="ifYes3" style="display: none">
       
       
       Recomendation: <textarea name="recomendation" rows="4" cols="20"></textarea>
       
       
   </div>
   
   <br>
        Upload pictures:
        <input type="file" name="fileUpload"  multiple="multiple"/>
        Discprition: 
     <textarea name="roompicdescrip" rows="2" cols="20"></textarea>
        
        <br>
        <br>
        
        <input type="submit" value="Next" name="NextButton" />
    </form>
</main>


<%@include file="/Style/Footer.jsp" %>
