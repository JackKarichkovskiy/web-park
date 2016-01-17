/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.mysql;

import org.webpark.configuration.AbstractConfiguration;

/**
 * Configuration that stores sql queries for mysql database.
 *
 * @author Karichkovskiy Yevhen
 */
public class MySQLDaoConfiguration extends AbstractConfiguration {

    /**
     * Default location of mysql dao config file.
     */
    private static final String DEFAULT_CONF_PATH = "mysql/dao.properties";

    private MySQLDaoConfiguration(String confPath) {
        super(confPath);
    }

    public static MySQLDaoConfiguration getInstance() {
        return MySQLDaoConfigurationHolder.INSTANCE;
    }

    private static class MySQLDaoConfigurationHolder {

        private static final MySQLDaoConfiguration INSTANCE = new MySQLDaoConfiguration(DEFAULT_CONF_PATH);

    }
}
