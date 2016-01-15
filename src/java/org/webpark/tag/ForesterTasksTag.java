/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.log4j.Logger;
import org.webpark.controller.command.CommandResult;
import org.webpark.controller.command.WebTags;
import org.webpark.controller.uri.UriBuilder;
import org.webpark.dao.AppDaoFactory;
import org.webpark.dao.InstructionDaoServiceInterface;
import org.webpark.dao.entities.Instruction;
import org.webpark.dao.entities.Instruction.Status;
import org.webpark.dao.entities.InstructionStep;
import org.webpark.dao.entities.User;
import org.webpark.dao.exception.DAOException;
import org.webpark.dao.mysql.instruction.MySQLInstructionDaoService;
import org.webpark.locale.AppBundleFactory;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class ForesterTasksTag extends SimpleTagSupport {

    private static final String DATABASE_CONN_ERROR = "log.database_conn_error";
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();
    private static final String ERROR_PAGE = UriBuilder.getUri("error_page", CommandResult.JumpType.FORWARD);

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(WebTags.USER_TAG);

        InstructionDaoServiceInterface instructionDao = AppDaoFactory.getInstance().getInstructionDao();

        List<Instruction> allForesterInstructions = null;
        try {
            allForesterInstructions = instructionDao.getAllNotDoneInstructionsByForester(user.getId().toString());

            JspWriter out = getJspContext().getOut();
            out.println("<ol>");
            for (Instruction instruction : allForesterInstructions) {
                out.println("<li>");
                out.println("<form method=\"POST\" action=\"/WebPark/Controller?command=updateInstructionStatuses\">");
                out.println(String.format("<input type=\"hidden\" value=\"%s\" name=\"%s\"/>", instruction.getId(), WebTags.INSTRUCTION_ID_TAG));
                out.println(String.format("<p>%s: %s</p>", "Title", instruction.getTitle()));
                out.println(String.format("<p>%s:</p>", "Tasks"));
                Map<String, List<Object>> allStepsInInstruction = instructionDao.getAllStepsInInstruction(instruction.getId().toString());
                out.println("<ul>");
                for (int i = 0; i < allStepsInInstruction.get(MySQLInstructionDaoService.GetAllStepsInInstructionResultTags.STEP_ID).size(); i++) {
                    out.println("<li>");
                    out.println(String.format("<div><input type=\"hidden\" value=\"%s\" name=\"%s\"/></div>", allStepsInInstruction.get(MySQLInstructionDaoService.GetAllStepsInInstructionResultTags.STEP_ID).get(i), WebTags.INSTRUCTION_STEP_ID_TAG));
                    out.println(String.format("<div>%s: %s</div>", "Plant", allStepsInInstruction.get(MySQLInstructionDaoService.GetAllStepsInInstructionResultTags.PLANT_NAME).get(i)));
                    out.println(String.format("<div>%s: %s</div>", "Task", allStepsInInstruction.get(MySQLInstructionDaoService.GetAllStepsInInstructionResultTags.STEP_TASK).get(i)));
                    String report = (String) allStepsInInstruction.get(MySQLInstructionDaoService.GetAllStepsInInstructionResultTags.STEP_REPORT).get(i);
                    String notNullReport = report != null ? report : "";
                    out.println(String.format("<div>%s: <input type=\"text\" value=\"%s\" name=\"%s\"/></div>", "Report", notNullReport, WebTags.INSTRUCTION_STEP_REPORT_TAG));
                    out.println(String.format("<select name=\"%s\">", WebTags.INSTRUCTION_STEP_STATUS_TAG));
                    for (InstructionStep.Status status : InstructionStep.Status.values()) {
                        String stepStatus = (String)allStepsInInstruction.get(MySQLInstructionDaoService.GetAllStepsInInstructionResultTags.STEP_STATUS).get(i);
                        String selected = status.equals(InstructionStep.Status.valueOf(stepStatus)) ? "selected=\"selected\"" : "";
                        out.println(String.format("<option %s>%s</option>", selected, status));
                    }
                    out.println("</select>");
                    out.println("</li>");
                }
                out.println("</ul>");
                out.println(String.format("<div><select name=\"%s\">", WebTags.INSTRUCTION_STATUS_TAG));
                for (Status status : Instruction.Status.values()) {
                    String selected = status.equals(instruction.getStatus()) ? "selected=\"selected\"" : "";
                    out.println(String.format("<option %s>%s</option>", selected, status));
                }
                out.println("</select></div>");
                out.println("<div><input type=\"submit\"/></div>");
                out.println("</form>");
                out.println("</li>");
            }
            out.println("</ol>");
        } catch (DAOException ex) {
            String errorMessage = BUNDLE.getString(DATABASE_CONN_ERROR);
            Logger.getLogger(AllPlantsTag.class).error(errorMessage, ex);
            request.setAttribute(WebTags.ERROR_MESSAGE_TAG, errorMessage);
            request.setAttribute(WebTags.ERROR_CODE_TAG, 500);
            try {
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            } catch (ServletException ex1) {
                Logger.getLogger(AllPlantsTag.class).error(null, ex1);
            }
        }
    }
}
