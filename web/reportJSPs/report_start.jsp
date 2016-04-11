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
    
    <h1> Hello from Report Start</h1>
    
    <h1> ID for the report to be Created: ${sessionScope.reportToBeCreated.reportId}</h1>
    
    
    
</main>


<%@include file="/Style/Footer.jsp" %>
