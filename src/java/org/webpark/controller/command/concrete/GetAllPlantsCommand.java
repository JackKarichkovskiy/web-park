/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command.concrete;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.webpark.controller.command.Command;
import org.webpark.controller.command.CommandResult;
import org.webpark.dao.entities.Plant;
import org.webpark.dao.exception.DAOException;
import org.webpark.dao.mysql.MySQLDriver;

/**
 *
 * @author Karichkovskiy Yevhen
 */
class GetAllPlantsCommand implements Command{
    private static final Class<?> PLANT_CLASS = Plant.class;
    private static final String RESULT_PAGE = "./index.html";
    private static final String ALL_PLANTS_ATTR = "allPlants";
    
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<?> allPlants = MySQLDriver.getInstance().getAllEntities(PLANT_CLASS);
            request.setAttribute(ALL_PLANTS_ATTR, allPlants);
        } catch (DAOException ex) {
            Logger.getLogger(GetAllPlantsCommand.class.getName()).error(null, ex);
        }
        
        return new CommandResult(RESULT_PAGE, CommandResult.JumpType.FORWARD);
    }
    
}
