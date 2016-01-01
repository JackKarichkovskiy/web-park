/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.annotation.utils;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public interface Converter<T> {

    public String toString(T value);

    public T toValue(String str);
}
