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
    

    
<div class="leftmenu">
<ul>
<li> <a href="frontpage?page=report">Report</a></li>
<li><a href="frontpage?page=addbuilding">Add building</a></li>
<li> <a href="frontpage?page=addcustomer">Add customer</a></li>
</ul>
</div>

</main>


<%@include file="Style/Footer.jsp" %>