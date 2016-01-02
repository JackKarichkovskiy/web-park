/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class CommandResult {
    private String nextPage;
    
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
    
    public enum JumpType{
        FORWARD, REDIRECT, IGNORE;
    }
    
}
