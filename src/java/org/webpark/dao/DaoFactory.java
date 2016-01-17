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
 * Class represents factory for producing dao objects for different entities and
 * different targets.
 *
 * @author Karichkovskiy Yevhen
 */
public abstract class DaoFactory {

    /**
     * Error message when cannot convert dao type from configuration.
     */
    private static final String DAO_TYPE_ERROR_TAG = "log.dao_type_error";

    /**
     * Error message when dao type is absent in config file.
     */
    private static final String DAO_TYPE_ABSENT_ERROR_TAG = "log.dao_type_absent_error";

    /**
     * Error message when cannot find class of dao factory.
     */
    private static final String CLASS_NOT_FOUND_ERROR_TAG = "log.class_not_found_error";

    /**
     * Error message when cannot instantiate class with reflection.
     */
    private static final String INSTANTIATING_OBJ_ERROR_TAG = "log.object_instantiating_error";

    /**
     * Application standard locale bundle.
     */
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();

    /**
     * General configuration for DAO.
     */
    private static final DaoConfiguration DAO_PROP = DaoConfiguration.getInstance();

    /**
     * Returns dao object for general CRUD operations.
     *
     * @return crud dao object
     */
    public abstract CRUDDaoInterface getCRUDDao();

    /**
     * Returns dao object for specific work with user entity.
     *
     * @return user dao object
     */
    public abstract UserDaoServiceInterface getUserDao();

    /**
     * Returns dao object for specific work with instruction and its steps
     * entities.
     *
     * @return instruction dao object
     */
    public abstract InstructionDaoServiceInterface getInstructionDao();

    /**
     * Enumeration of available databases.
     */
    public enum DaoType {

        MYSQL("factory.mysql");

        /**
         * Key in dao config file which leads to realization class of factory.
         */
        private final String paramName;

        DaoType(String paramName) {
            this.paramName = paramName;
        }

        public String getParamName() {
            return paramName;
        }
    }

    /**
     * Returns factory by its name.
     *
     * @param type - name of enumeration of database
     * @return dao factory
     */
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

    /**
     * Returns factory by enumeration value.
     *
     * @param type - enumeration value of database
     * @return dao factory
     */
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
