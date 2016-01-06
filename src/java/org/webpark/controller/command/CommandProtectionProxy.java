/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.webpark.controller.uri.UriBuilder;
import org.webpark.dao.entities.User;
import org.webpark.dao.entities.User.Roles;
import static org.webpark.utils.ProjectUtils.checkNotNull;

/**
 *
 * @author Karichkovskiy Yevhen
 */
class CommandProtectionProxy implements Command {

    private static final String ACCESS_PAGE_TAG = "access_denied_page";
    private static final String ACCESS_PAGE = UriBuilder.getUri(ACCESS_PAGE_TAG);
    private static final Class<RolesAllowed> SECURE_ANNO_CLASS = RolesAllowed.class;

    private Command command;
    private Class<? extends Command> commandClass;

    public CommandProtectionProxy(Command command) {
        this.command = checkNotNull(command);
        this.commandClass = command.getClass();
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user;
        if (session == null || session.isNew() ||
                (user = (User) session.getAttribute(WebTags.USER_TAG)) == null) {
            session = request.getSession();
            user = new User();
            user.setRole(Roles.GUEST);
            session.setAttribute(WebTags.USER_TAG, user);
            Logger.getLogger(CommandProtectionProxy.class).info("New GUEST try to execute command " + command.getClass().getSimpleName());
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
