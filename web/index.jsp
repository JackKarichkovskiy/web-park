<%-- 
    Document   : index
    Created on : Jan 7, 2016, 8:23:09 PM
    Author     : Karichkovskiy Yevhen
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<fmt:setBundle basename="org.webpark.locale.web.WebPagesBundle" var="lang" scope="session"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="index.title" bundle="${lang}"/></title>
    </head>
    <body>
        <div>
            <fmt:setBundle basename="org.webpark.locale.web.WebPagesBundle" var="lang" scope="session"/>
            <ex:lang/>
        </div>
        <div>
            <jsp:include page="secured/entry/login.jsp"/>
        </div>
        <div>
            <fmt:message key="index.plants_title" bundle="${lang}" var="plants_title_lbl"/>
            <ex:allPlants title="${plants_title_lbl}"/>
        </div>
    </body>
</html>
