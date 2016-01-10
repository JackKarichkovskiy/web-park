<%-- 
    Document   : account
    Created on : Jan 10, 2016, 2:32:19 PM
    Author     : Karichkovskiy Yevhen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account page</title>
    </head>
    <body>
        <a href="/WebPark/index.jsp">Back to Main Page</a>
        <c:choose>
            <c:when test="${sessionScope.user.role eq 'OWNER'}">
                <%@include file="owner.jsp" %>
            </c:when>
            <c:when test="${sessionScope.user.role eq 'FORESTER'}">
                <%@include file="forester.jsp" %>
            </c:when>
        </c:choose>
    </body>
</html>
