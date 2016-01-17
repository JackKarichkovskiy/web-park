/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao;

import org.webpark.configuration.AbstractConfiguration;

/**
 * Basic DAO configuration.
 *
 * @author Karichkovskiy Yevhen
 */
public class DaoConfiguration extends AbstractConfiguration {

    /**
     * Location of dao config file.
     */
    private static final String DEFAULT_CONF_PATH = "dao.properties";

    /**
     * Returns Singleton instance.
     *
     * @return Singleton instance
     */
    public static DaoConfiguration getInstance() {
        return DaoConfigurationHolder.INSTANCE;
    }

    private DaoConfiguration(String confPath) {
        super(confPath);
    }

    private static class DaoConfigurationHolder {

        private static final DaoConfiguration INSTANCE = new DaoConfiguration(DEFAULT_CONF_PATH);
    }
}
