/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.annotation.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.webpark.dao.annotation.Primary;
import org.webpark.dao.annotation.Stored;
import org.webpark.dao.annotation.utils.converters.Converters;
import org.webpark.dao.exception.DAOException;
import org.webpark.dao.exception.FieldNotStoredException;
import org.webpark.dao.exception.FieldReadingException;
import org.webpark.dao.exception.FieldSettingException;
import org.webpark.dao.exception.ObjectInstantiatingException;
import org.webpark.dao.exception.PrimaryKeyInClassNotFoundException;
import static org.webpark.utils.ProjectUtils.checkNotNull;

/**
 * Class that contains some general utility methods for dealing with dao
 * entities.
 *
 * @author Karichkovskiy Yevhen
 */
public class DAOUtils {

    /**
     * Class of annotation that marks primary keys.
     */
    public static final Class<Primary> PRIMARY_KEY_ANNO_CLASS = Primary.class;

    /**
     * Class of annotation that marks all stored in database fields.
     */
    public static final Class<Stored> STORED_ANNO_CLASS = Stored.class;

    /**
     * Defines table name of input entity.
     *
     * @param <T> - type of input entity
     * @param instance - instance of entity
     * @return name of table
     */
    public static <T> String defineTableName(Class<T> instance) {
        checkNotNull(instance);

        String table = null;
        //Определения таблицы
        if (instance.isAnnotationPresent(DAOUtils.STORED_ANNO_CLASS)) {
            table = instance.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name();
        }
        return table;
    }

    /**
     * Method that returns primary key field.
     *
     * @param c - entity class
     * @return primary key field
     */
    public static Field getPrimaryKey(Class c) {
        checkNotNull(c);

        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Primary p = field.getAnnotation(PRIMARY_KEY_ANNO_CLASS);
            if (p != null) {
                return field;
            }
        }
        return null;
    }

    /**
     * Returns primary key value.
     *
     * @param <T> - type of entity
     * @param instance - instance of entity
     * @return primary key value
     * @throws org.webpark.dao.exception.DAOException - if primary key not found
     * or problems with reading from fields
     */
    public static <T> Object getPrimaryKeyValue(T instance) throws DAOException {
        checkNotNull(instance);

        Field pk = getPrimaryKey(instance.getClass());
        if (pk == null) {
            throw new DAOException(new PrimaryKeyInClassNotFoundException());
        }

        PropertyDescriptor p;

        try {
            p = new PropertyDescriptor(pk.getName(), instance.getClass());
            return p.getReadMethod().invoke(instance, null);
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new DAOException(new FieldReadingException(ex));
        }
    }

    /**
     * Returns field value.
     *
     * @param <T> - type of entity
     * @param instance - instance of entity
     * @param f - field of entity
     * @return field value
     * @throws org.webpark.dao.exception.DAOException - if some problems with
     * reading from field
     */
    public static <T> Object getFieldValue(T instance, Field f) throws DAOException {
        checkNotNull(instance);
        checkNotNull(f);

        PropertyDescriptor p;
        try {
            p = new PropertyDescriptor(f.getName(), instance.getClass());
            return p.getReadMethod().invoke(instance, null);
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new DAOException(new FieldReadingException(ex));
        }
    }

    /**
     * Get all fields that stored in database.
     *
     * @param c - entity class
     * @return map with field name-value mapping
     */
    public static Map<String, Field> getStoredFields(Class c) {
        Map<String, Field> res = new HashMap<>();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Stored p = field.getAnnotation(STORED_ANNO_CLASS);
            if (p != null) {
                res.put(p.name(), field);
            }
        }
        return res;
    }

    /**
     * Transform map of fields to constructed object.
     *
     * @param <T> - type of entity
     * @param instanceClass - class of entity
     * @param map - map with fields mapping
     * @return constructed object
     * @throws org.webpark.dao.exception.DAOException - if some object
     * instantiating problems occurred
     */
    public static <T> T mapToEntity(Class<T> instanceClass, Map map) throws DAOException {
        checkNotNull(instanceClass);
        checkNotNull(map);

        Map<String, Field> storedFields = getStoredFields(instanceClass);

        T instance = null;
        try {
            instance = (T) instanceClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new DAOException(new ObjectInstantiatingException(ex));
        }

        try {
            PropertyDescriptor p;
            for (String storedName : storedFields.keySet()) {
                Object strValue = map.get(storedName);
                Field f = storedFields.get(storedName);
                Converter c = getConverter(f);
                p = new PropertyDescriptor(f.getName(), instanceClass);
                p.getWriteMethod().invoke(instance, c.toValue(strValue.toString()));

            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new DAOException(new FieldSettingException(ex));
        }
        return instance;
    }

    /**
     * Transform object to map of fields.
     *
     * @param <T> - type of entity
     * @param instance - instance of entity
     * @return map of fields
     * @throws org.webpark.dao.exception.DAOException - if some field reading
     * problems
     */
    public static <T> Map entityToMap(T instance) throws DAOException {
        checkNotNull(instance);

        Map result = new HashMap<>();
        Map<String, Field> storedFields = getStoredFields(instance.getClass());

        PropertyDescriptor p;
        try {
            for (String storedName : storedFields.keySet()) {
                Field f = storedFields.get(storedName);

                p = new PropertyDescriptor(f.getName(), instance.getClass());
                Class returnType = p.getReadMethod().getReturnType();
                Object value = p.getReadMethod().invoke(instance, null);
                Converter c = getConverter(f);
                result.put(storedName, c.toString(value));
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new DAOException(new FieldReadingException(ex));
        }
        return result;
    }

    /**
     * Get Converter type of converting object from field.
     *
     * @param instance - field of entity
     * @return class available to convert by current converter
     * @throws org.webpark.dao.exception.DAOException - if instance not marked
     * as stored in database
     */
    public static Class convertFieldType(Field instance) throws DAOException {
        checkNotNull(instance);

        if (!instance.isAnnotationPresent(STORED_ANNO_CLASS)) {
            throw new DAOException(new FieldNotStoredException());
        }

        return instance.getAnnotation(STORED_ANNO_CLASS).converter().getValueType();
    }

    /**
     * Returns converter to input field.
     *
     * @param <T> - type of Converter
     * @param field - field of entity
     * @return Converter to field
     * @throws org.webpark.dao.exception.DAOException - if field isn't marked as
     * stored in database
     */
    public static <T extends Converter> T getConverter(Field field) throws DAOException {
        checkNotNull(field);

        if (!field.isAnnotationPresent(STORED_ANNO_CLASS)) {
            throw new DAOException(new FieldNotStoredException());
        }
        Converter converter = field.getAnnotation(STORED_ANNO_CLASS).converter().getConverter();
        return (T) converter;
    }

    /**
     * Transform ResultSet into collection of entity objects.
     *
     * @param <T> - type of entities
     * @param entityClass - class of entities
     * @param rset - ResultSet that stores answer from database
     * @return collection of entities
     * @throws org.webpark.dao.exception.DAOException - if some object
     * instantiating problems
     */
    public static <T> ArrayList<T> resultSetToEntityArray(Class<T> entityClass, ResultSet rset) throws DAOException {
        checkNotNull(entityClass);
        checkNotNull(rset);

        ArrayList<T> result = new ArrayList<>();
        try {
            while (rset.next()) {
                T obj = entityClass.newInstance();
                Map<String, Object> map = new HashMap<>();
                for (Field field : entityClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(STORED_ANNO_CLASS)) {
                        map.put(field.getAnnotation(STORED_ANNO_CLASS).name(),
                                rset.getString(field.getAnnotation(STORED_ANNO_CLASS).name()));
                    }
                }
                obj = mapToEntity(entityClass, map);
                result.add(obj);
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new DAOException(new ObjectInstantiatingException(ex));
        }

        return result;
    }

    /**
     * Converts field to string using its converter.
     *
     * @param <T> - type of field
     * @param field - field that needs to be converted
     * @return converted string of field
     */
    public static <T> String convertFieldToString(T field) {
        checkNotNull(field);

        Converters converter = Converters.getConverterByArgType(field.getClass());
        if (converter == null) {
            return null;
        }

        return converter.getConverter().toString(field);
    }
}
