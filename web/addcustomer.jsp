<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" type="text/css" href="Style/Style.css">

<title>Polygon - add customer</title>
<%@include file="Style/Header.jsp" %>

<main>
    
    <h1>Add customer</h1>
    
    <form action="frontpage" method="POST">
        <input type="hidden" name="page" value="addcus" />
        Company Name <input type="text" name="companyname" value="" required="true"/><br>
        Contact person <input type="text" name="contactperson" value="" /><br>
        Street<input type="text" name="street" value="" required="true"/><br>
        Street number <input type="number" name="streetnumber" value="" required="true"/><br>
        Zip code <input type="number" name="zip" value="" required="true"/><br>
        City <input type="text" name="city" value="" required="true"/><br>
        Email <input type="text" name="email" value="" required="true"/><br>
        CVR <input type="number" name="cvr" value="" required="true"/><br>
        Phone <input type="text" name="phone" value="" required="true"/><br>
        <input type="submit" value="Create Customer" />
    </form>
    
    
</main>


<%@include file="Style/Footer.jsp" %>