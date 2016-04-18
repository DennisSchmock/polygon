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
        <input type="hidden" name="action" value="addfilessubmit" />
        <div class="w3-half"><label>Remark:</label><input type="Text" name="floornumber" value="" required class="w3-input w3-border"/></div>
        <div class="w3-half"><input name="addFiles" type="file" multiple="multiple" /></div>

    </div>
    <br>
    <br>
    <br>

    
</form>    
