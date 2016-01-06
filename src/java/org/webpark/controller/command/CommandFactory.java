/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command;

import org.webpark.controller.command.concrete.Commands;
import org.webpark.controller.uri.UriBuilder;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class CommandFactory {

    private static final CommandFactory INSTANCE = new CommandFactory(null);
    
    private static final String INIT_PAGE = UriBuilder.getUri("init_page");
    private static final Command IGNORE_COMMAND = (request, response)
            -> new CommandResult(INIT_PAGE, CommandResult.JumpType.REDIRECT);

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    private CommandFactory(String dummyStr) {

    }

    public Command getCommand(String commandName) {
        if (commandName == null) {
            return IGNORE_COMMAND;
        }

        for (Commands command : Commands.values()) {
            if (commandName.equals(command.getCommandName())) {
                return new CommandProtectionProxy(command.getCommand());
            }
        }

        return IGNORE_COMMAND;
    }

    public Command getCommand(Commands command) {
        return command.getCommand();
    }
}
