/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.annotation.utils.converters;

import java.util.UUID;
import org.webpark.dao.annotation.utils.Converter;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public enum Converters {
    StringConverter(String.class, new StringConverter()),
    UUIDConverter(UUID.class, new UUIDConverter()),
    IntConverter(Integer.class, new IntConverter()),
    BooleanConverter(Boolean.class, new BooleanConverter()),
    ;
    
    private final Converter converter;
    
    private final Class valueType;
    
    Converters(Class valueType, Converter converter){
        this.converter = converter;
        this.valueType = valueType;
    }
    
    public Converter getConverter(){
        return converter;
    }

    public Class getValueType() {
        return valueType;
    }
}
