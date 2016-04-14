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

<main>
    <h1>Select Condition Grade for building: ${sessionScope.reportBuilding.buildingName}</h1>
    
    
    <form name="conditionGradeForm" action="frontpage" method="POST">
           <input type="hidden" name="page" value="saveFinishedReport" />    
        
        Select Condition Grade:
        <select name="Condition Grade">
            <option value="0">Condition Grade 0</option>
            <option value="1">Condition Grade 1</option>
            <option value="2">Condition Grade 2 </option>
            <option value="3">Condition Grade 3 </option>
        </select>
        
        <br>
        <br>
        
        Customer Accountable: (if None leave it empty):<input type="text" name="customerAccountable" maxlength="30" />
        
        <input type="submit" value="Save Report" name="Save Button" />
        
    </form>
    
    <form name="conditionGradeForm" action="frontpage" method="POST">
        <input type="hidden" name="page" value="backToChooseRoom" />  
     <input type="submit" value="Go Back" name="BackButton" />
    </form>
    
    <br>
    <br>
    
    <table class="w3-table w3-striped">
        
        
        <tr>
            <th>Condition Grade</th>
            <th>Description of building</th>
            <th>Functionalty of building</th>
            </tr>
            <tr>
            <td>Condition Grade 0</td>
            <td>The building is as if it was new</td>
            <td>The Functionality of the building is as descriped</td>
            </tr>
            <tr>
            <td>Condition Grade 1</td>
            <td>The building is intact, but with some wear and some visible damages.
                (Not something that disrupts the functionality )</td>
            <td>The Functionality of the building is as descriped</td>
            </tr>
            <tr>
            <td>Condition Grade 2</td>
            <td>The building has started to decay with some defective elements</td>
            <td>The functionality has been reduced – risk for consequential damages</td>
            </tr>
            <tr>
            <td>Condition Grade 3</td>
            <td>The building has been degraded and needs to be replaced</td>
            <td>The functionality has ceased - risk for consequential damages</td>
            </tr>
    </table>
    
    
    
    <br>
    <br>
    
    <h1>Overview for report</h1>
    
    
    ${sessionScope.reportToBeCreated}
    
    
    
    
    
    
    
</main>


<%@include file="/Style/Footer.jsp" %>
