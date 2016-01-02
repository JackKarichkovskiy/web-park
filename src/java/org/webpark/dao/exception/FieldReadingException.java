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
public class FieldReadingException extends Exception {

    /**
     * Creates a new instance of <code>FieldReadingException</code> without
     * detail message.
     */
    public FieldReadingException() {
    }

    /**
     * Constructs an instance of <code>FieldReadingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FieldReadingException(String msg) {
        super(msg);
    }

    public FieldReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldReadingException(Throwable cause) {
        super(cause);
    }
    
    
}
