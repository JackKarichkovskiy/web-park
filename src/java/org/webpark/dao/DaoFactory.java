/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.webpark.dao.exception.DaoFactoryNotFoundException;
import static org.webpark.utils.ProjectUtils.*;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public abstract class DaoFactory {

    private static final DaoConfiguration DAO_PROP = DaoConfiguration.getInstance();

    public abstract CRUDDaoInterface getCRUDDao();
    
    public enum DaoType {

        MYSQL("factory.mysql");

        private final String paramName;

        DaoType(String paramName) {
            this.paramName = paramName;
        }

        public String getParamName() {
            return paramName;
        }
    }

    public static DaoFactory getInstance(DaoType type) {
        checkNotNull(type);

        String property = DAO_PROP.getProperty(type.getParamName());
        if (property == null) {
            throw new DaoFactoryNotFoundException("This dao type is absent in dao properties file");
        }
        try {
            Class clazz = Class.forName(property);
            return (DaoFactory) clazz.newInstance();
        } catch (ClassNotFoundException ex) {
            throw new DaoFactoryNotFoundException("Cannot find class " + property + " in classpath", ex);
        } catch (Exception ex) {
            Logger.getLogger(DaoFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
