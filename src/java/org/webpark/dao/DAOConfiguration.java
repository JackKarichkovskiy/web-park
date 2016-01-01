/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class DAOConfiguration {

    private static final String DEFAULT_CONF_PATH = "dao.properties";

    private static DAOConfiguration instance;

    public static DAOConfiguration getInstance() {
        if (instance == null) {
            instance = new DAOConfiguration(DEFAULT_CONF_PATH);
        }
        return instance;
    }

    private Properties daoProp;

    public DAOConfiguration(String confPath) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        daoProp = new Properties();
        try {
            daoProp.load(classLoader.getResourceAsStream(confPath));
        } catch (IOException ex) {
            Logger.getLogger(DAOConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Properties getDaoProp() {
        return daoProp;
    }
}