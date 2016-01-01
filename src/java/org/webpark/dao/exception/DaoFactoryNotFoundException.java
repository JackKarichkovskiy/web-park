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
public class DaoFactoryNotFoundException extends RuntimeException{

    public DaoFactoryNotFoundException(String message) {
        super(message);
    }

    public DaoFactoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoFactoryNotFoundException(Throwable cause) {
        super(cause);
    }
    
}
