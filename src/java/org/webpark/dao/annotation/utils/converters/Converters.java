/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.annotation.utils.converters;

import java.util.Date;
import java.util.UUID;
import org.webpark.dao.annotation.utils.Converter;
import org.webpark.dao.entities.Instruction;
import org.webpark.dao.entities.InstructionStep;
import org.webpark.dao.entities.User.Roles;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public enum Converters {
    StringConverter(String.class, new StringConverter()),
    UUIDConverter(UUID.class, new UUIDConverter()),
    IntConverter(Integer.class, new IntConverter()),
    BooleanConverter(Boolean.class, new BooleanConverter()),
    DateConverter(Date.class, new DateConverter()),
    RolesConverter(Roles.class, new RolesConverter()),
    InstructionStatusConverter(Instruction.Status.class, new InstructionStatusConverter()),
    InstructionStepStatusConverter(InstructionStep.Status.class, new InstructionStepStatusConverter()),
    ;
    
    private final Converter converter;
    
    private final Class valueType;
    
    Converters(Class valueType, Converter converter){
        this.converter = converter;
        this.valueType = valueType;
    }
    
    public static Converters getConverterByArgType(Class clazz){
        for(Converters converter : Converters.values()){
            if(converter.getValueType().equals(clazz)){
                return converter;
            }
        }
        
        return null;
    }
    
    public Converter getConverter(){
        return converter;
    }

    public Class getValueType() {
        return valueType;
    }
}
