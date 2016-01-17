/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command;

import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.webpark.controller.uri.UriBuilder;
import org.webpark.dao.entities.User;
import org.webpark.dao.entities.User.Roles;
import org.webpark.locale.AppBundleFactory;
import static org.webpark.utils.ProjectUtils.checkNotNull;

/**
 * Proxy of Command object for protection from not allowed users of using.
 * In 'execute' method class identify if current user have access to real Command.
 *
 * @author Karichkovskiy Yevhen
 */
class CommandProtectionProxy implements Command {

    /**
     * Logging message if new user try to execute some command.
     */
    private static final String NEW_GUEST_TAG = "log.new_guest";
    
    /**
     * Application standard locale bundle.
     */
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();
    
    /**
     * URI tag to the page with access denied message.
     */
    private static final String ACCESS_PAGE = UriBuilder.getUri("access_denied_page");
    
    /**
     * Class of annotation for detection allowed roles for using the command.
     */
    private static final Class<RolesAllowed> SECURE_ANNO_CLASS = RolesAllowed.class;

    /**
     * Real Command object.
     */
    private Command command;
    
    /**
     * Class of real Command object.
     */
    private Class<? extends Command> commandClass;

    public CommandProtectionProxy(Command command) {
        this.command = checkNotNull(command);
        this.commandClass = command.getClass();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user;
        if (session == null || session.isNew()
                || (user = (User) session.getAttribute(WebTags.SESSION_USER_TAG)) == null) {
            session = request.getSession();
            user = new User();
            user.setRole(Roles.GUEST);
            session.setAttribute(WebTags.SESSION_USER_TAG, user);
            Logger.getLogger(CommandProtectionProxy.class).
                    info(String.format(BUNDLE.getString(NEW_GUEST_TAG),
                                    command.getClass().getSimpleName()));
        }

        if (!commandClass.isAnnotationPresent(SECURE_ANNO_CLASS)) {
            return command.execute(request, response);
        } else {
            RolesAllowed roleAnno = commandClass.getAnnotation(SECURE_ANNO_CLASS);
            Roles[] roles = roleAnno.value();
            for (Roles role : roles) {
                if (role.equals(user.getRole())) {
                    return command.execute(request, response);
                }
            }
            return new CommandResult(ACCESS_PAGE, CommandResult.JumpType.REDIRECT);
        }

    }

}
