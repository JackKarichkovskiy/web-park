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
 *
 * @author Karichkovskiy Yevhen
 */
public interface UserDaoServiceInterface {
    
    public User getUserByUsernameAndPassword(String login, String password) throws DAOException;
    
    public List<User> getAllForesters() throws DAOException;
    
}
