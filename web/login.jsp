<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Login</title>
<%@include file="Style/Header.jsp" %>
<main>
    <h1>Login</h1>

    <c:if test="${sessionScope.loggedin==null||sessionScope.loggedin==false}">
        <form action="login" method="POST">
            
          Customer  <input type="radio" name="empOrCus" value="emp" checked="checked" />
          Employee  <input type="radio" name="empOrCus" value="cus" />
          <br>
          
            <input type="hidden" name="page" value="loguserin"/>          
            <label>Username</label><input type="text" name="username" value="" /><br>
            <label>Password </label><input type="password" name="pwd" value="" /><br>
            <input type="submit" value="Login" /><br>
        </form>
         <c:if test="${sessionScope.loggedin!=null&&sessionScope.loggedin==false}">
                Wrong user name or password!
            </c:if>
    </c:if>
    <c:if test="${sessionScope.loggedin==true}">
        
        Hello <c:out value="${sessionScope.user.fName}"/> <c:out value="${sessionScope.user.userName}"/>

       
            
    </c:if>
    
    
    </main>
    <%@include file="Style/Footer.jsp" %>
