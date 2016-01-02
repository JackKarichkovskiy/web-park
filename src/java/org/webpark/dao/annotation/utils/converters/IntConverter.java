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
class IntConverter implements Converter<Integer> {

    @Override
    public String toString(Integer value) {
        if (value == null) {
            return "null";
        }
        return Integer.toString(value);
    }

    @Override
    public Integer toValue(String str) {
        if (str == null || str.equals("null")) {
            return null;
        }
        return Integer.parseInt(str);
    }

}
