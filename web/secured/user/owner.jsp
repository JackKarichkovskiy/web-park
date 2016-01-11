<%-- 
    Document   : owner
    Created on : Jan 10, 2016, 8:46:55 PM
    Author     : Karichkovskiy Yevhen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld"%>
<%@page import="org.webpark.dao.entities.Instruction"%>
<%@page import="org.webpark.dao.entities.Instruction.Status"%>

<h2>Hello Owner ${sessionScope.user.username}!</h2>

<script src="addTask.js" language="Javascript" type="text/javascript"></script>
<form method="POST">
    <h4>Add new instruction</h4>
    <p>Title:<input type="text" name="title"/></p>
    <p><input type="hidden" name="createdBy" value="${sessionScope.user.id}"/></p>

    <p>Executor:<ex:allForestersSelect/></p>

    <div>
        Tasks:
        <input type="button" value="Add another task" onClick="addTask('newTasks');"/>
        <input type="button" value="Remove last task" onClick="removeTask('newTasks');"/>
        <div id="newTasks">
            <p id="task1">
                <span>1.</span>
                <select name="select1[]">
                    <option>a</option>
                    <option>b</option>
                    <option>c</option>
                </select>
                <select name="select2[]">
                    <option>a</option>
                    <option>b</option>
                    <option>c</option>
                </select>
            </p>
        </div>
    </div>

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