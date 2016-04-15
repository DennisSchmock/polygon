<%-- 
    Document   : Building added
    Created on : 30.03.2016
    Author     : Daniel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>






<form action="viewreport1" method="POST">
    <div class="w3-row-padding w3-threequarter">
        <input type="hidden" name="action" value="addfloorsubmit" />
        <div class="w3-half"><label>Floor Number:</label><input type="number" name="floornumber" value="" required class="w3-input w3-border"/></div>
        <div class="w3-half"><label>Floor Size (m2):</label><input type="decimal" name="floorsize" value="" class="w3-input w3-border" required /></div>
        <div class="w3-half"><label>Number of Rooms:</label><input type="number" name="totalrooms" value="" class="w3-input w3-border" required /></div>
        <div class="w3-half"><input name="addFloor" type="submit" value="Add Floor" /></div>

    </div>

    <table class="w3-table w3-striped">
        <tr>
        <th>Floor Number</th>
        <th>Size</th>
        <th>Number of Rooms</th>
        </tr>
       

    </table>
</form>    
