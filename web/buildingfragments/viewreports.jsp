<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


        <div class="w3-row-padding">
            <div class="w3-container w3-black">
                <h3>List of reports</h3></div>
        </div>
        <div class="w3-row-padding">

            
                <ul class="w3-ul w3-card-4">
                    <c:forEach items="${sessionScope.building.listOfReports}" var="report">
                       
                      
                        <li><a href="getreport?action=showreport&reportid=${report.reportId}">Report date: ${report.date} Report id: ${report.reportId}</a>
                                                                                           
                    </c:forEach>
                </ul>
            
        </div>
