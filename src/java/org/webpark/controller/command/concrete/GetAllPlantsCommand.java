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
import org.webpark.controller.command.WebTags;
import org.webpark.controller.uri.UriBuilder;
import org.webpark.dao.AppDaoFactory;
import org.webpark.dao.entities.Plant;
import org.webpark.dao.exception.DAOException;

/**
 *
 * @author Karichkovskiy Yevhen
 */

class GetAllPlantsCommand implements Command{
    private static final Class<?> PLANT_CLASS = Plant.class;
    private static final String DEFAULT_PAGE = UriBuilder.getUri("init_page");
    
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<?> allPlants = AppDaoFactory.getInstance().getCRUDDao().getAllEntities(PLANT_CLASS);
            request.setAttribute(WebTags.ALL_PLANTS_TAG, allPlants);
        } catch (DAOException ex) {
            Logger.getLogger(GetAllPlantsCommand.class.getName()).error(null, ex);
        }
        
        return new CommandResult(DEFAULT_PAGE, CommandResult.JumpType.FORWARD);
    }
    
}
