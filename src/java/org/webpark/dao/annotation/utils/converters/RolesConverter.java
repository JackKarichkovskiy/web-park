/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.annotation.utils.converters;

import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.webpark.dao.annotation.utils.Converter;
import org.webpark.dao.entities.User.Roles;
import org.webpark.locale.AppBundleFactory;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class RolesConverter implements Converter<Roles> {

    private static final String ROLE_CONVERT_ERROR_TAG = "log.role_convert_error";
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();
    
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
            String errorMessage = String.format(BUNDLE.getString(ROLE_CONVERT_ERROR_TAG), str);
            Logger.getLogger(Roles.class).error(errorMessage, ex);
            return null;
        }
    }

}
