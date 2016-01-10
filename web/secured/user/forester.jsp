<%-- 
    Document   : forester
    Created on : Jan 10, 2016, 8:47:54 PM
    Author     : Karichkovskiy Yevhen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Hello Forester ${sessionScope.user.username}!</h1>

<c:if test="${requestScope.instructions ne null}">
    <c:forEach items="${requestScope.instructions}">

    </c:forEach>
</c:if>
