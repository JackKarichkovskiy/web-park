/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.tag;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.log4j.Logger;
import org.webpark.controller.command.CommandResult;
import org.webpark.controller.command.WebTags;
import org.webpark.controller.uri.UriBuilder;
import org.webpark.dao.AppDaoFactory;
import org.webpark.dao.InstructionDaoServiceInterface;
import org.webpark.dao.entities.Instruction;
import org.webpark.dao.entities.InstructionStep.Status;
import org.webpark.dao.entities.User;
import org.webpark.dao.exception.DAOException;
import org.webpark.dao.mysql.instruction.MySQLInstructionDaoService;
import org.webpark.locale.AppBundleFactory;
import org.webpark.locale.Language;

/**
 * Class realizes custom jsp tag 'foresterTasks'.
 *
 * @author Karichkovskiy Yevhen
 */
public class ForesterTasksTag extends SimpleTagSupport {

    /**
     * Error message tag for some database operation problems.
     */
    private static final String DATABASE_CONN_ERROR = "log.database_conn_error";

    /**
     * Application standard locale bundle.
     */
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();

    /**
     * URI that refers to error page.
     */
    private static final String ERROR_PAGE = UriBuilder.getUri("error_page", CommandResult.JumpType.FORWARD);

    /**
     * HTTP error code status if some problems occurs.
     */
    private static final int ERROR_CODE = 500;

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(WebTags.SESSION_USER_TAG);

        Locale curLocale = (Locale) Config.get(session, Config.FMT_LOCALE);
        Language sessionLang = Language.getLanguageByLocale(curLocale);
        ResourceBundle sessionBundle = AppBundleFactory.getInstance().createBundle(sessionLang);

        InstructionDaoServiceInterface instructionDao = AppDaoFactory.getInstance().getInstructionDao();

        List<Instruction> allForesterInstructions;
        try {
            allForesterInstructions = instructionDao.getAllNotDoneInstructionsByForester(user.getId().toString());

            JspWriter out = getJspContext().getOut();
            out.println("<ol>");
            for (Instruction instruction : allForesterInstructions) {
                out.println("<li>");
                out.println("<form method=\"POST\" action=\"/WebPark/Controller?command=updateInstructionStatuses\" accept-charset=\"UTF-8\">");
                out.println(String.format("<input type=\"hidden\" value=\"%s\" name=\"%s\"/>", instruction.getId(), WebTags.INSTRUCTION_ID_TAG));
                out.println(String.format("<p>%s: %s</p>", sessionBundle.getString(LocaleKeys.TITLE), instruction.getTitle()));
                out.println(String.format("<p>%s:</p>", sessionBundle.getString(LocaleKeys.TASKS)));
                Map<String, List<Object>> allStepsInInstruction = instructionDao.getAllStepsInInstruction(instruction.getId().toString());
                out.println("<ul>");
                for (int i = 0; i < allStepsInInstruction.get(MySQLInstructionDaoService.GetAllStepsInInstructionResultTags.STEP_ID).size(); i++) {
                    String stepStatusStr = (String) allStepsInInstruction.
                            get(MySQLInstructionDaoService.GetAllStepsInInstructionResultTags.STEP_STATUS).get(i);
                    Status stepStatus = Status.valueOf(stepStatusStr);
                    if (!stepStatus.equals(Status.DONE_VERIFIED)) {
                        out.println("<li>");
                        out.println(String.format("<div><input type=\"hidden\" value=\"%s\" name=\"%s\"/></div>",
                                allStepsInInstruction.get(MySQLInstructionDaoService.GetAllStepsInInstructionResultTags.STEP_ID).get(i),
                                WebTags.INSTRUCTION_STEP_ID_TAG));
                        out.println(String.format("<div>%s: %s</div>",
                                sessionBundle.getString(LocaleKeys.STEP_PLANT),
                                allStepsInInstruction.get(MySQLInstructionDaoService.GetAllStepsInInstructionResultTags.PLANT_NAME).get(i)));
                        out.println(String.format("<div>%s: %s</div>",
                                sessionBundle.getString(LocaleKeys.STEP_TASK),
                                allStepsInInstruction.get(MySQLInstructionDaoService.GetAllStepsInInstructionResultTags.STEP_TASK).get(i)));
                        String report = (String) allStepsInInstruction.get(MySQLInstructionDaoService.GetAllStepsInInstructionResultTags.STEP_REPORT).get(i);
                        String notNullReport = report != null ? report : "";
                        out.println(String.format("<div>%s: <input type=\"text\" value=\"%s\" name=\"%s\"/></div>",
                                sessionBundle.getString(LocaleKeys.STEP_REPORT),
                                notNullReport,
                                WebTags.INSTRUCTION_STEP_REPORT_TAG));
                        out.println(String.format("<div>%s: <select name=\"%s\">",
                                sessionBundle.getString(LocaleKeys.STEP_STATUS),
                                WebTags.INSTRUCTION_STEP_STATUS_TAG));
                        for (Status status : Status.values()) {
                            if (status.equals(Status.DONE_VERIFIED)) {
                                continue;
                            }
                            String selected = status.equals(stepStatus) ? "selected=\"selected\"" : "";
                            out.println(String.format("<option %s value=\"%s\">%s</option>",
                                    selected,
                                    status,
                                    sessionBundle.getString(status.getLocaleKey())));
                        }
                        out.println("</select></div>");
                        out.println("</li>");
                    }
                }
                out.println("</ul>");
                out.println(String.format("<div>%s: <select name=\"%s\">",
                        sessionBundle.getString(LocaleKeys.STATUS),
                        WebTags.INSTRUCTION_STATUS_TAG));
                for (Instruction.Status status : Instruction.Status.values()) {
                    if (status.equals(Instruction.Status.DONE_VERIFIED)) {
                        continue;
                    }
                    String selected = status.equals(instruction.getStatus()) ? "selected=\"selected\"" : "";
                    out.println(String.format("<option %s value=\"%s\">%s</option>",
                            selected,
                            status,
                            sessionBundle.getString(status.getLocaleKey())));
                }
                out.println("</select></div>");
                out.println(String.format("<div><input type=\"submit\" value=\"%s\"/></div>", sessionBundle.getString(LocaleKeys.SUBMIT)));
                out.println("</form>");
                out.println("</li>");
            }
            out.println("</ol>");
        } catch (DAOException ex) {
            String errorMessage = BUNDLE.getString(DATABASE_CONN_ERROR);
            Logger.getLogger(AllPlantsTag.class).error(errorMessage, ex);
            request.setAttribute(WebTags.ERROR_MESSAGE_TAG, errorMessage);
            request.setAttribute(WebTags.ERROR_CODE_TAG, ERROR_CODE);
            try {
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            } catch (ServletException ex1) {
                Logger.getLogger(AllPlantsTag.class).error(null, ex1);
            }
        }
    }

    /**
     * Keys in locale properties file.
     */
    private interface LocaleKeys {

        String TITLE = "forester_tasks_tag.title";
        String TASKS = "forester_tasks_tag.tasks";
        String STEP_PLANT = "forester_tasks_tag.step_plant";
        String STEP_TASK = "forester_tasks_tag.step_task";
        String STEP_REPORT = "forester_tasks_tag.step_report";
        String STEP_STATUS = "forester_tasks_tag.step_status";
        String STATUS = "forester_tasks_tag.status";
        String SUBMIT = "forester_tasks_tag.submit";
    }
}
