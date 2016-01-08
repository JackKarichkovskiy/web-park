/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao;

import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.webpark.dao.exception.DaoFactoryNotFoundException;
import org.webpark.dao.exception.ObjectInstantiatingException;
import org.webpark.locale.AppBundleFactory;
import static org.webpark.utils.ProjectUtils.*;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public abstract class DaoFactory {

    private static final String DAO_TYPE_ERROR_TAG = "log.dao_type_error";
    private static final String DAO_TYPE_ABSENT_ERROR_TAG = "log.dao_type_absent_error";
    private static final String CLASS_NOT_FOUND_ERROR_TAG = "log.class_not_found_error";
    private static final String INSTANTIATING_OBJ_ERROR_TAG = "log.object_instantiating_error";
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();
    
    private static final DaoConfiguration DAO_PROP = DaoConfiguration.getInstance();

    public abstract CRUDDaoInterface getCRUDDao();

    public abstract UserDaoServiceInterface getUserDao();

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

    public static DaoFactory getInstance(String type) {
        checkNotNull(type);
        
        DaoType daoType;
        try {
            daoType = DaoType.valueOf(type);
        } catch (IllegalArgumentException ex) {
            String errorMessage = String.format(BUNDLE.getString(DAO_TYPE_ERROR_TAG), type);
            Logger.getLogger(DaoFactory.class).error(errorMessage, ex);
            return null;
        }
        
        return getInstance(daoType);
    }

    public static DaoFactory getInstance(DaoType type) {
        checkNotNull(type);

        String property = DAO_PROP.getProperty(type.getParamName());
        if (property == null) {
            String errorMessage = String.format(BUNDLE.getString(DAO_TYPE_ABSENT_ERROR_TAG), type);
            throw new DaoFactoryNotFoundException(errorMessage);
        }
        try {
            Class clazz = Class.forName(property);
            return (DaoFactory) clazz.newInstance();
        } catch (ClassNotFoundException ex) {
            String errorMessage = String.format(BUNDLE.getString(CLASS_NOT_FOUND_ERROR_TAG), property);
            throw new DaoFactoryNotFoundException(errorMessage, ex);
        } catch (InstantiationException | IllegalAccessException ex) {
            String errorMessage = String.format(BUNDLE.getString(INSTANTIATING_OBJ_ERROR_TAG), property);
            Logger.getLogger(DaoFactory.class).error(errorMessage, new ObjectInstantiatingException(ex));
        }

        return null;
    }
}
