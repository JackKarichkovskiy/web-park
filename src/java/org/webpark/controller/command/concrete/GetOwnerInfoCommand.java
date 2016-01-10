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

/**
 *
 * @author Karichkovskiy Yevhen
 */
class GetOwnerInfoCommand implements Command{

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
    
}
