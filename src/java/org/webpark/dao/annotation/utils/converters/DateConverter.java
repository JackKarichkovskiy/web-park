/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.annotation.utils.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.webpark.dao.annotation.utils.Converter;
import org.webpark.locale.AppBundleFactory;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class DateConverter implements Converter<Date> {

    private static final String DATE_CONVERT_ERROR_TAG = "log.date_convert_error";
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public String toString(Date value) {
        if (value == null) {
            return null;
        }
        return "'" + DATE_FORMAT.format(value) + "'";
    }

    @Override
    public Date toValue(String str) {
        if ("null".equals(str)) {
            return null;
        }
        Date parsedDate;
        try {
            parsedDate = DATE_FORMAT.parse(str);
        } catch (ParseException ex) {
            String errorMessage = String.format(BUNDLE.getString(DATE_CONVERT_ERROR_TAG), str);
            Logger.getLogger(DateConverter.class).error(errorMessage, ex);
            return null;
        }
        return parsedDate;
    }

}
