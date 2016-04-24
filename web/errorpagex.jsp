<%-- 
    Document   : errorpage
    Created on : 20-04-2016, 10:01:33
    Author     : danie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h1>Error - something went incredibly wrong!</h1>
        <h2>${requestScope.errormessage}</h2>
        <h2>Please contact us if the problem persists</h2>
    </body>
</html>
