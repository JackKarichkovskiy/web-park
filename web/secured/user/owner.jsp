<%-- 
    Document   : owner
    Created on : Jan 10, 2016, 8:46:55 PM
    Author     : Karichkovskiy Yevhen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld"%>
<%@page import="org.webpark.dao.entities.Instruction"%>
<%@page import="org.webpark.dao.entities.Instruction.Status"%>

<h1>Hello Owner ${sessionScope.user.username}!</h1>

<form>
    <h3>Add new instruction</h3>
    <p>Title:<input type="text" name="title"/></p>
    <p><input type="hidden" name="createdBy" value="${sessionScope.user.id}"/></p>

    <p>Executor:<ex:allForestersSelect/></p>

    <p>Status:
        <select name="status">
            <%
                for (Status status : Instruction.Status.values()) {
                    out.println("<option>" + status + "</option>");
                }
            %>
        </select>
    </p>
    <p>
        <input type="submit"/>
        <input type="reset"/>
    </p>
</form>