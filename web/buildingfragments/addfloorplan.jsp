<%-- 
    Document   : Building added
    Created on : 30.03.2016
    Author     : Daniel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>






<form action="viewreport1" method="POST" enctype="multipart/form-data">
    <c:if test="${requestScope.filessubmitted==true}">Your upload is done</c:if>
    <div class="w3-row-padding w3-threequarter">
        <h4>Please upload floorplans here so we can provide the best posible service!</h4>
        
        <select name="floors">
        <c:forEach items="${sessionScope.floorsList}" var="floor">
            <option value="${floor.floorId}">${floor.floorNumber}</option>
            
        </c:forEach>
        </select>
        Please select the floor this plan belongs to
        <br>
        <br>
        
        <input type="hidden" name="action" value="addfloorplanssubmit" />
        <div class="w3-half"><input name="uploadFile" type="file" multiple="multiple" required/></div>
        <input type="submit" value="Add Files" />
    </div>
    
</form>
    <br>
    <div class="w3-row-padding w3-threequarter">
    <h4>You currently have the following floorplans uploaded:</h4>    
    <br>
    <ul>
        <c:forEach items="${sessionScope.floorplans}" var="floorplan">
            <li>${floorplan.documentname}</li>                                                             
                    </c:forEach>
    </ul>
    </div>

    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
