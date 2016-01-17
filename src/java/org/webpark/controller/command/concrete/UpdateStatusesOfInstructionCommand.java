/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command.concrete;

import java.util.ResourceBundle;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.webpark.controller.command.Command;
import org.webpark.controller.command.CommandResult;
import org.webpark.controller.command.RolesAllowed;
import org.webpark.controller.command.WebTags;
import org.webpark.controller.uri.UriBuilder;
import org.webpark.dao.AppDaoFactory;
import org.webpark.dao.entities.Instruction;
import org.webpark.dao.entities.InstructionStep;
import org.webpark.dao.entities.User;
import org.webpark.dao.exception.DAOException;
import org.webpark.locale.AppBundleFactory;

/**
 * Command that updates statuses of instruction and its tasks by forester.
 *
 * @author Karichkovskiy Yevhen
 */
@RolesAllowed(User.Roles.FORESTER)
public class UpdateStatusesOfInstructionCommand implements Command {

    /**
     * Error message for some database operation problems.
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
     * HTTP error status code if some problems occurs.
     */
    private static final int ERROR_CODE = 500;
    
    /**
     * URI that refers to account page.
     */
    private static final String ACCOUNT_PAGE = UriBuilder.getUri("account_page", CommandResult.JumpType.REDIRECT);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String instructionIdStr = request.getParameter(WebTags.INSTRUCTION_ID_TAG);
        String instructionStatusStr = request.getParameter(WebTags.INSTRUCTION_STATUS_TAG);
        Instruction inst = new Instruction(UUID.fromString(instructionIdStr), null, null, null, Instruction.Status.valueOf(instructionStatusStr));

        String[] stepIdsStr = request.getParameterValues(WebTags.INSTRUCTION_STEP_ID_TAG);
        String[] stepReportsStr = request.getParameterValues(WebTags.INSTRUCTION_STEP_REPORT_TAG);
        String[] stepStatusesStr = request.getParameterValues(WebTags.INSTRUCTION_STEP_STATUS_TAG);
        InstructionStep[] steps = new InstructionStep[stepIdsStr.length];
        for (int i = 0; i < steps.length; i++) {
            steps[i] = new InstructionStep(UUID.fromString(stepIdsStr[i]),
                    null,
                    null,
                    null,
                    stepReportsStr[i],
                    InstructionStep.Status.valueOf(stepStatusesStr[i]));
        }

        try {
            AppDaoFactory.getInstance().getInstructionDao().updateInstructionStatuses(inst, steps);
        } catch (DAOException ex) {
            String errorMessage = BUNDLE.getString(DATABASE_CONN_ERROR);
            Logger.getLogger(UpdateStatusesOfInstructionCommand.class).error(errorMessage, ex);
            request.setAttribute(WebTags.ERROR_MESSAGE_TAG, errorMessage);
            request.setAttribute(WebTags.ERROR_CODE_TAG, ERROR_CODE);
            return new CommandResult(ERROR_PAGE, CommandResult.JumpType.FORWARD);
        }

        return new CommandResult(ACCOUNT_PAGE, CommandResult.JumpType.REDIRECT);
    }

}
