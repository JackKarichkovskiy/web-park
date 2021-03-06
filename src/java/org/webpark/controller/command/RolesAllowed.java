/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.webpark.dao.entities.User.Roles;

/**
 * Annotation that controls the access to Command executing.
 *
 * @author Karichkovskiy Yevhen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RolesAllowed {

    /**
     * Roles that have access to Command.
     * @return roles that have access to Command
     */
    public Roles[] value();
}
