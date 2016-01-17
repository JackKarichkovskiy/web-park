/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command;

/**
 * Answer from Command.execute method to identify how and where its needs to be
 * redirected after executing the Command.
 *
 * @author Karichkovskiy Yevhen
 */
public class CommandResult {

    /**
     * URI of the next page location.
     */
    private String nextPage;

    /**
     * Method of redirecting.
     */
    private JumpType jumpType;

    public CommandResult() {
    }

    public CommandResult(String nextPage, JumpType jumpType) {
        this.nextPage = nextPage;
        this.jumpType = jumpType;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public JumpType getJumpType() {
        return jumpType;
    }

    public void setJumpType(JumpType jumpType) {
        this.jumpType = jumpType;
    }

    /**
     * Enumeration that contains transition types.
     */
    public enum JumpType {

        FORWARD, REDIRECT, IGNORE;
    }

}
