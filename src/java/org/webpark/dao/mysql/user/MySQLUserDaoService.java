/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.mysql.user;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.webpark.configuration.exception.ConfigurationPropertyNotFoundException;
import org.webpark.dao.AppDaoFactory;
import org.webpark.dao.DaoConnection;
import org.webpark.dao.UserDaoServiceInterface;
import org.webpark.dao.annotation.utils.DAOUtils;
import org.webpark.dao.annotation.utils.converters.Converters;
import org.webpark.dao.entities.User;
import org.webpark.dao.exception.DAOException;
import org.webpark.dao.mysql.MySQLDaoConfiguration;
import static org.webpark.utils.ProjectUtils.checkNotNull;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class MySQLUserDaoService implements UserDaoServiceInterface {

    private static final Class<User> USER_CLASS = User.class;

    private final MySQLDaoConfiguration daoConf = MySQLDaoConfiguration.getInstance();

    private final DaoConnection connHolder = DaoConnection.getInstance();

    private MySQLUserDaoService() {
    }

    public static MySQLUserDaoService getInstance() {
        return UserDaoServiceHolder.INSTANCE;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) throws DAOException {
        checkNotNull(username);
        checkNotNull(password);

        Converters usernameConverter = Converters.getConverterByArgType(username.getClass());
        String convertedUsername = usernameConverter.getConverter().toString(username);
        Converters passwordConverter = Converters.getConverterByArgType(password.getClass());
        String convertedPassword = passwordConverter.getConverter().toString(password);

        String readQuery = daoConf.getProperty(MySQLUserDaoService.Queries.GET_BY_USERNAME_PASSWORD_QUERY_TAG);
        if (readQuery == null) {
            throw new DAOException(new ConfigurationPropertyNotFoundException());
        }
        String query = String.format(readQuery, convertedUsername, convertedPassword);
        Logger.getLogger(MySQLUserDaoService.class).info(query);
        
        Connection connection = null;
        try {
            connection = connHolder.getConnection();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        
        ResultSet rs;
        try (Statement stmt = connection.createStatement()) {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                HashMap<String, Object> map = new HashMap<>();
                for (Field field : USER_CLASS.getDeclaredFields()) {
                    if (field.isAnnotationPresent(DAOUtils.STORED_ANNO_CLASS)) {
                        map.put(field.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name(), rs.getString(field.getAnnotation(DAOUtils.STORED_ANNO_CLASS).name()));
                    }
                }
                return DAOUtils.MapToEntity(USER_CLASS, map);
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        }
        return null;
    }

    @Override
    public List<User> getAllForesters() throws DAOException {
        String selectQuery = daoConf.getProperty(MySQLUserDaoService.Queries.GET_ALL_FORESTERS_TAG);
        if (selectQuery == null) {
            throw new DAOException(new ConfigurationPropertyNotFoundException());
        }
        
        return AppDaoFactory.getInstance().getCRUDDao().select(USER_CLASS, selectQuery);
    }

    private static class UserDaoServiceHolder {

        private static final MySQLUserDaoService INSTANCE = new MySQLUserDaoService();
    }

    private interface Queries {

        String GET_BY_USERNAME_PASSWORD_QUERY_TAG = "user.get_by_username_password";
        String GET_ALL_FORESTERS_TAG = "user.get_all_foresters";
    }
}
