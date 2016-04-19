<%-- 
    Document   : Header
    Created on : 21-Mar-2016, 15:10:46
    Author     : Dennis Schmock
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
        <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">



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
                    <a href="login?page=logout">log out</a>


                </div>

            </c:if>
        </header>

        <ul class="w3-navbar w3-blue">


            <c:if test="${sessionScope.loggedin||sessionScope.testing==true}">
                <c:if test="${sessionScope.user.role=='customer'||sessionScope.testing==true}">
                    <li><a href="frontpage?page=viewlistofbuildings">View My Buildings</a></li>
                    <li><a href="frontpage?page=orderRequest">Order Request</a></li>
                    <li><a href="frontpage?page=orderhistory">Order History</a></li>
                    </c:if>
                    <c:if test="${sessionScope.user.role=='employee'||sessionScope.testing==true}"> 
                    <li><a href="frontpage?page=newreport">Add Report</a></li>
                    </c:if>

                <li><a href="frontpage?page=addbuilding">Add building</a></li>
                  <c:if test="${sessionScope.user.role=='employee'||sessionScope.testing==true}"> 
                <li class="w3-dropdown-hover">
                    <a href="#">Admin/employee</a>
                    <div class="w3-dropdown-content w3-white w3-card-4">

                    
                        <a href="frontpage?page=addcustomer">Add customer</a>
                        <a href="frontpage?page=viewcustomers">View Customers</a>
                        <a href="viewreports?action=listreports">View Reports</a>
                        <a href="#">View Buildings*</a>
                        <a href="frontpage?page=addfloor">Add Floors and rooms</a>
                    </div>
                </li>
                 </c:if>
            </c:if>
            <c:if test="${sessionScope.loggedin==null}"><li><a href="login?page=login">Login</a></li></c:if>

        </ul>
