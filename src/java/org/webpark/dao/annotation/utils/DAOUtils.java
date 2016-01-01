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
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.webpark.dao.annotation.Primary;
import org.webpark.dao.annotation.Stored;
import org.webpark.dao.annotation.utils.converters.BooleanConverter;
import org.webpark.dao.annotation.utils.converters.IntConverter;
import org.webpark.dao.annotation.utils.converters.StringConverter;
import org.webpark.dao.annotation.utils.converters.UUIDConverter;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class DAOUtils {

    /**
     * Метод, що повертає поле із первинним ключем.
     *
     * @param c-клас з якого зчитуються поля.
     * @return поле з первинним ключем.
     */
    public static Field getPrimaryKey(Class c) {
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Primary p = field.getAnnotation(Primary.class);
            if (p != null) {
                return field;
            }
        }
        return null;
    }

    /**
     * Повертає значення первинного ключа.
     *
     * @param instance-об'єкт з якого зчитується ключ
     * @return значення первинного ключа
     */
    public static <T> UUID getPrimaryKeyValue(T instance) {
        Field pk = getPrimaryKey(instance.getClass());
        PropertyDescriptor p;
        try {
            p = new PropertyDescriptor(pk.getName(), instance.getClass());
            return ((UUID) p.getReadMethod().invoke(instance, null));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException ex) {
            Logger.getLogger(DAOUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Повертає значення поля
     *
     * @param instance-об'єкт з якого зчитується значення
     * @param f-поле з якого потрібно зчитати
     * @return значення поля
     */
    public static <T> T getFieldValue(T instance, Field f) {
        PropertyDescriptor p;
        try {
            p = new PropertyDescriptor(f.getName(), instance.getClass());
            return ((T) p.getReadMethod().invoke(instance, null));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException ex) {
            Logger.getLogger(DAOUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Зчитуються усі поля із аннотацією Stored.
     *
     * @param c-клас з якого зчитуються дані
     * @return колекція(Map) із полями класа
     */
    public static HashMap<String, Field> getStoredFields(Class c) {
        HashMap<String, Field> res = new HashMap<String, Field>();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Stored p = field.getAnnotation(Stored.class);
            if (p != null) {
                res.put(p.name(), field);
            }
        }
        return res;
    }

    /**
     * Зчитується значення поля як рядок
     *
     * @param instance-об'єкт з якого потрібно зчитати
     * @param f-поле з якого потрібно зчитати
     * @return рядок-значення поля
     */
    public static <T> String getStringValue(T instance, Field f) {
        try {
            PropertyDescriptor p = new PropertyDescriptor(f.getName(), instance.getClass());
            Class fieldClass = p.getPropertyType();
            if (fieldClass.getCanonicalName().equals("int")) {
                return ((Integer) p.getReadMethod().invoke(instance, null)).toString();
            }
            if (fieldClass.getCanonicalName().equals("double")) {
                return ((Double) p.getReadMethod().invoke(instance, null)).toString();
            }
            Object value = p.getReadMethod().invoke(instance, null);

            if (value == null) {
                return "null";
            }

            if (fieldClass.equals(String.class) || fieldClass.equals(UUID.class)) {
                return "\"" + value.toString() + "\"";
            }

            String prefix = ""; // (isReference(f)) ? "^"+f.getType().getCanonicalName()+" : " : "";
            return (value != null) ? prefix + value.toString() : prefix + "NULL";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException ex) {
            Logger.getLogger(DAOUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Перетворює колекцію на об'єкт заданного класу.
     *
     * @param instanceClass-клас отриманого об'єкту
     * @param map-карта з полями
     * @return готовий об'єкт
     */
    public static <T> T MapToEntity(Class instanceClass, Map map) throws IntrospectionException {
        PropertyDescriptor p;
        HashMap<String, Field> storedFields = getStoredFields(instanceClass);
        try {
            T instance = (T) instanceClass.newInstance();

            for (String storedName : storedFields.keySet()) {
                Object strValue = map.get(storedName);
                Field f = storedFields.get(storedName);
                Converter c = getConverter(f);
                p = new PropertyDescriptor(f.getName(), instanceClass);
                p.getWriteMethod().invoke(instance, c.toValue(strValue.toString()));
            }
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException ex) {
            Logger.getLogger(DAOUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Перетворює об'єкт у карту з полей та їх імен.
     *
     * @param instance-об'єкт для перетворення
     * @return карту з полями об'єкта
     */
    public static <T> Map entityToMap(T instance) {
        PropertyDescriptor p;
        Map res = new HashMap<String, Object>();
        HashMap<String, Field> storedFields = getStoredFields(instance.getClass());
        try {
            for (String storedName : storedFields.keySet()) {
                Field f = storedFields.get(storedName);

                p = new PropertyDescriptor(f.getName(), instance.getClass());
                Class returnType = p.getReadMethod().getReturnType();
                Object value = p.getReadMethod().invoke(instance, null);
                Converter c = getConverter(f);
                res.put(storedName, c.toString(value));
            }
            return res;
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Повертає список значень полів із об'єкту instance
     *
     * @param instance-об'єкт з якого будуть зчитуватись значення
     * @return об'єкт класу Properties зі значеннями полів
     */
    public static <T> Properties getValueList(T instance) {
        Properties res = new Properties();
        Class instanceClass = instance.getClass();
        HashMap<String, Field> storedFields = getStoredFields(instanceClass);
        for (String storedName : storedFields.keySet()) {
            Field f = storedFields.get(storedName);
            res.setProperty(storedName, getStringValue(instance, f));
        }
        return res;
    }

    /**
     * Використовує конвертор поля і повертає необхідний клас, згідно з
     * конвертором.
     *
     * @param instance-об'єкт поля
     * @return клас,
     */
    public static Class convertFieldType(Field instance) {
        if (instance.getAnnotation(Stored.class).converter() == StringConverter.class) {
            return String.class;
        }
        if (instance.getAnnotation(Stored.class).converter() == BooleanConverter.class) {
            return Boolean.class;
        }
        if (instance.getAnnotation(Stored.class).converter() == IntConverter.class) {
            return Integer.class;
        }
        if (instance.getAnnotation(Stored.class).converter() == UUIDConverter.class) {
            return String.class;
        }
        return null;
    }

    /**
     * Повертає Конвертор для данного поля.
     *
     * @param field-поле з якого вилучається Конвертор
     * @return Конвертор поля
     */
    public static <T extends Converter> T getConverter(Field field) {
        Stored s = (Stored) field.getAnnotation(Stored.class);
        Class converterClass = s.converter();
        try {
            Converter res = (Converter) converterClass.newInstance();
            return (T) res;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Перетворює ResultSet у колекцію об'єктів бази даних.
     *
     * @param entityClass-клас отриманих об'єктів
     * @param rset-об'єкт класу ResultSet, отриманий після деякого запиту до
     * бази даних
     * @return колекцію з отриманими об'єктами
     */
    public static <T> ArrayList<T> ResultSetToEntityArray(Class entityClass, ResultSet rset) {
        ArrayList<T> result = new ArrayList<T>();
        try {
            while (rset.next()) {
                T obj = (T) entityClass.newInstance();
                HashMap<String, Object> map = new HashMap<String, Object>();
                for (Field field : entityClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Stored.class)) {
                        map.put(field.getAnnotation(Stored.class).name(), rset.getString(field.getAnnotation(Stored.class).name()));
                    }
                }
                obj = MapToEntity(entityClass, map);
                result.add(obj);
            }
            return result;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException ex) {
            Logger.getLogger(DAOUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
