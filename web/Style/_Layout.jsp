<%-- 
    Document   : _Layout
    Created on : 21-Mar-2016, 15:11:49
    Author     : Daniel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@include file="Header.jsp" %>

    <title>JSP</title>
<main>
    
    <h1>Headline 1</h1> 
    
    <h2> Headline 2</h2> 
    
    <table border="1" class="tablestyle">
        <thead>
            <tr>
                <th>People</th>
                <th>Location</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Bjarne</td>
                <td>The Street</td>
            </tr>
            <tr>
                <td>Gert</td>
                <td>The bench</td>
            </tr>
        </tbody>
    </table>

    
    <br>
    
    <p1>
        Here is one style
    </p1>
    
    <br>
    
    <p2>
        Here is another style
    </p2>
    
    <br>
    
        <a href="/frontpage?page=report">Report</a>

    
    
 
   <!--This form also sets the page parameter to something. 
   In this instance the page would be index -->
   
    <form name="DummieForm" action="frontpage">
        <input type="hidden" name="page" value="index" />
        <input type="submit" value="Go to Index" name="submit" />
        
    </form>
    
    
 
   <!--This form also sets the page parameter to something. 
   In this instance the page would be index -->
   
    <form name="DummieForm" action="frontpage">
        <input type="hidden" name="page" value="index" />
        <input type="submit" value="Go to Index" name="submit" />
        
    </form>
    
    
    
    
</main>

<%@include file="Footer.jsp" %>
