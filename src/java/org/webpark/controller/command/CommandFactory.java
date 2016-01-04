/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command;

import org.webpark.controller.command.concrete.Commands;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class CommandFactory {

    private static final CommandFactory INSTANCE = new CommandFactory(null);

    private static final String DEFAULT_PAGE = "index.html";
    
    private static final Command EMPTY_COMMAND = (request, response) 
            -> new CommandResult(DEFAULT_PAGE, CommandResult.JumpType.FORWARD);
    
    private static final Command IGNORE_COMMAND = (request, response) 
            -> new CommandResult(DEFAULT_PAGE, CommandResult.JumpType.IGNORE);

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    private CommandFactory(String dummyStr) {

    }

    public Command getCommand(String commandName) {
        if(commandName == null){
            return IGNORE_COMMAND;
        }
        
        for (Commands command : Commands.values()) {
            if (commandName.equals(command.getCommandName())) {
                return command.getCommand();
            }
        }
        
        return EMPTY_COMMAND;
    }

    public Command getCommand(Commands command) {
        return command.getCommand();
    }
}
