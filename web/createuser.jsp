<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : Dennis Schmock
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Custom Title</title>
<%@include file="Style/Header.jsp" %>

<main class="w3-container w3">
    
    <div class="w3-container"><h1>Create User</h1></div>
    
    <form action="login" method="POST" class="w3-container">
        <input type="hidden" name="page" value="createuser" />
        <label class="w3-label">username</label><input type="text" name="username" value="" class="w3-input"/><br>
        <label class="w3-label">Password</label><input type="text" name="username" value="" class="w3-input" /><br>
        <label class="w3-label">Repeat Password</label><input type="text" name="username" value="" class="w3-input" />
        <input type="submit" value="Create User" class="w3-button" />
    </form>
    
    
</main>


<%@include file="Style/Footer.jsp" %>