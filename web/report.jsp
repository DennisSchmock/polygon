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



<h1>Report</h1>
<form action="frontpage?page=report" method="POST">
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
                <td>Category</td>
                <td><select name="category">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                        <option>5</option>
                    </select></td>
                </tr>
                <tr>
                <td></td>
                <td></td>
                </tr>
                <tr>
                    </tr>
                    <c:forEach var="i" begin="0" end="${requestScope.numOfRooms}">
                    <table border="0">

                        <tbody>
                            <tr>
                            <td>Choose Room</td>
                            <td><select name="chosenRoom">

                                    <option>1</option>

                                </select></td>
                            </tr>
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
                            <td><input type="text" name="where" value="" /></td>

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

                
                <tr>
                <td></td>

                </tr>
                <tr>
                <td><input type="file" name="Image of Building" value="" /></td>
                <td><input type="hidden" name="command" value="reportSubmit">
                    <p><input type="submit" name="submit"></p></td>
                </form>
                </tr>
            </tbody>
        </table>
    </div>
    <div>
        2nd div
    </div>



    <form action="frontpage?page=report" method="POST">
        <input type="hidden" name="command" value="reportAddRoom">
        <input type="hidden" name="numOfRooms" value="${requestScope.numOfRooms}">
        <td><input type="submit" value="Add Room" />${requestScope.numOfRooms}</td>
    </form>




    <%@include file="Style/Footer.jsp" %>