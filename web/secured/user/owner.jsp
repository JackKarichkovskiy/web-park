<%-- 
    Document   : owner
    Created on : Jan 10, 2016, 8:46:55 PM
    Author     : Karichkovskiy Yevhen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld"%>
<%@page import="org.webpark.dao.entities.Instruction"%>
<%@page import="org.webpark.dao.entities.Instruction.Status"%>

<h2><fmt:message key="owner.welcome" bundle="${lang}"/> ${sessionScope.user.username}!</h2>

<script src="addTask.js" language="Javascript" type="text/javascript"></script>
<form method="POST" action="/WebPark/Controller?command=addInstruction" accept-charset="UTF-8">
    <h4><fmt:message key="owner.add_instruction" bundle="${lang}"/></h4>
    <p><fmt:message key="owner.inst_title" bundle="${lang}"/>:<input type="text" name="title"/></p>
    <p><input type="hidden" name="createdBy" value="${sessionScope.user.id}"/></p>

    <p><fmt:message key="owner.inst_executor" bundle="${lang}"/>:<ex:allForestersSelect/></p>

    <div>
        <fmt:message key="owner.inst_tasks" bundle="${lang}"/>:
        <fmt:message key="owner.add_task" bundle="${lang}" var="add_task_lbl"/>
        <input type="button" value="${add_task_lbl}" onClick="addTask('newTasks');"/>
        <fmt:message key="owner.remove_task" bundle="${lang}" var="remove_task_lbl"/>
        <input type="button" value="${remove_task_lbl}" onClick="removeTask('newTasks');"/>
        <div id="newTasks">
            <p id="task1">
                <ex:allPlantsSelect/>
                <input name="tasks" type="text" placeholder="Task"/>
            </p>
        </div>
    </div>

    <p><fmt:message key="owner.inst_status" bundle="${lang}"/>:
        <select name="status">
            <%
                for (Status status : Instruction.Status.values()) {
                    out.println("<option>" + status + "</option>");
                }
            %>
        </select>
    </p>
    <p>
        <fmt:message key="owner.inst_submit" bundle="${lang}" var="inst_submit_lbl"/>
        <input type="submit" value="${inst_submit_lbl}"/>
        <fmt:message key="owner.inst_reset" bundle="${lang}" var="inst_reset_lbl"/>
        <input type="reset" value="${inst_reset_lbl}"/>
    </p>
</form>

<h4><fmt:message key="owner.done_insts" bundle="${lang}"/>:</h4>
<p><ex:ownerNotConfirmedTasks/></p>