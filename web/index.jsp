<%-- 
    Document   : index
    Created on : Jan 7, 2016, 8:23:09 PM
    Author     : Karichkovskiy Yevhen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="ex" uri="WEB-INF/custom.tld"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Park Web Application</title>
    </head>
    <body>
        <div>
            <jsp:include page="secured/entry/login.jsp"/>
        </div>
        <div>
            <ex:allPlants title="Park plants"/>
        </div>
    </body>
</html>