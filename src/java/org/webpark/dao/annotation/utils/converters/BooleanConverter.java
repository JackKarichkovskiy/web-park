/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.annotation.utils.converters;

import org.webpark.dao.annotation.utils.Converter;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class BooleanConverter implements Converter<Boolean> {

    @Override
    public String toString(Boolean value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @Override
    public Boolean toValue(String str) {
        if (str == null || str.equals("null")) {
            return null;
        }
        if (str.equals("1")) {
            return true;
        }
        if (str.equals("0")) {
            return false;
        }
        return Boolean.valueOf(str);
    }

}
