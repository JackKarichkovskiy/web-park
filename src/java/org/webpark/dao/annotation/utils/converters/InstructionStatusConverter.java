/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.annotation.utils.converters;

import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.webpark.dao.annotation.utils.Converter;
import org.webpark.dao.entities.Instruction.Status;
import org.webpark.locale.AppBundleFactory;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class InstructionStatusConverter implements Converter<Status> {

    private static final String STATUS_CONVERT_ERROR_TAG = "log.status_convert_error";
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();
    
    @Override
    public String toString(Status value) {
        if (value == null) {
            return "null";
        }

        return "'" + value.toString() + "'";
    }

    @Override
    public Status toValue(String str) {
        if (str == null || "null".equals(str)) {
            return null;
        }
        try {
            return Status.valueOf(str);
        } catch (IllegalArgumentException ex) {
            String errorMessage = String.format(BUNDLE.getString(STATUS_CONVERT_ERROR_TAG), str);
            Logger.getLogger(InstructionStatusConverter.class).error(errorMessage, ex);
            return null;
        }
    }

}
