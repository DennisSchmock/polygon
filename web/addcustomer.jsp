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
        <input type="hidden" name="page" value="submitcustomer" />
        <label>Company Name</label><input type="text" name="companyname" value="" required/><br>
        <label>Contact person</label><input type="text" name="contactperson" value="" /><br>
        <label>Street</label><input type="text" name="street" value="" required/><br>
        <label>Street number</label><input type="number" name="streetnumber" value="" required/><br>
        <label>Zip code</label><input type="number" name="zip" value="" required/><br>
        <label>City</label><input type="text" name="city" value="" required/><br>
        <label>Email</label><input type="text" name="email" value="" required/><br>
        <label>CVR</label><input type="number" name="cvr" value="" required/><br>
        <label>Phone</label><input type="text" name="phone" value="" required/><br>
        <span  class="form-field-no-caption"><input type="submit" value="Create Customer"/></span>
    </form>
    
    
</main>


<%@include file="Style/Footer.jsp" %>