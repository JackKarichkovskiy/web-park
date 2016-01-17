/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface that represents Command in pattern 'Command'.
 *
 * @author Karichkovskiy Yevhen
 */
public interface Command {

    /**
     * Method represents the realization of command.
     *
     * @param request - request from client
     * @param response - response to client
     * @return object that contains next page and method of transition type
     */
    CommandResult execute(HttpServletRequest request, HttpServletResponse response);
}
