<%-- 
    Document   : login
    Created on : Jan 7, 2016, 12:12:16 PM
    Author     : Karichkovskiy Yevhen
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%--<fmt:setLocale value="en_US"/>--%>
<fmt:setBundle basename="org.webpark.locale.web.WebPagesBundle" var="lang"/>
<fmt:message key="login.log_in" bundle="${lang}" var="log_in_lbl"/>
<fmt:message key="login.log_out" bundle="${lang}" var="log_out_lbl"/>
<c:choose>
    <c:when test="${(sessionScope.user eq null) or (sessionScope.user.role eq 'GUEST')}">
        <form method="POST" action="/WebPark/Controller?command=logIn">
            <h3><fmt:message key="login.welcome_guest" bundle="${lang}"/></h3>
            <p><fmt:message key="login.username" bundle="${lang}"/>:<input name="username" type="text"/></p>
            <p><fmt:message key="login.password" bundle="${lang}"/>:<input name="password" type="password"/></p>
            <p><input value="${log_in_lbl}" type="submit"/></p>
        </form>
    </c:when>
    <c:otherwise>
        <form method="POST" action="/WebPark/Controller?command=logOut">
            <h3><fmt:message key="login.welcome_user" bundle="${lang}"/> ${sessionScope.user.username}</h3>
            <input value="${log_out_lbl}" type="submit"/><br/>
        </form>
    </c:otherwise>
</c:choose>
