<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<nav class="w3-sidenav w3-light-grey" style="width:120px">

    <c:forEach var="i" begin="1" end="5">
        <div class="w3-accordion">
            <a onclick="myAccFunc(<c:out value="${i}"/>)" href="#">Floor: <c:out value="${i}"/></a>
            
            <div id="demoAcc<c:out value="${i}"/>" class="w3-accordion-content w3-white w3-card-4">
                
                 <c:forEach items="${sessionScope.report.listOfRepRoom}" var="room">
                     <a href="#">Room 1</a>
                 </c:forEach>
               
            </div>
        </div>

    </c:forEach>

</nav>
<script>
    function myAccFunc(i) {
        var x = document.getElementById("demoAcc" + i);
        if (x.className.indexOf("w3-show") == -1) {
            x.className += " w3-show";
            x.previousElementSibling.className += " w3-green";
        } else {
            x.className = x.className.replace(" w3-show", "");
            x.previousElementSibling.className =
                    x.previousElementSibling.className.replace(" w3-green", "");
        }
    }
</script>
