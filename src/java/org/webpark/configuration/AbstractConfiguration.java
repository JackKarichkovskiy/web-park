/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.configuration;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.webpark.configuration.exception.ConfigurationLoadingException;
import org.webpark.dao.DaoConfiguration;
import org.webpark.utils.ProjectUtils;

/**
 * Abstract class that loads some configuration file.
 *
 * @author Karichkovskiy Yevhen
 */
public abstract class AbstractConfiguration implements Configuration {

    /**
     * Error detail message.
     */
    private static final String ERROR_CONF_LOAD = "Some problems with loading config file %s";

    /**
     * Property file object.
     */
    protected Properties daoProp;

    /**
     * Constructor for loading a configuration from config path.
     *
     * @param confPath - path of config file
     */
    protected AbstractConfiguration(String confPath) {
        try {
            daoProp = ProjectUtils.loadProperties(confPath);
        } catch (IOException ex) {
            Logger.getLogger(DaoConfiguration.class).
                    error(String.format(ERROR_CONF_LOAD, confPath),
                            new ConfigurationLoadingException(ex));
        }
    }

    @Override
    public String getProperty(String propName) {
        return daoProp.getProperty(propName);
    }
}
