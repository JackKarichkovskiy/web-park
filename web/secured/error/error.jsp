<%-- 
    Document   : error
    Created on : Jan 6, 2016, 5:49:11 PM
    Author     : Karichkovskiy Yevhen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <%
            String message = (String)request.getAttribute("exMessage");
            Integer errorCode = (Integer) request.getAttribute("errorCode");
            response.sendError(errorCode, message);
        %>
    </body>
</html>
