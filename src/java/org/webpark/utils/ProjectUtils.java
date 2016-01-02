/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.webpark.dao.DaoConfiguration;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class ProjectUtils {

    /**
     * Method checks the object by null equality.
     *
     * @param <T> - type of object
     * @param obj - object that needs to be checked
     * @return the origin value
     * @throws IllegalArgumentException if (object == null)
     */
    public static final <T> T checkNotNull(T obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }

        return obj;
    }

    public static final Properties loadProperties(String confPath) throws IOException {
        checkNotNull(confPath);
        
        Properties resultProp = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        resultProp.load(classLoader.getResourceAsStream(confPath));

        return resultProp;
    }
}
