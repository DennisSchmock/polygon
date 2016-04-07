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

    <h1>New Report</h1>

    <div>
        <table border="3">

            <tbody>
                <tr>
                <td>Building Name</td>
                <td>Adress</td>
                <td>Zip Code</td>

                </tr>
                <tr>
                <td>${sessionScope.testBuilding.buildingName}</td>
                <td>${sessionScope.testBuilding.streetAddress} ${sessionScope.testBuilding.streetNumber}</td>
                <td>${sessionScope.testBuilding.zipCode}</td>

                </tr>
            </tbody>
        </table>



    </div>

    <div>
        <form action="frontpage" method="post">
            <input type="hidden" name="page" value="newReportSubmit" />
            <table>


                <tbody>
                    <tr>
                    <td>Date in format of YYYY-MM-DD</td>
                    <td><input type="date" name="date" required></td>
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
                    <td><select name="category" required>
                            <option>0</option>
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                        </select></td>
                    </tr>
                    <tr>
                    <td></td>
                    <td></td>
                    </tr>
                    <tr>
                    </tr>
            </table>

            <c:forEach var="i" begin="0" end="${requestScope.numOfRooms}" varStatus="count">
                <select name="roomSelect${count.count}" id="roomSel${count.count}" selected="${buildingRooms[0].roomId}">
                    <c:forEach items="${buildingRooms}" var="room">
                        <option value="${room.roomId}">
                            ${room.roomName}
                        </option>
                    </c:forEach>
                </select>
                <table border="0" cellspacing="5" cellpadding="5">
                    <tbody>
                        <tr>
                        <td>When (in the form of (YYYY-MM-DD)? </td>
                        <td><input type="date" name="when${count.count}" value="" /></td>
                        </tr>
                        <tr>
                        <td>Where did the damage occour?</td>
                        <td><input type="text" name="where${count.count}" value="" /></td>

                        </tr>
                        <tr>
                        <td>How did the damage occour?</td>
                        <td><input type="text" name="how${count.count}" value="" /></td>
                        </tr>
                        <tr>
                        <td>What has been done?</td>
                        <td><input type="text" name="whatIsDone${count.count}" value="" /></td>

                        </tr>


                        <tr>
                        <td>Moist scan:</td>
                        <td><input type="number" name="moistScan${count.count}" value="" /></td>

                        </tr>
                        <tr>
                        <td>moistMeasurePoint:</td>
                        <td><input type="text" name="moistPoint${count.count}" value="" /></td>

                        </tr>
                        <br>
                        <tr>
                        <td>Remarks for walls?</td>
                        <td><input type="text" name="rWalls${count.count}" value="" /></td>
                        </tr>
                        <tr><td></td><td><input type="file" name="" value="" /></td></tr>
                        <tr>
                        <td>Remarks for ceiling?</td>
                        <td><input type="text" name="rCeil${count.count}" value="" /></td>
                        </tr>
                        <tr><td></td><td><input type="file" name="" value="" /></td></tr>
                        <tr>
                        <td>Remarks for floor?</td>
                        <td><input type="text" name="rFloor${count.count}" value="" /></td>
                        </tr>
                        <tr><td></td><td><input type="file" name="" value="" /></td></tr>
                        <tr>
                        <td>Remarks for windows/doors?</td>
                        <td><input type="text" name="rWinDoor${count.count}" value="" /></td>
                        </tr>
                        <tr><td></td><td><input type="file" name="" value="" /></td></tr>
                        <tr>
                        <td><input type="text" name="r1otherLoc" value="*If other type location here*" size="35"/></td>
                        <td><input type="text" name="r1other${count.count}" value="" /></td>
                        </tr>
                        <tr><td></td><td><input type="file" name="" value="" /></td></tr>
                        <tr>
                        <td><input type="text" name="r2otherLoc" value="*If other type location here*" size="35"/></td>
                        <td><input type="text" name="r2other${count.count}" value="" /></td>
                        </tr>
                        <tr><td></td><td><input type="file" name="" value="" /></td></tr>
                        <tr>
                        <td>Recommendations for this room</td>
                        <td><input type="text" name="recommendation${count.count}" value="" size="35" /></td>
                        </tr>
                    </tbody>
                </table>
            </c:forEach>
            <table border="0">
                <tr>
                <td></td>

                </tr>
                <tr>
                <td><input type="file" name="Image of Building" value="" /></td>
                <td><input type="hidden" name="page" value="newReportSubmit">
                    <input type="hidden" name="numOfRooms" value="${requestScope.numOfRooms}">
                    <p><input type="submit" name="submit"></p></td>

                </tr>

                </tbody>
            </table>       
        </form>

    </div>

    <form action="frontpage?page=newreport" method="POST">
        <input type="hidden" name="command" value="reportAddRoom">
        <input type="hidden" name="numOfRooms" value="${requestScope.numOfRooms}">
        <input type="submit" value="Add Room" />
    </form>


</main>


<%@include file="Style/Footer.jsp" %>
