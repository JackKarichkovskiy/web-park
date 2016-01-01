/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao;

import java.util.List;
import java.util.UUID;
import org.webpark.dao.exception.DAOException;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public interface CRUDDaoInterface {

    /**
     * Reads entity from database by its id.
     *
     * @param entityClass-class of entity
     * @param id-primary field
     * @return result entity object from db
     * @throws DAOException - if some sql or connection problems
     */
    public <T> T read(Class entityClass, UUID id) throws DAOException;

    /**
     * Inserts new data to the database.
     *
     * @param instance-object that needs to be inserted
     * @return the input object
     * @throws DAOException - if some sql or connection problems
     */
    public <T> T insert(T instance) throws DAOException;

    /**
     * Updates raw in the db by object's id.
     *
     * @param instance-об'єкт, який необхідно оновити
     * @throws DAOException - if some sql or connection problems
     */
    public <T> void update(T instance) throws DAOException;

    /**
     * Deletes entity in db with the same id.
     *
     * @param instance - object that needs to be deleted
     * @throws DAOException - if some sql or connection problems
     */
    public <T> void delete(T instance) throws DAOException;

    /**
     * Returns list of entities from db after executing input SQL string.
     *
     * @param entityClass-class of entities
     * @param SQLString - sql string that needs to be executed
     * @return list of result entities
     * @throws DAOException - if some sql or connection problems
     */
    public <T> List<T> select(Class entityClass, String SQLString) throws DAOException;
}
