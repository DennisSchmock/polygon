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

}

</script>

<main>
    <h1> Hello from Report Inspected Room</h1>
    
    <h1> ID for the report to be Created: ${sessionScope.reportToBeCreated.reportId}</h1>
    
    Report for Buidling: ${sessionScope.reportBuilding.buildingName} <br>
    With Address: ${sessionScope.reportBuilding.streetAddress}
    
    <br>
    
    
    <br>
   Inspection of This Room:
   
   <br>
   Room Name: ${sessionScope.reportRoomToBeCreated.roomName}   Room Floor: ${sessionScope.reportRoomToBeCreated.roomFloor} 
  
        <br>
        
        <br>
        Remarks:
  <form action="">
      <input type="radio" onclick="javascript:yesnoCheck();" name="yesno" id="noCheck"> NO Remarks
      <input type="radio" onclick="javascript:yesnoCheck();" name="yesno" id="yesCheck"> Yes
      
       <br>
</form>
        
        <br>
        <br>
<div id="ifYes" style="display:none">
-------------------------------------------------------------------------------- 
    
<br>
<br>
 Remarks:
 <br>

       Remarks to floor:
     <textarea name="" rows="2" cols="20">
     </textarea>
       <br>
     
      Remarks to Roof:
     <textarea name="" rows="2" cols="20">
     </textarea>
      
      <br>
      
       Remarks to Celling:
     <textarea name="" rows="2" cols="20">
     </textarea>
       
       <br>
       
        Remarks to windows:
     <textarea name="" rows="2" cols="20">
     </textarea>
        
        <br>
        Upload pictures:
        <input type="file" name="Upload Pictures"  />
        Discprition: 
     <textarea name="" rows="2" cols="20">
     </textarea>
        
        <br>
        <br>
        
  --------------------------------------------------------------------------------------------      
</div>
  <br>
  <br>
        
        
        
        
        Has there been any damage:
        
 <form action="">
      <input type="radio" onclick="javascript:yesnoCheck();" name="yesno" id="noCheck"> NO Remarks
      <input type="radio" onclick="javascript:yesnoCheck();" name="yesno" id="yesCheck1"> Yes
      
       <br>
</form>
        <br>
        <div id="ifYes1" style="display:none">
            
 -----------------------------------------------------------------------------    
 <BR>
     When has the damage happend:
     <textarea name="" rows="2" cols="20">
     </textarea>
     
     <br>
     
     Where
     <textarea name="" rows="2" cols="20">
     </textarea>
     
     <br>
     
     What has happend:
     <textarea name="" rows="2" cols="20">
     </textarea>
     
     <br>
     
     What is fixed:
     <textarea name="" rows="2" cols="20">
     </textarea>
     
     <br>
     
     What kind of damage
     <select name="">
         <option>Moist</option>
         <option>Fire</option>
         <option>Other</option>
     </select>
     
     <br>
     
     -----------------------------------------------------------------------------
        </div>
     <br>
     
    
   
     <br>
        
          MoistScan:
  <form action="">
  <input type="radio" onclick="javascript:yesnoCheck();" name="yesno" id="noCheck"> NO Remarks
   <input type="radio" onclick="javascript:yesnoCheck();" name="yesno" id="yesCheck2"> Yes
</form>
          <div id="ifYes2" style="display:none">
              
          <br>
          ------------------------------------------------------------------------
        
          <br>
          
        Moist scan Result:
        
        
        <textarea name="" rows="2" cols="20">
     </textarea>
        
        <br>
        Moist  scan Area:
        
        <textarea name="" rows="2" cols="20">
     </textarea>
        
        <br>
        ---------------------------------------------------------------------------------
      </div>
        <br>
        
    
    
    
</main>


<%@include file="/Style/Footer.jsp" %>
