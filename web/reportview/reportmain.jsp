<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="w3-row-padding">
    <div class="w3-threequarter">




        <h4> Report date: ${sessionScope.report.date}</h4>

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





</div>


private ArrayList<ReportFloor> reportFloors;
    private ArrayList<ReportRoom> listOfRepRoom;
        private ArrayList<ReportExterior> listOfRepExt;
            private int buildingId;
            private int categoryConclusion;
            private String polygonUserName;
            private String customerAccountable;
            private boolean finshed;
            private ArrayList<ReportPic> listOfExtPics;