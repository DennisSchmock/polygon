<%-- 
    Document   : Header
    Created on : 21-Mar-2016, 15:10:46
    Author     : Daniel
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
        <link rel="stylesheet" type="text/css" href="Style/Style.css">



    </head>
    <body> <header>
            <a href="frontpage?page=index">  
                <img src="${pageContext.request.contextPath}/images/polygon-logo.svg"/>
            </a>
            <c:if test="${sessionScope.loggedin==true}">
                <div class="topbar w3-container">
                    <c:out value="${sessionScope.user.companyName}"/><br> 

                    <c:out value="${sessionScope.user.fName}"/> 
                    <c:out value="${sessionScope.user.lName}"/><br>
                    <a href="?page=logout">log out</a>


                </div>

            </c:if>
        </header>

        <ul class="w3-navbar w3-blue">
            <li><a href="frontpage?page=addbuilding">Add building</a></li>
            <li><a href="frontpage?page=addcustomer">Add customer</a></li>
            <li><a href="frontpage?page=viewlistofbuildings">View Buildings</a></li>
            <li><a href="frontpage?page=newreport">Add Report</a></li>
            <li><a href="frontpage?page=listreports">List Reports</a></li>
            <li><a href="login?page=login">Login</a></li>

        </ul>

