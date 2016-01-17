/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.configuration;

/**
 * Interface that represents Configuration.
 *
 * @author Karichkovskiy Yevhen
 */
public interface Configuration {
    
    /**
     * Method returns property value by its key.
     * @param propName - some key in config file
     * @return property value from config file
     */
    String getProperty(String propName);
}
