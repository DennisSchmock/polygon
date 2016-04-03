<%-- 
    Document   : Header
    Created on : 21-Mar-2016, 15:10:46
    Author     : Daniel
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
        <link rel="stylesheet" type="text/css" href="Style/Style.css">



    </head>
    <body> <header>
            <a href="frontpage?page=index">  
                <img src="${pageContext.request.contextPath}/images/Polygon.png"/>
            </a>

        </header>
        <nav class="w3-sidenav w3-light-grey" style="width:25%">
            <a href="frontpage?page=report" class="w3-green">Report</a>
            <a href="frontpage?page=addbuilding">Add building</a>
            <a href="frontpage?page=addcustomer">Add customer</a>
        </nav>
