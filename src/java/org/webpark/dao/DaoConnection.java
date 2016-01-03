/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class DaoConnection {
    private static DaoConnection instance;
    
    private static final String ENV_PARAM = "jndi.env";
    private static final String JNDI_JDBC_PARAM = "jndi.jdbc";
    
    private final DaoConfiguration daoConf = DaoConfiguration.getInstance();
    
    public static DaoConnection getInstance(){
        if( instance == null ){
            instance = new DaoConnection();
        }
        return instance;
    }
    
    private DataSource ds;
    
    private DaoConnection(){
        try {
            InitialContext initCtx = new InitialContext();

            Context envCtx = (Context) initCtx.lookup(daoConf.getProperty(ENV_PARAM));
            // Look up our jdbc pool data source
            ds = (DataSource) envCtx.lookup(daoConf.getProperty(JNDI_JDBC_PARAM));
        } catch (NamingException ex) {
            Logger.getLogger(DaoConnection.class).error(null, ex);
        }
    }
    
    public Connection getConnection() throws SQLException{
        return ds.getConnection();
    }
}
