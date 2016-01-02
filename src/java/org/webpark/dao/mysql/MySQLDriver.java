/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.mysql;

import java.beans.IntrospectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.webpark.dao.CRUDDaoInterface;
import org.webpark.dao.DaoConnection;
import org.webpark.dao.annotation.Stored;
import org.webpark.dao.annotation.utils.Converter;
import org.webpark.dao.annotation.utils.DAOUtils;
import org.webpark.dao.exception.DAOException;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class MySQLDriver implements CRUDDaoInterface {

    private final DaoConnection connHolder = DaoConnection.getInstance();

    private static MySQLDriver instance;
    
    private MySQLDriver(String dummyStr){
    }
    
    public static MySQLDriver getInstance(){
        if(instance == null){
            return new MySQLDriver(null);
        }
        
        return instance;
    }
    
    @Override
    public <T> T read(Class<T> entityClass, UUID id) throws DAOException {
        String table = null;
        ResultSet rs;
        for (Annotation anno : entityClass.getAnnotations()) {
            if (anno.annotationType().equals(DAOUtils.STORED_ANNO_CLASS)) {
                table = ((Stored) anno).name();
            }
        }
        if(table == null){
            return null;
        }
        Field primaryKey = DAOUtils.getPrimaryKey(entityClass);
        Converter pkConverter = primaryKey.getAnnotation(DAOUtils.STORED_ANNO_CLASS).converter().getConverter();
        String query = String.format("SELECT * FROM %s "
                + "WHERE %s.%s=%s;", table, table, primaryKey.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name(), pkConverter.toString(id));
        System.out.println(query);

        Connection connection = null;
        try {
            connection = connHolder.getConnection();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        try (Statement stmt = connection.createStatement()) {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                for (Field field : entityClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(DAOUtils.STORED_ANNO_CLASS)) {
                        map.put(field.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name(), rs.getString(field.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name()));
                    }
                }
                return (T) DAOUtils.<T>MapToEntity(entityClass, map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } 
        return null;
    }

    @Override
    public <T> T insert(T instance) throws DAOException {
        String query = null;
        String table = null;

        //Defining the table
        if (instance.getClass().isAnnotationPresent(DAOUtils.STORED_ANNO_CLASS)) {
            table = instance.getClass().getAnnotation(DAOUtils.STORED_ANNO_CLASS).name();
        }

        //Forming the query
        query = String.format("INSERT INTO %s VALUES(", table);
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DAOUtils.STORED_ANNO_CLASS)) {
                query += DAOUtils.getConverter(field).toString(DAOUtils.getFieldValue(instance, field)) + ",";
            }
        }
        query = query.substring(0, query.length() - 1);
        query += ");";
        System.out.println(query);
        
        //Executing the query
        Connection connection = null;
        try {
            connection = connHolder.getConnection();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }

    @Override
    public <T> void update(T instance) throws DAOException {
        String query = null;
        String table = null;

        //Определения таблицы
        if (instance.getClass().isAnnotationPresent(DAOUtils.STORED_ANNO_CLASS)) {
            table = instance.getClass().getAnnotation(DAOUtils.STORED_ANNO_CLASS).name();
        }

        //Формирование запроса
        query = String.format("UPDATE %s\nSET ", table);
        for (Field field : instance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DAOUtils.STORED_ANNO_CLASS) && (!field.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name().equals("ID"))) {
                query += String.format("%s=%s,", field.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name(), DAOUtils.getConverter(field).toString(DAOUtils.<T>getFieldValue(instance, field)));
            }
        }
        query = query.substring(0, query.length() - 1);
        query += String.format("\nWHERE ID='%s'", DAOUtils.getPrimaryKeyValue(instance).toString());
        System.out.println(query);

        Connection connection = null;
        try {
            connection = connHolder.getConnection();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> void delete(T instance) throws DAOException {
        String query = null;
        String table = null;

        //Определения таблицы
        if (instance.getClass().isAnnotationPresent(DAOUtils.STORED_ANNO_CLASS)) {
            table = instance.getClass().getAnnotation(DAOUtils.STORED_ANNO_CLASS).name();
        }

        query = String.format("DELETE FROM %s WHERE %s='%s';", table, DAOUtils.getPrimaryKey(instance.getClass()).getName(),
                DAOUtils.getPrimaryKeyValue(instance));
        System.out.println(query);

        Connection connection = null;
        try {
            connection = connHolder.getConnection();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> List<T> select(Class<T> entityClass, String SQLString) throws DAOException {
        List<T> resultList = new ArrayList<T>();
        ResultSet rs;

        Connection connection = null;
        try {
            connection = connHolder.getConnection();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        try (Statement stmt = connection.createStatement()) {
            rs = stmt.executeQuery(SQLString);
            resultList = DAOUtils.ResultSetToEntityArray(entityClass, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
