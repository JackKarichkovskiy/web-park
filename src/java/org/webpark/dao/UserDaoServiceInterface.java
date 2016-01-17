/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao;

import java.util.List;
import org.webpark.dao.entities.User;
import org.webpark.dao.exception.DAOException;

/**
 * Interface that defines methods for specific database work with users.
 *
 * @author Karichkovskiy Yevhen
 */
public interface UserDaoServiceInterface {

    /**
     * Returns user with input username and password.
     *
     * @param login - username
     * @param password - password of user
     * @return user entity
     * @throws DAOException - if some database problems
     */
    public User getUserByUsernameAndPassword(String login, String password) throws DAOException;

    /**
     * Returns all foresters in database.
     *
     * @return all foresters
     * @throws DAOException - if some database problems
     */
    public List<User> getAllForesters() throws DAOException;

}
