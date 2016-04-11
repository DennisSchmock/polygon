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
        
        <select name="owners">
            <c:forEach items="${sessionScope.allCustomers}" var="cus">    
            <option>${cus.companyName} - ${cus.contactPerson}</option>
            </c:forEach>
        </select>
        
        
    
    
    
</main>


<%@include file="/Style/Footer.jsp" %>
