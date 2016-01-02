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
public class EntityNotStoredException extends Exception {

    /**
     * Creates a new instance of <code>EntityNotStoredException</code> without
     * detail message.
     */
    public EntityNotStoredException() {
    }

    /**
     * Constructs an instance of <code>EntityNotStoredException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EntityNotStoredException(String msg) {
        super(msg);
    }

    public EntityNotStoredException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotStoredException(Throwable cause) {
        super(cause);
    }

}
