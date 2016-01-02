/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.exception;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class PrimaryKeyInClassNotFoundException extends Exception{

    public PrimaryKeyInClassNotFoundException() {
    }

    public PrimaryKeyInClassNotFoundException(String message) {
        super(message);
    }

    public PrimaryKeyInClassNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrimaryKeyInClassNotFoundException(Throwable cause) {
        super(cause);
    }

    public PrimaryKeyInClassNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
