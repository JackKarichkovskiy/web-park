/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.exception;

/**
 * Exception occurs when some problems with reflection writing to field.
 *
 * @author Karichkovskiy Yevhen
 */
public class FieldSettingException extends Exception {

    /**
     * Creates a new instance of <code>FieldSettingException</code> without
     * detail message.
     */
    public FieldSettingException() {
    }

    /**
     * Constructs an instance of <code>FieldSettingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FieldSettingException(String msg) {
        super(msg);
    }

    public FieldSettingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldSettingException(Throwable cause) {
        super(cause);
    }

}
