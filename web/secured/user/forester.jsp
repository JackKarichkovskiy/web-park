<%-- 
    Document   : forester
    Created on : Jan 10, 2016, 8:47:54 PM
    Author     : Karichkovskiy Yevhen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld"%>
<h1><fmt:message key="forester.welcome" bundle="${lang}"/> ${sessionScope.user.username}!</h1>

<h3><fmt:message key="forester.instructions" bundle="${lang}"/>:</h3>
<p><ex:foresterTasks/></p>
