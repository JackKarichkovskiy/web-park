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
public class ObjectInstantiatingException extends Exception {

    /**
     * Creates a new instance of <code>ObjectInstantiatingException</code>
     * without detail message.
     */
    public ObjectInstantiatingException() {
    }

    /**
     * Constructs an instance of <code>ObjectInstantiatingException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ObjectInstantiatingException(String msg) {
        super(msg);
    }

    public ObjectInstantiatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectInstantiatingException(Throwable cause) {
        super(cause);
    }
    
    
}
