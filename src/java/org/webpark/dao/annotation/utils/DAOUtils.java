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
import org.webpark.dao.exception.DAOException;
import org.webpark.dao.exception.FieldNotStoredException;
import org.webpark.dao.exception.FieldReadingException;
import org.webpark.dao.exception.FieldSettingException;
import org.webpark.dao.exception.ObjectInstantiatingException;
import org.webpark.dao.exception.PrimaryKeyInClassNotFoundException;
import static org.webpark.utils.ProjectUtils.checkNotNull;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class DAOUtils {

    public static final Class<Primary> PRIMARY_KEY_ANNO_CLASS = Primary.class;
    public static final Class<Stored> STORED_ANNO_CLASS = Stored.class;

    /**
     * Метод, що повертає поле із первинним ключем.
     *
     * @param c-клас з якого зчитуються поля.
     * @return поле з первинним ключем.
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
     * Повертає значення первинного ключа.
     *
     * @param <T>
     * @param instance-об'єкт з якого зчитується ключ
     * @return значення первинного ключа
     * @throws org.webpark.dao.exception.DAOException
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
     * Повертає значення поля
     *
     * @param <T>
     * @param instance-об'єкт з якого зчитується значення
     * @param f-поле з якого потрібно зчитати
     * @return значення поля
     * @throws org.webpark.dao.exception.DAOException
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
     * Зчитуються усі поля із аннотацією Stored.
     *
     * @param c-клас з якого зчитуються дані
     * @return колекція(Map) із полями класа
     */
    public static HashMap<String, Field> getStoredFields(Class c) {
        HashMap<String, Field> res = new HashMap<>();
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
     * Перетворює колекцію на об'єкт заданного класу.
     *
     * @param <T>
     * @param instanceClass-клас отриманого об'єкту
     * @param map-карта з полями
     * @return готовий об'єкт
     * @throws org.webpark.dao.exception.DAOException
     */
    public static <T> T MapToEntity(Class<T> instanceClass, Map map) throws DAOException {
        checkNotNull(instanceClass);
        checkNotNull(map);

        HashMap<String, Field> storedFields = getStoredFields(instanceClass);

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
     * Перетворює об'єкт у карту з полей та їх імен.
     *
     * @param <T>
     * @param instance-об'єкт для перетворення
     * @return карту з полями об'єкта
     * @throws org.webpark.dao.exception.DAOException
     */
    public static <T> Map entityToMap(T instance) throws DAOException {
        checkNotNull(instance);

        Map result = new HashMap<>();
        HashMap<String, Field> storedFields = getStoredFields(instance.getClass());

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
     * Використовує конвертор поля і повертає необхідний клас, згідно з
     * конвертором.
     *
     * @param instance-об'єкт поля
     * @return клас,
     * @throws org.webpark.dao.exception.DAOException
     */
    public static Class convertFieldType(Field instance) throws DAOException {
        checkNotNull(instance);

        if (!instance.isAnnotationPresent(STORED_ANNO_CLASS)) {
            throw new DAOException(new FieldNotStoredException());
        }

        return instance.getAnnotation(STORED_ANNO_CLASS).converter().getValueType();
    }

    /**
     * Повертає Конвертор для данного поля.
     *
     * @param <T>
     * @param field-поле з якого вилучається Конвертор
     * @return Конвертор поля
     * @throws org.webpark.dao.exception.DAOException
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
     * Перетворює ResultSet у колекцію об'єктів бази даних.
     *
     * @param <T>
     * @param entityClass-клас отриманих об'єктів
     * @param rset-об'єкт класу ResultSet, отриманий після деякого запиту до
     * бази даних
     * @return колекцію з отриманими об'єктами
     * @throws org.webpark.dao.exception.DAOException
     */
    public static <T> ArrayList<T> ResultSetToEntityArray(Class<T> entityClass, ResultSet rset) throws DAOException {
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
                obj = MapToEntity(entityClass, map);
                result.add(obj);
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new DAOException(new ObjectInstantiatingException(ex));
        }

        return result;
    }
}
