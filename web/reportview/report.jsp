<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Report</title>
<%@include file="/Style/Header.jsp" %>


<%@include file="reportnav.jsp" %>
<%@include file="reportheader.jsp" %>
<c:if test="${requestScope.room!=null}"<%@include file="reportmain.jsp" %></c:if>





<%@include file="/Style/Footer.jsp" %>

