/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command.concrete;

import org.webpark.controller.command.Command;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public enum Commands {
    GET_PLANTS_LIST("getPlants", new GetAllPlantsCommand()),
    LOG_IN("logIn", new LogInCommand()),
    ;
    
    private final String commandName;
    
    private final Command command;

    private Commands(String commandName, Command command) {
        this.command = command;
        this.commandName = commandName;
    }

    public Command getCommand() {
        return command;
    }

    public String getCommandName() {
        return commandName;
    }
    
}
