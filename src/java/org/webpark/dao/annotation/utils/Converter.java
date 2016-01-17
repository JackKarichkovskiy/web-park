/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.annotation.utils;

/**
 * Class that converts some object to string database representation and back.
 *
 * @author Karichkovskiy Yevhen
 * @param <T> - converted object type
 */
public interface Converter<T> {

    /**
     * Converts object to string representation.
     *
     * @param value - object that needs to be converted
     * @return converted string
     */
    public String toString(T value);

    /**
     * Converts string to object value.
     *
     * @param str - string that needs to be converted
     * @return converted object
     */
    public T toValue(String str);
}
