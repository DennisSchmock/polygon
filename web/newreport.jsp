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
    Building Name:     ${sessionScope.testBuilding.buildingName}<br>

    Adress: ${sessionScope.testBuilding.streetAddress} ${sessionScope.testBuilding.streetNumber}<br>
    Zip Code: ${sessionScope.testBuilding.zipCode}


    

    <form action="frontpage" method="post" class="w3-container">
        <div class="w3-row-padding">
            <div class="w3-half w3-row-padding">
                <input type="hidden" name="page" value="newReportSubmit" />
                <div class="w3-threequarter">
                    <label class="w3-label w3-text-black"><b>Date in format of YYYY-MM-DD</b></label>
                    <input type="date" name="date" required class="w3-input w3-border" >
                </div>

                <div class="w3-half">
                    <label class="w3-label w3-text-black"><b>Remarks for roof</b></label>
                    <input type="text"  name="roof" class="w3-input w3-border">
                </div>

                <div class="w3-half">
                    <label class="w3-label w3-text-black"><b>Remarks for outer walls</b></label>
                    <input type="text" name="walls"class="w3-input w3-border" >
                </div>


                <div class="w3-threequarter"><label class="w3-label w3-text-black"><b>Building Category</b></label>
                    <select name="category" required class="w3-select w3-border">
                        <option>0</option>
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                    </select></div>


                <c:forEach var="i" begin="0" end="${requestScope.numOfRooms}" varStatus="count">

                    <div class="w3-threequarter"><label class="w3-label w3-text-black"><b>Room name</b></label> <select name="roomSelect${count.count}" id="roomSel${count.count}" selected="${buildingRooms[0].roomId}" class="w3-select w3-border">

                            <c:forEach items="${buildingRooms}" var="room">
                                <option value="${room.roomId}">
                                    ${room.roomName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>


                    <div class="w3-threequarter"><label class="w3-label w3-text-black"><b>When (in the form of (YYYY-MM-DD)?</b></label> 
                        <input type="date" name="when${count.count}" value="" class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"><label class="w3-label w3-text-black"><b>Where did the damage occour?</b></label>
                        <input type="text" name="where${count.count}" value="" class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"><label class="w3-label w3-text-black"><b>How did the damage occour?</b></label>
                        <input type="text"  name="how${count.count}" value="" class="w3-input w3-border"/></div>
                    <div class="w3-threequarter"><label class="w3-label w3-text-black"><b>What has been done?</b></label>
                        <input type="text"  name="whatIsDone${count.count}" value="" class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"><label class="w3-label w3-text-black"><b>Moist scan:</b></label>
                        <input type="number" name="moistScan${count.count}" value="" class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"><label class="w3-label w3-text-black"><b>moistMeasurePoint:</b></label>
                        <input type="text"  name="moistPoint${count.count}" value="" class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"><label class="w3-label w3-text-black"><b>Remarks for walls?</b></label>
                        <input type="text"  name="rWalls${count.count}" value="" class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"> <label class="w3-label w3-text-black">Add picture</label><input type="file" name=""  class="w3-input w3-border"></div>
                    <div class="w3-threequarter"><label class="w3-label w3-text-black"><b>Remarks for ceiling?</b></label>
                        <input type="text"  name="rCeil${count.count}" value="" class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"><label class="w3-label w3-text-black">Add picture</label><input type="file" name=""  class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"><label class="w3-label w3-text-black"><b>emarks for floor?</b></label>
                        <input type="text"  name="rFloor${count.count}" value="" class="w3-input w3-border w3-blue"/></div>

                    <div class="w3-threequarter"><label class="w3-label w3-text-black">Add picture</label><input type="file" name=""  class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"><label class="w3-label w3-text-black"><b>Remarks for windows/doors?</b></label>
                        <input type="text"  name="rWinDoor${count.count}" value="" class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"><label class="w3-label w3-text-black">Add picture</label> <input type="file" name="" class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"> <input type="text"  name="r1otherLoc" placeholder="*If other type location here*" size="35"class="w3-input w3-border"/></div>
                    <div class="w3-threequarter"><input type="text"  name="r1other${count.count}" value="" class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"><label class="w3-label w3-text-black">Add picture</label> <input type="file" name="" value="" class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"><input type="text"  name="r2otherLoc" placeholder="*If other type location here*" size="35"class="w3-input w3-border"/></div>
                    <div class="w3-threequarter"> <input type="text"  name="r2other${count.count}" value="" class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"><label class="w3-label w3-text-black">Add picture</label><input type="file" name="" value="" class="w3-input w3-border"/></div>

                    <div class="w3-threequarter"><label class="w3-label w3-text-black"><b>Recommendations for this room</b></label>
                        <input type="text"  name="recommendation${count.count}" value="" size="35" class="w3-input w3-border"/></div>

                </c:forEach> 
            </div>

            <div class="w3-threequarter"><label class="w3-label w3-text-black">Add picture</label><input type="file" name="Image of Building" value="" class="w3-input w3-border"/></div>
            <input type="hidden" name="page" value="newReportSubmit">
            <input type="hidden" name="numOfRooms" value="${requestScope.numOfRooms}">
            <div class="w3-threequarter"><input type="submit" name="submit" value="finish report"></div>

        </div>

    </form>





    <form action="frontpage?page=newreport" method="POST">
        <input type="hidden" name="command" value="reportAddRoom">
        <input type="hidden" name="numOfRooms" value="${requestScope.numOfRooms}">
        <input type="submit" value="Add Room" name="command"/>
    </form>
</main>
<%@include file="Style/Footer.jsp" %>