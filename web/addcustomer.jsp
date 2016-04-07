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
            <div class="w3-row-padding">
                <input type="hidden" name="page" value="submitcustomer" />
                <div class="w3-half"><label>Company Name</label><input type="text" name="companyname" value="" required class="w3-input w3-border"/></div>
                <div class="w3-half"><label>Contact person</label><input type="text" name="contactperson" value="" class="w3-input w3-border" /></div>
                <div class="w3-half"><label>Street</label><input type="text" name="street" value="" required class="w3-input w3-border"/></div>
                <div class="w3-half"><label>Street number</label><input type="number" name="streetnumber" value="" required class="w3-input w3-border"/></div>
                <div class="w3-half"><label>Zip code</label><input type="number" name="zip" value="" required class="w3-input w3-border"/></div>
                <div class="w3-half"><label>City</label><input type="text" name="city" value="" required class="w3-input w3-border"/></div>
                <div class="w3-half"><label>Email</label><input type="text" name="email" value="" required class="w3-input w3-border"/></div>
                <div class="w3-half"><label>CVR</label><input type="number" name="cvr" value="" required class="w3-input w3-border"/></div>
                <div class="w3-half"><label>Phone</label><input type="text" name="phone" value="" class="w3-input w3-border" required /></div>
                <div class="w3-half"><input type="submit" value="Create Customer" /></div>


            </div>
        </form>


</main>


<%@include file="Style/Footer.jsp" %>