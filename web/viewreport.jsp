<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Report</title>
<%@include file="Style/Header.jsp" %>


<%@include file="reportview/reportnav.jsp" %>
<%@include file="reportview/reportheader.jsp" %>
<c:if test="${requestScope.room!=null}"><%@include file="reportview/reportroom.jsp" %></c:if>
<%@include file="reportview/reportmain.jsp" %>






<%@include file="Style/Footer.jsp" %>

