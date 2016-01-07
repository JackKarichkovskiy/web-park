/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.mysql;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.webpark.dao.CRUDDaoInterface;
import org.webpark.dao.DaoConnection;
import org.webpark.dao.annotation.utils.Converter;
import org.webpark.dao.annotation.utils.DAOUtils;
import org.webpark.configuration.exception.ConfigurationPropertyNotFoundException;
import org.webpark.dao.exception.DAOException;
import org.webpark.dao.exception.EntityNotStoredException;
import static org.webpark.utils.ProjectUtils.checkNotNull;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class MySQLDriver implements CRUDDaoInterface {

    private final MySQLDaoConfiguration daoConf = MySQLDaoConfiguration.getInstance();

    private final DaoConnection connHolder = DaoConnection.getInstance();

    private static MySQLDriver instance;

    private MySQLDriver(String dummyStr) {
    }

    public static MySQLDriver getInstance() {
        if (instance == null) {
            return new MySQLDriver(null);
        }

        return instance;
    }

    @Override
    public <T> T read(Class<T> entityClass, UUID id) throws DAOException {
        checkNotNull(entityClass);
        checkNotNull(id);

        String table = DAOUtils.defineTableName(entityClass);
        if (table == null) {
            throw new DAOException(new EntityNotStoredException());
        }

        Field primaryKey = DAOUtils.getPrimaryKey(entityClass);
        Converter pkConverter = primaryKey.getAnnotation(DAOUtils.STORED_ANNO_CLASS).converter().getConverter();
        String readQuery = daoConf.getProperty(Queries.READ_QUERY);
        if (readQuery == null) {
            throw new DAOException(new ConfigurationPropertyNotFoundException());
        }
        String query = String.format(readQuery, table, table, primaryKey.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name(), pkConverter.toString(id));
        Logger.getLogger(MySQLDriver.class).info(query);

        Connection connection = null;
        try {
            connection = connHolder.getConnection();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }

        ResultSet rs;
        try (Statement stmt = connection.createStatement()) {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                HashMap<String, Object> map = new HashMap<>();
                for (Field field : entityClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(DAOUtils.STORED_ANNO_CLASS)) {
                        map.put(field.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name(), rs.getString(field.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name()));
                    }
                }
                return DAOUtils.MapToEntity(entityClass, map);
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        return null;
    }

    @Override
    public <T> T insert(T instance) throws DAOException {
        checkNotNull(instance);

        //Defining the table
        String table = DAOUtils.defineTableName(instance.getClass());
        if (table == null) {
            throw new DAOException(new EntityNotStoredException());
        }

        //Forming the query
        String values = "";
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DAOUtils.STORED_ANNO_CLASS)) {
                values += DAOUtils.getConverter(field).toString(DAOUtils.getFieldValue(instance, field)) + ",";
            }
        }
        values = values.substring(0, values.length() - 1);
        String insertQuery = daoConf.getProperty(Queries.INSERT_QUERY);
        if (insertQuery == null) {
            throw new DAOException(new ConfigurationPropertyNotFoundException());
        }
        String query = String.format(insertQuery, table, values);

        Logger.getLogger(MySQLDriver.class).info(query);

        //Executing the query
        Connection connection = null;
        try {
            connection = connHolder.getConnection();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        return instance;
    }

    @Override
    public <T> void update(T instance) throws DAOException {
        checkNotNull(instance);

        String table = DAOUtils.defineTableName(instance.getClass());
        if (table == null) {
            throw new DAOException(new EntityNotStoredException());
        }

        //Формирование запроса
        String updatedValues = "";
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DAOUtils.STORED_ANNO_CLASS)
                    && (!field.isAnnotationPresent(DAOUtils.PRIMARY_KEY_ANNO_CLASS))) {
                String fieldName = field.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name();
                Converter fieldConverter = field.getAnnotation(DAOUtils.STORED_ANNO_CLASS).converter().getConverter();
                Object fieldValue = DAOUtils.getFieldValue(instance, field);
                String convertedFieldValue = fieldConverter.toString(fieldValue);
                updatedValues += String.format("%s=%s,", fieldName, convertedFieldValue);
            }
        }
        updatedValues = updatedValues.substring(0, updatedValues.length() - 1);

        Field primaryKey = DAOUtils.getPrimaryKey(instance.getClass());
        String pkName = primaryKey.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name();
        Converter pkConverter = primaryKey.getAnnotation(DAOUtils.STORED_ANNO_CLASS).converter().getConverter();
        Object pkValue = DAOUtils.getFieldValue(instance, primaryKey);
        String convertedPkValue = pkConverter.toString(pkValue);

        String updateQuery = daoConf.getProperty(Queries.UPDATE_QUERY);
        if (updateQuery == null) {
            throw new DAOException(new ConfigurationPropertyNotFoundException());
        }
        String query = String.format(updateQuery, table, updatedValues, table, pkName, convertedPkValue);

        Logger.getLogger(MySQLDriver.class).info(query);

        Connection connection = null;

        try {
            connection = connHolder.getConnection();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    @Override
    public <T> void delete(T instance) throws DAOException {
        checkNotNull(instance);

        //Определения таблицы
        String table = DAOUtils.defineTableName(instance.getClass());
        if (table == null) {
            throw new DAOException(new EntityNotStoredException());
        }

        Field primaryKey = DAOUtils.getPrimaryKey(instance.getClass());
        String pkName = primaryKey.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name();
        Converter pkConverter = primaryKey.getAnnotation(DAOUtils.STORED_ANNO_CLASS).converter().getConverter();
        Object pkValue = DAOUtils.getFieldValue(instance, primaryKey);
        String convertedPkValue = pkConverter.toString(pkValue);

        String deleteQuery = daoConf.getProperty(Queries.DELETE_QUERY);
        if (deleteQuery == null) {
            throw new DAOException(new ConfigurationPropertyNotFoundException());
        }
        String query = String.format(deleteQuery, table, pkName,
                convertedPkValue);
        Logger.getLogger(MySQLDriver.class).info(query);

        Connection connection = null;
        try {
            connection = connHolder.getConnection();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    @Override
    public <T> List<T> select(Class< T> entityClass, String sqlString) throws DAOException {
        checkNotNull(entityClass);
        checkNotNull(sqlString);

        List<T> resultList = new ArrayList<>();

        Connection connection = null;
        try {
            connection = connHolder.getConnection();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlString);
            resultList = DAOUtils.ResultSetToEntityArray(entityClass, rs);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        return resultList;
    }

    @Override
    public <T> List<T> getAllEntities(Class<T> entityClass) throws DAOException {
        checkNotNull(entityClass);
        
        List<T> resultList = new ArrayList<>();

        //Определения таблицы
        String table = DAOUtils.defineTableName(entityClass);
        if (table == null) {
            throw new DAOException(new EntityNotStoredException());
        }
        
        String getAllQuery = daoConf.getProperty(Queries.GET_ALL_QUERY);
        if (getAllQuery == null) {
            throw new DAOException(new ConfigurationPropertyNotFoundException());
        }
        String query = String.format(getAllQuery, table);
        
        Logger.getLogger(MySQLDriver.class).info(query);
        
        return select(entityClass, query);
    }

    private interface Queries {

        String READ_QUERY = "read_query";

        String INSERT_QUERY = "insert_query";

        String UPDATE_QUERY = "update_query";

        String DELETE_QUERY = "delete_query";

        String GET_ALL_QUERY = "get_all_query";
    }
}
