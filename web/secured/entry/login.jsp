<%-- 
    Document   : login
    Created on : Jan 7, 2016, 12:12:16 PM
    Author     : Karichkovskiy Yevhen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${(sessionScope.user eq null) or (sessionScope.user.role eq 'GUEST')}">
                <form method="POST" action="./Controller?command=logIn">
                    <h3>Welcome, please login</h3>
                    <span>Login:</span><input name="username" type="text"/><br/>
                    <span>Password:</span><input name="password" type="password"/><br/>
                    <input value="Log In" type="submit"/><br/>
                </form>
            </c:when>
            <c:otherwise>
                <form method="POST" action="./Controller?command=logOut">
                    <h3>Welcome, ${sessionScope.user.username}</h3>
                    <input value="Log Out" type="submit"/><br/>
                </form>
            </c:otherwise>
        </c:choose>
    </body>
</html>
