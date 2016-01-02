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
public class FieldNotStoredException extends Exception {

    /**
     * Creates a new instance of <code>FieldNotStoredException</code> without
     * detail message.
     */
    public FieldNotStoredException() {
    }

    /**
     * Constructs an instance of <code>FieldNotStoredException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FieldNotStoredException(String msg) {
        super(msg);
    }

    public FieldNotStoredException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldNotStoredException(Throwable cause) {
        super(cause);
    }
}
