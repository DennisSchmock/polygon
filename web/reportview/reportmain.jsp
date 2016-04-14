<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="w3-container" style="margin-left:120px">

    Show room: ${sessionScope.report.date}

    <br>Test room: ${requestScope.room}<br><br>
    <c:forEach items="${sessionScope.report.listOfRepRoom}" var="room">
        <a href="#">Room 1</a>
    </c:forEach>
</div>