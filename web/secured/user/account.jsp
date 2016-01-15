<%-- 
    Document   : account
    Created on : Jan 10, 2016, 2:32:19 PM
    Author     : Karichkovskiy Yevhen
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="account.title" bundle="${lang}"/></title>
    </head>
    <body>
        <p>
            <fmt:setBundle basename="org.webpark.locale.web.WebPagesBundle" var="lang" scope="session"/>
            <ex:lang/>
        </p>
        <a href="/WebPark/index.jsp"><fmt:message key="account.back" bundle="${lang}"/></a>
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
