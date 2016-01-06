/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command.concrete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.webpark.controller.command.Command;
import org.webpark.controller.command.CommandResult;
import org.webpark.controller.command.RolesAllowed;
import org.webpark.controller.command.WebTags;
import static org.webpark.controller.command.WebTags.USER_TAG;
import org.webpark.controller.session.SessionStorage;
import org.webpark.controller.uri.UriBuilder;
import org.webpark.dao.AppDaoFactory;
import org.webpark.dao.entities.User;
import org.webpark.dao.exception.DAOException;

/**
 *
 * @author Karichkovskiy Yevhen
 */
@RolesAllowed(User.Roles.GUEST)
class LogInCommand implements Command{
    
    private static final String ERROR_PAGE = UriBuilder.getUri("error_page");
    private static final String ALL_PLANT_PAGE = UriBuilder.getUri("all_plants");
    
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        
        String username = request.getParameter(WebTags.USERNAME_TAG);
        String password = request.getParameter(WebTags.PASSWORD_TAG);
        if(username == null || password == null){
            return new CommandResult(null, CommandResult.JumpType.IGNORE);
        }
        
        User user;
        try {
            user = AppDaoFactory.getInstance().getUserDao().getUserByUsernameAndPassword(username, password);
        } catch (DAOException ex) {
            Logger.getLogger(LogInCommand.class).error(null, ex);
            return new CommandResult(ERROR_PAGE, CommandResult.JumpType.FORWARD);
        }
        
        if(user != null){
            HttpSession session = request.getSession(true);
            SessionStorage.getInstance().addSession(session);
            session.setAttribute(USER_TAG, user);
            return new CommandResult(ALL_PLANT_PAGE, CommandResult.JumpType.REDIRECT);
        }else{
            return new CommandResult(null, CommandResult.JumpType.IGNORE);
        }
    }
    
}
