/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command;

import org.webpark.controller.command.concrete.Commands;
import org.webpark.controller.uri.UriBuilder;

/**
 * Factory that creates commands by its name.
 *
 * @author Karichkovskiy Yevhen
 */
public class CommandFactory {

    /**
     * Singleton instance.
     */
    private static final CommandFactory INSTANCE = new CommandFactory(null);

    /**
     * URI of index page.
     */
    private static final String INIT_PAGE = UriBuilder.getUri("init_page");

    /**
     * Object that represents Ignore Command that redirects to index page.
     */
    private static final Command IGNORE_COMMAND = (request, response)
            -> new CommandResult(INIT_PAGE, CommandResult.JumpType.REDIRECT);

    /**
     * Returns Singleton instance.
     *
     * @return Singleton instance
     */
    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    private CommandFactory(String dummyStr) {
    }

    /**
     * Method returns Command by its name.
     *
     * @param commandName name of command
     * @return Command object
     */
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

    /**
     * Returns command realization by its enumeration wrapper.
     *
     * @param command enumeration that represents Command
     * @return Command object
     */
    public Command getCommand(Commands command) {
        return command.getCommand();
    }
}
