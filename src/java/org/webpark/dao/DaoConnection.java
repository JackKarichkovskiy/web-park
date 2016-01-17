/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.webpark.locale.AppBundleFactory;

/**
 * This class works with pool connection and produces database connections to
 * clients.
 *
 * @author Karichkovskiy Yevhen
 */
public class DaoConnection {

    /**
     * Singleton instance.
     */
    private static DaoConnection instance;

    /**
     * Error message for jdbc pool lookup problems.
     */
    private static final String JDBC_POOL_LOOKUP_ERROR_TAG = "log.pool_lookup_error";

    /**
     * Application standard locale bundle.
     */
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();

    /**
     * Tag of environment(for JNDI).
     */
    private static final String ENV_PARAM = "jndi.env";

    /**
     * Tag of database pool connection resource(for JNDI).
     */
    private static final String JNDI_JDBC_PARAM = "jndi.jdbc";

    /**
     * DAO configuration object.
     */
    private final DaoConfiguration daoConf = DaoConfiguration.getInstance();

    /**
     * Pool of connections.
     */
    private DataSource ds;

    /**
     * Returns Singleton instance.
     *
     * @return Singleton instance
     */
    public static DaoConnection getInstance() {
        if (instance == null) {
            instance = new DaoConnection("");
        }
        return instance;
    }

    /**
     * Constructor which look up the pool connection with JNDI.
     */
    private DaoConnection(String dummyStr) {
        try {
            InitialContext initCtx = new InitialContext();

            Context envCtx = (Context) initCtx.lookup(daoConf.getProperty(ENV_PARAM));
            // Look up our jdbc pool data source
            ds = (DataSource) envCtx.lookup(daoConf.getProperty(JNDI_JDBC_PARAM));
        } catch (NamingException ex) {
            String errorMessage = BUNDLE.getString(JDBC_POOL_LOOKUP_ERROR_TAG);
            Logger.getLogger(DaoConnection.class).error(errorMessage, ex);
        }
    }

    /**
     * Returns connection from pool.
     *
     * @return jdbc connection
     * @throws SQLException - if some database problems
     */
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
