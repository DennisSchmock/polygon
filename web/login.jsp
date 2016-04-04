<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>Login</title>
<%@include file="Style/Header.jsp" %>
<main>
<h1>Login</h1>
<form action="frontpage" method="POST">
    <input type="hidden" name="page" value="login"/>          
    <label>Username</label><input type="text" name="username" value="" /><br>
    <label>Password </label><input type="password" name="pwd" value="" /><br>
    <span  class="form-field-no-caption"><input type="submit" value="Login" /><br>
        </form>
</main>
        <%@include file="Style/Footer.jsp" %>
