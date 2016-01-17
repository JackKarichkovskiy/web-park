/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command.concrete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.webpark.controller.command.Command;
import org.webpark.controller.command.CommandResult;
import org.webpark.controller.uri.UriBuilder;

/**
 * Command that log out a user.
 *
 * @author Karichkovskiy Yevhen
 */
class LogOutCommand implements Command {

    /**
     * URI that refers to index page.
     */
    private static final String INIT_PAGE = UriBuilder.getUri("init_page", CommandResult.JumpType.REDIRECT);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return new CommandResult(INIT_PAGE, CommandResult.JumpType.REDIRECT);
    }

}
