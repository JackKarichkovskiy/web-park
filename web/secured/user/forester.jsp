<%-- 
    Document   : forester
    Created on : Jan 10, 2016, 8:47:54 PM
    Author     : Karichkovskiy Yevhen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld"%>
<h1>Hello Forester ${sessionScope.user.username}!</h1>

<h3>Your instructions:</h3>
<p><ex:foresterTasks/></p>
