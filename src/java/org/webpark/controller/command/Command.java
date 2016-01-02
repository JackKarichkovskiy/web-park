/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public interface Command {

    CommandResult execute(HttpServletRequest request, HttpServletResponse response);
}
