/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command.concrete;

import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.webpark.configuration.AppConfiguration;
import org.webpark.controller.command.Command;
import org.webpark.controller.command.CommandResult;
import org.webpark.controller.command.RolesAllowed;
import org.webpark.controller.command.WebTags;
import static org.webpark.controller.command.WebTags.SESSION_USER_TAG;
import org.webpark.controller.uri.UriBuilder;
import org.webpark.dao.AppDaoFactory;
import org.webpark.dao.entities.User;
import org.webpark.dao.exception.DAOException;
import org.webpark.locale.AppBundleFactory;

/**
 * Command that log in a user.
 *
 * @author Karichkovskiy Yevhen
 */
@RolesAllowed(User.Roles.GUEST)
class LogInCommand implements Command {

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

    /**
     * URI that refers to index page.
     */
    private static final String INIT_PAGE = UriBuilder.getUri("init_page", CommandResult.JumpType.REDIRECT);

    /**
     * Tag-key to app config file to session timeout value.
     */
    private static final String SESSION_TIMEOUT_TAG = "session_timeout";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter(WebTags.USERNAME_TAG);
        String password = request.getParameter(WebTags.PASSWORD_TAG);
        if (username == null || password == null) {
            return new CommandResult(null, CommandResult.JumpType.IGNORE);
        }

        User user;
        try {
            user = AppDaoFactory.getInstance().getUserDao().getUserByUsernameAndPassword(username, password);
        } catch (DAOException ex) {
            String errorMessage = BUNDLE.getString(DATABASE_CONN_ERROR);
            Logger.getLogger(LogInCommand.class).error(errorMessage, ex);
            request.setAttribute(WebTags.ERROR_MESSAGE_TAG, errorMessage);
            request.setAttribute(WebTags.ERROR_CODE_TAG, ERROR_CODE);
            return new CommandResult(ERROR_PAGE, CommandResult.JumpType.FORWARD);
        }

        if (user != null) {
            HttpSession session = request.getSession(true);
            setSessionTimeout(session);
            session.setAttribute(SESSION_USER_TAG, user);
            return new CommandResult(ACCOUNT_PAGE, CommandResult.JumpType.REDIRECT);
        } else {
            return new CommandResult(INIT_PAGE, CommandResult.JumpType.REDIRECT);
        }
    }

    /**
     * Method set up the session max inactive time after what session would
     * become invalidate.
     *
     * @param session - current session
     * @throws NumberFormatException - if timeout value from config file isn't
     * number
     */
    private void setSessionTimeout(HttpSession session) throws NumberFormatException {
        String timeout = AppConfiguration.getInstance().getProperty(SESSION_TIMEOUT_TAG);
        if (timeout != null) {
            session.setMaxInactiveInterval(Integer.valueOf(timeout));
        }
    }

}
