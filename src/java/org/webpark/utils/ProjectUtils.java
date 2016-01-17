/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Class stores some general methods that help through over the whole
 * application.
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

    /**
     * Method loads and constructs Properties object from some config file.
     *
     * @param confPath - location of config file
     * @return Properties object that represents config file
     * @throws IOException - if IO problems
     */
    public static final Properties loadProperties(String confPath) throws IOException {
        checkNotNull(confPath);

        Properties resultProp = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        resultProp.load(classLoader.getResourceAsStream(confPath));

        return resultProp;
    }
}
