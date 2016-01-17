/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao;

import org.webpark.configuration.AppConfiguration;

/**
 * Standard DAO factory for this application defined in app config file.
 *
 * @author Karichkovskiy Yevhen
 */
public class AppDaoFactory {

    /**
     * DAO factory type tag in app config file.
     */
    private static final String FACTORY_TYPE_TAG = "db.name";

    /**
     * DAO factory type name read from app config file.
     */
    private static final String FACTORY_TYPE = AppConfiguration.getInstance().getProperty(FACTORY_TYPE_TAG);

    private AppDaoFactory() {
    }

    /**
     * Returns Singleton instance.
     *
     * @return Singleton instance
     */
    public static DaoFactory getInstance() {
        return AppDaoFactoryHolder.INSTANCE;
    }

    private static class AppDaoFactoryHolder {

        private static final DaoFactory INSTANCE = DaoFactory.getInstance(FACTORY_TYPE);
    }
}
