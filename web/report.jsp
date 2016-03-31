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

<main>
    
    <h1>Report</h1>
    <form action="FrontControl" method="POST">
        <div>
        
        <table border="0">
            
            <tbody>
                <tr>
                <td>Date in format of YYYY-MM-DD</td>
                <td><input type="text" name="date"></td>
                </tr>
                <tr>
                <td>Remarks for roof</td>
                <td><input type="text" name="roof"><br></td>
                </tr>
                <tr>
                <td>Remarks for outer walls</td>
                <td><input type="text" name="walls"></td>
                </tr>
                <tr>
                <td></td>
                <td></td>
                </tr>
                <tr>
                <td></td>
                <td></td>
                </tr>
                <tr>
                    <c:forEach var="i" begin="0" end="${numberOfRooms}">
                        <table border="0">
                            
                            <tbody>
                                <tr>
                                <td>Has there been damage to the room?</td>
                                <td><input type="checkbox" name="damageToRoom" value="OFF" /></td>
                            </tr>
                                <tr>
                                <td>When?</td>
                                <td><input type="text" name="When (in the form of (YYYY-MM-DD)" value="" /></td>
                                
                                </tr>
                                <tr>
                                <td>Where?</td>
                                <td><input type="text" name="where" value="" /></td></td
                                
                                </tr>
                                <tr>
                                <td>How?</td>
                                <td><input type="text" name="how" value="" /></td>
                                </tr>
                                <tr>
                                <td>What has been done?</td>
                                <td><input type="text" name="whatIsDone" value="" /></td>
                                
                                </tr>
                            </tbody>
                        </table>

                    </c:forEach>
                
                </tr>
                <tr>
                                <td></td>
                                <form action="FrontControl" method="GET">
                                <input type="hidden" name="command" value="reportAddRoom">
                                <td><input type="submit" value="Add Room" /></td>
                                </form>
                                </tr>
                <tr>
                <td><input type="file" name="Image of Building" value="" /></td>
                <td><input type="hidden" name="command" value="ReportSubmit">
            <p><input type="submit" name="submit"></p></td>
                </tr>
            </tbody>
        </table>
        </div>
        <div>
            2nd div
        </div>
             
             
        </form>
    
</main>


<%@include file="Style/Footer.jsp" %>