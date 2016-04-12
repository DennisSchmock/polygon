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
    
    
    
    <h1>Hello ${sessionScope.user.fName} <br>
        Create an Report
    </h1>
    
    
        Choose the Owner of the building:<br>
        
            <form name="chooseUser" action="frontpage" method="POST">
                 <input type="hidden" name="page" value="report_cus_choosen" />
        <select name="owners">
            <c:forEach items="${sessionScope.allCustomers}" var="cus">    
                <option value="${cus.customer_id}">${cus.companyName} - ${cus.contactPerson}</option>
            </c:forEach>
            
        </select>
                 <input type="submit" value="Choose Customer" name="Submit" />     
            </form>
            
        
        <c:if test="${sessionScope.customerSelcted}" >
        <form name="chooseBuilding" action="frontpage" method="POST">
            <input type="hidden" name="page" value="report_start" />
            <select name="buildings">
            <c:forEach items="${sessionScope.customersBuildings}" var="buildings"> 
                <option value="${buildings.bdgId}">${buildings.buildingName} - ${buildings.streetAddress} </option>
            </c:forEach>
            </select>
            
            <input type="submit" value="Choose Building" name="BuildingButton" />
        </form>
        </c:if>
    
    
    
</main>


<%@include file="/Style/Footer.jsp" %>
