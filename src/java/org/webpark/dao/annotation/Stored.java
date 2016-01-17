/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.webpark.dao.annotation.utils.converters.Converters;

/**
 * Annotation that marks entities that stored in database. Also contains the
 * related name in database to marked part and the converter.
 *
 * @author Karichkovskiy Yevhen
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Stored {

    /**
     * Stores the name of marked part in database.
     *
     * @return name in database
     */
    public String name();

    /**
     * Converter for integration with database.
     *
     * @return converter
     */
    public Converters converter() default Converters.StringConverter;
}
