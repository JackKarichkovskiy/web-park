<%-- 
    Document   : access_denied
    Created on : Jan 6, 2016, 5:45:09 PM
    Author     : Karichkovskiy Yevhen
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="access_denied.message" bundle="${lang}"/></title>
    </head>
    <body>
        <h3><fmt:message key="access_denied.message" bundle="${lang}"/></h3>
        <a href="/WebPark/index.jsp">Main page</a>
    </body>
</html>
