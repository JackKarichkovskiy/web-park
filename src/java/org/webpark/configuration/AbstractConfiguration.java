/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.configuration;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.webpark.configuration.exception.ConfigurationLoadingException;
import org.webpark.dao.DaoConfiguration;
import org.webpark.utils.ProjectUtils;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public abstract class AbstractConfiguration implements Configuration{
    
    protected Properties daoProp;
    
    protected AbstractConfiguration(String confPath){
        try {
            daoProp = ProjectUtils.loadProperties(confPath);
        } catch (IOException ex) {
            Logger.getLogger(DaoConfiguration.class).error(null, new ConfigurationLoadingException(ex));
        }
    }
    
    @Override
    public String getProperty(String propName) {
        return daoProp.getProperty(propName);
    }
}
