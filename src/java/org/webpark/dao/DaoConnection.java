/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            // Look up our data source
            ds = (DataSource) envCtx.lookup("jdbc/WebPark");
        } catch (NamingException ex) {
            Logger.getLogger(DaoConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnection() throws SQLException{
        return ds.getConnection();
    }
}
