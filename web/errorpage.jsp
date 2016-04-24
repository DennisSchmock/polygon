<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Custom Title</title>
<%@include file="Style/Header.jsp" %>

<main>
    
    <img src="images/PolyError.png" width="300"  alt="PolyError"/>

    
        <h1>Error - something went incredibly wrong!</h1>
        <h2>${requestScope.errormessage}</h2>
        <h2>Please contact us if the problem persists</h2>
    
    
</main>


<%@include file="Style/Footer.jsp" %>
