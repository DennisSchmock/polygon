<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="w3-row-padding">
    <div class="w3-threequarter">

        <h4> Report date: ${sessionScope.report.date} - by ${sessionScope.report.polygonUserName}</h4>

    </div>

    <div class="w3-threequarter">
        <h1>Exterior observations</h1>

        <table class="w3-table w3-striped w3-bordered w3-border w3-large">
            <tr>
            <th width="20%">Name</th> 
            <th width="40%">Remarks</th>
            <th width="40%">Picture</th>
            </tr>
            <tbody>
                <c:forEach items="${sessionScope.report.listOfRepExt}" var="reportext">
                    <tr>
                    <td>${reportext.repExtInspectedArea}</td>
                    <td>${reportext.repExtDescription}</td>

                    <td>
                        <img class="w3-round" style="width: 300px" src="${pageContext.request.contextPath}/ReportExtPic/${reportext.repExtPic}" />
                    </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="w3-threequarter">
        <div class="w3-container w3-yellow"><h1>State of Building is: ${sessionScope.report.categoryConclusion}</div>
        <table class="w3-table w3-striped">


            <tr>
            <th>Condition Grade</th>
            <th>Description of building</th>
            <th>Functionalty of building</th>
            </tr>
            <tr>
            <td>Condition Grade 0</td>
            <td>The building is as if it was new</td>
            <td>The Functionality of the building is as descriped</td>
            </tr>
            <tr>
            <td>Condition Grade 1</td>
            <td>The building is intact, but with some wear and some visible damages.
                (Not something that disrupts the functionality )</td>
            <td>The Functionality of the building is as descriped</td>
            </tr>
            <tr>
            <td>Condition Grade 2</td>
            <td>The building has started to decay with some defective elements</td>
            <td>The functionality has been reduced ? risk for consequential damages</td>
            </tr>
            <tr>
            <td>Condition Grade 3</td>
            <td>The building has been degraded and needs to be replaced</td>
            <td>The functionality has ceased - risk for consequential damages</td>
            </tr>
        </table>
    </div>
</div>