/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.configuration;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class AppConfiguration extends AbstractConfiguration{
    
    private static final String DEFAULT_APP_CONF_PATH = "app.properties";
    
    private AppConfiguration(String confFile) {
        super(confFile);
    }
    
    public static AppConfiguration getInstance() {
        return AppConfigurationHolder.INSTANCE;
    }
    
    private static class AppConfigurationHolder {

        private static final AppConfiguration INSTANCE = new AppConfiguration(DEFAULT_APP_CONF_PATH);
    }
}
