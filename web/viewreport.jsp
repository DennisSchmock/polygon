<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Submitted report</title>
<%@include file="Style/Header.jsp" %>

<main>
    <h1>Submitted report page</h1>
    ${sessionScope.report.date}
</main>
<%@include file="Style/Footer.jsp" %>