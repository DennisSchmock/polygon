<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Landing Page</title>
<%@include file="Style/Header.jsp" %>

<main>
    
    <h1>Landing Page</h1>
    
    <nav>
    <a href="frontpage?page=report">Report</a>
    <a href="frontpage?page=addbuilding">Add building</a>
    <a href="frontpage?page=addcustomer">Add customer</a>
    </nav>
    
    
    
</main>


<%@include file="Style/Footer.jsp" %>