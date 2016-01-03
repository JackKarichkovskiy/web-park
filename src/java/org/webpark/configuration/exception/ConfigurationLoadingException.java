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
public class ConfigurationLoadingException extends Exception {

    /**
     * Creates a new instance of <code>ConfigurationLoadingException</code>
     * without detail message.
     */
    public ConfigurationLoadingException() {
    }

    /**
     * Constructs an instance of <code>ConfigurationLoadingException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ConfigurationLoadingException(String msg) {
        super(msg);
    }

    public ConfigurationLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationLoadingException(Throwable cause) {
        super(cause);
    }

}
