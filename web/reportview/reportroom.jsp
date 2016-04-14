<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="w3-row-padding">
    <div class="w3-container w3-threequarter">
        <h1>Room: ${requestScope.reportroom.roomName}</h1>
    </div>
    <div class="w3-threequarter">
        <table class="w3-table w3-striped w3-bordered w3-border w3-large">
            <thead>
                <tr>
                <th >Name</th>
                <th class="w3-leftbar">Remark</th>
                </tr>
            </thead>
            <c:forEach items="${requestScope.reportroom.listOfInt}" var="interior" >
                <tr>
                <td width="150px">${interior.repRoomIntName}</td>

                <td class="w3-leftbar">${interior.remark}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

</div>
<div class="w3-row-padding">
    <div class="w3-threequarter"> <h1>Pictures</h1>
        <table class="w3-table w3-striped w3-bordered w3-border w3-large">
            <thead>
                <tr>
                <th >Name</th>
                <th class="w3-leftbar">Remarks</th>
                </tr>
            </thead>
            <c:forEach items="${requestScope.reportroom.rrPic}" var="pictures" >

            <tr>
            <td width="150px">
                <img class="w3-round" style="width: 300px" src="${pageContext.request.contextPath}/ReportRoomPic/${pictures.filename}" />
               </td>

            <td class="w3-leftbar">${pictures.description}</td>
            </tr>
            </c:forEach>
        </table>
    </div>

</div>
<div class="w3-row-padding">
    <div class="w3-container w3-threequarter">
        <h1>Registered damage</h1>
    </div>
    <div class="w3-threequarter">
        <table class="w3-table w3-striped w3-bordered w3-border w3-large">
            <thead>
                <tr>
                <th >Where?</th>
                <th class="w3-leftbar">What</th>
                <th class="w3-leftbar">When</th>
                <th class="w3-leftbar">Repaired?</th>
                <th class="w3-leftbar">Type of damage?</th>
                </tr>
            </thead>
            <c:forEach items="${requestScope.reportroom.listOfDamages}" var="damage" >
                <tr>
                <td  >${damage.whatHappened}</td>

                <td class="w3-leftbar">${damage.damageTime}</td>
                <td class="w3-leftbar">${damage.whatIsRepaired}</td>
                <td class="w3-leftbar">${damage.damageType}</td>
                <td class="w3-leftbar">${damage.damageType}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

</div>
    <div class="w3-row-padding">
    <div class="w3-container w3-threequarter">
        <h1>Recommendation for ${requestScope.reportroom.roomName}</h1>
    </div>
    <div class="w3-threequarter">
        <table class="w3-table w3-striped w3-bordered w3-border w3-large">
           
            <c:forEach items="${requestScope.reportroom.listOfRec}" var="recommendation" >
                <tr>
                <td  >${recommendation.recommendation}</td>

                
                </tr>
            </c:forEach>
        </table>
    </div>
</div>