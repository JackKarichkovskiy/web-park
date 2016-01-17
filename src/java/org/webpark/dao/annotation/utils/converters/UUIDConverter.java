/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.annotation.utils.converters;

import java.util.UUID;
import org.webpark.dao.annotation.utils.Converter;

/**
 * Converts UUID object.
 *
 * @author Karichkovskiy Yevhen
 */
class UUIDConverter implements Converter<UUID> {

    @Override
    public String toString(UUID value) {
        if (value == null) {
            return null;
        }
        return "'" + value.toString() + "'";
    }

    @Override
    public UUID toValue(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("null")) {
            return null;
        }
        return UUID.fromString(str);
    }

}
