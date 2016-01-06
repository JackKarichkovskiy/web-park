/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.annotation.utils.converters;

import org.apache.log4j.Logger;
import org.webpark.dao.annotation.utils.Converter;
import org.webpark.dao.entities.User.Roles;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class RolesConverter implements Converter<Roles> {

    @Override
    public String toString(Roles value) {
        if (value == null) {
            return "null";
        }

        return "'" + value.toString() + "'";
    }

    @Override
    public Roles toValue(String str) {
        if (str == null || "null".equals(str)) {
            return null;
        }
        try {
            return Roles.valueOf(str);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Roles.class).error(null, ex);
            return null;
        }
    }

}
