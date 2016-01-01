/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.webpark.dao.annotation.utils.converters.StringConverter;

/**
 *
 * @author Karichkovskiy Yevhen
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Stored {

    public String name();

    public Class converter() default StringConverter.class;
}
