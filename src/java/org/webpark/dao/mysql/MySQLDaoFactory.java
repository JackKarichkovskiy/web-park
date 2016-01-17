/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.dao.mysql;

import org.webpark.dao.CRUDDaoInterface;
import org.webpark.dao.DaoFactory;
import org.webpark.dao.InstructionDaoServiceInterface;
import org.webpark.dao.UserDaoServiceInterface;
import org.webpark.dao.mysql.instruction.MySQLInstructionDaoService;
import org.webpark.dao.mysql.user.MySQLUserDaoService;

/**
 * Class that realizes MySQL DAO factory.
 *
 * @author Karichkovskiy Yevhen
 */
public class MySQLDaoFactory extends DaoFactory {

    @Override
    public CRUDDaoInterface getCRUDDao() {
        return MySQLDriver.getInstance();
    }

    @Override
    public UserDaoServiceInterface getUserDao() {
        return MySQLUserDaoService.getInstance();
    }

    @Override
    public InstructionDaoServiceInterface getInstructionDao() {
        return MySQLInstructionDaoService.getInstance();
    }

}
