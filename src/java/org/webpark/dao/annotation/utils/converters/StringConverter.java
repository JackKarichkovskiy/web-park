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
class StringConverter implements Converter<String> {

    @Override
    public String toString(String value) {
        if (value == null) {
            return null;
        }
        return "'" + value + "'";
    }

    @Override
    public String toValue(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("null")) {
            return null;
        }
        if (str.equals("")) {
            return "";
        }

        return str;
    }

}
