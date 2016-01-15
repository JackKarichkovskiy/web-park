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
import org.webpark.dao.InstructionDaoServiceInterface;
import org.webpark.dao.entities.Instruction;
import org.webpark.dao.entities.InstructionStep;
import org.webpark.dao.entities.User;
import org.webpark.dao.exception.DAOException;
import org.webpark.locale.AppBundleFactory;

/**
 *
 * @author Karichkovskiy Yevhen
 */
@RolesAllowed(User.Roles.OWNER)
public class AddNewInstructionCommand implements Command{
    
    private static final String PARAM_IS_NULL_ERROR = "log.request_param_is_null";
    private static final String DATABASE_CONN_ERROR = "log.database_conn_error";
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();
    private static final String ERROR_PAGE = UriBuilder.getUri("error_page", CommandResult.JumpType.FORWARD);
    private static final String ACCOUNT_PAGE = UriBuilder.getUri("account_page", CommandResult.JumpType.REDIRECT);
    
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String title = request.getParameter(WebTags.INSTRUCTION_TITLE_TAG);
        String createdBy = request.getParameter(WebTags.INSTRUCTION_CREATED_BY_TAG);
        String performedBy = request.getParameter(WebTags.PERFORMED_BY_TAG);
        String[] plants = request.getParameterValues(WebTags.TASK_PLANT_TAG);
        String[] tasks = request.getParameterValues(WebTags.TASK_TAG);
        String status = request.getParameter(WebTags.INSTRUCTION_STATUS_TAG);
        
        boolean anyParamIsEmpty = title == null || createdBy == null || performedBy == null
                || plants == null || tasks == null || status == null;
        if(anyParamIsEmpty){
            String errorMessage = BUNDLE.getString(PARAM_IS_NULL_ERROR);
            Logger.getLogger(LogInCommand.class).error(errorMessage);
            request.setAttribute(WebTags.ERROR_MESSAGE_TAG, errorMessage);
            request.setAttribute(WebTags.ERROR_CODE_TAG, 500);
            return new CommandResult(ERROR_PAGE, CommandResult.JumpType.FORWARD);
        }
        
        Instruction instruction = new Instruction();
        instruction.setId(UUID.randomUUID());
        instruction.setTitle(title);
        instruction.setCreatedBy(UUID.fromString(createdBy));
        instruction.setPerformedBy(UUID.fromString(performedBy));
        instruction.setStatus(Instruction.Status.valueOf(status));
        
        InstructionStep[] steps = new InstructionStep[plants.length];
        for (int i = 0; i < steps.length; i++) {
            InstructionStep step = new InstructionStep();
            step.setId(UUID.randomUUID());
            step.setPlantId(UUID.fromString(plants[i]));
            step.setInstructionId(instruction.getId());
            step.setTask(tasks[i]);
            step.setStatus(InstructionStep.Status.NEW);
            steps[i] = step;
        }
        
        InstructionDaoServiceInterface instructionDao = AppDaoFactory.getInstance().getInstructionDao();
        try {
            instructionDao.addNewInstruction(instruction, steps);
        } catch (DAOException ex) {
            String errorMessage = BUNDLE.getString(DATABASE_CONN_ERROR);
            Logger.getLogger(LogInCommand.class).error(errorMessage, ex);
            request.setAttribute(WebTags.ERROR_MESSAGE_TAG, errorMessage);
            request.setAttribute(WebTags.ERROR_CODE_TAG, 500);
            return new CommandResult(ERROR_PAGE, CommandResult.JumpType.FORWARD);
        }
        
        return new CommandResult(ACCOUNT_PAGE, CommandResult.JumpType.REDIRECT);
    }
    
}
