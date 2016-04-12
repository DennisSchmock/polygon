<%-- 
    Document   : jsptemplate
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Report</title>
<%@include file="Style/Header.jsp" %>


            
                <ul class="w3-ul w3-card-4">
                    <c:forEach items="${sessionScope.reports}" var="report">
                       
                      
                        <li>Report date: ${report.date} Report id: ${report.reportId}<span onclick="this.parentElement.style.display = 'none'" 
                                                                                           class="w3-closebtn w3-margin-right w3-medium">x</span><br></li>
                                                                                           
                    </c:forEach>
                </ul>
            
        </div>
    </div>
</main>
<%@include file="Style/Footer.jsp" %>
