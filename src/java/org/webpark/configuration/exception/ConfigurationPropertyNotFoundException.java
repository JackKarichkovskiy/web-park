/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.configuration.exception;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class ConfigurationPropertyNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>ConfigurationPropertyNotFoundException</code> without detail
     * message.
     */
    public ConfigurationPropertyNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>ConfigurationPropertyNotFoundException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ConfigurationPropertyNotFoundException(String msg) {
        super(msg);
    }

    public ConfigurationPropertyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationPropertyNotFoundException(Throwable cause) {
        super(cause);
    }
    
    
}
