/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command.concrete;

import org.webpark.controller.command.Command;

/**
 * Enumeration that contains all commands available to application.
 *
 * @author Karichkovskiy Yevhen
 */
public enum Commands {

    LOG_IN("logIn", new LogInCommand()),
    LOG_OUT("logOut", new LogOutCommand()),
    CHANGE_LANGUAGE("changeLang", new ChangeLanguageCommand()),
    ADD_NEW_INSTRUCTION("addInstruction", new AddNewInstructionCommand()),
    UPDATE_INSTRUCTION_STATUSES("updateInstructionStatuses", new UpdateStatusesOfInstructionCommand()),
    UPDATE_NOT_CONFIRMED_INSTRUCTION_STATUSES("updateNotConfirmedInstructionStatuses", new UpdateNotConfirmedInstructionCommand()),
    ;
    
    /**
     * Name of command that used in Controller to identify command.
     */
    private final String commandName;

    /**
     * Realization of command.
     */
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
