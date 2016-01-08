/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.log4j.Logger;
import org.webpark.controller.command.CommandResult;
import org.webpark.controller.command.WebTags;
import org.webpark.controller.uri.UriBuilder;
import org.webpark.dao.AppDaoFactory;
import org.webpark.dao.entities.Plant;
import org.webpark.dao.exception.DAOException;
import org.webpark.locale.AppBundleFactory;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class AllPlantsTag extends SimpleTagSupport {

    private static final String DATABASE_CONN_ERROR = "log.database_conn_error";
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();
    private static final Class<Plant> PLANT_CLASS = Plant.class;
    private static final String ERROR_PAGE = UriBuilder.getUri("error_page", CommandResult.JumpType.FORWARD);
    
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public void doTag() throws JspException, IOException {
        List<Plant> allPlants = null;
        try {
            allPlants = AppDaoFactory.getInstance().getCRUDDao().getAllEntities(PLANT_CLASS);
        } catch (DAOException ex) {
            String errorMessage = BUNDLE.getString(DATABASE_CONN_ERROR);
            Logger.getLogger(AllPlantsTag.class).error(errorMessage, ex);
            PageContext pageContext = (PageContext) getJspContext();
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            request.setAttribute(WebTags.ERROR_MESSAGE_TAG, errorMessage);
            request.setAttribute(WebTags.ERROR_CODE_TAG, 500);
            HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
            try {
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            } catch (ServletException ex1) {
                Logger.getLogger(AllPlantsTag.class).error(null, ex1);
            }
        }

        if (allPlants == null || allPlants.isEmpty()) {
            return;
        }

        JspWriter out = getJspContext().getOut();
        out.println("<table id=\"plants\" border=\"1\">");
        if(title != null){
            out.print("<caption>");out.print(title);out.println("</caption>");
        }
        
        out.println("<tr>");
        out.print("<td>");out.print("No");out.println("</td>");
        out.print("<td>");out.print("Name");out.println("</td>");
        out.print("<td>");out.print("Origin");out.println("</td>");
        out.print("<td>");out.print("Color");out.println("</td>");
        out.print("<td>");out.print("Sector");out.println("</td>");
        out.println("</tr>");
        int index = 1;
        for (Plant plant : allPlants) {
            out.println("<tr>");
            out.print("<td>");out.print(index++);out.println("</td>");
            out.print("<td>");out.print(plant.getName());out.println("</td>");
            out.print("<td>");out.print(plant.getOrigin());out.println("</td>");
            out.print("<td>");out.print(plant.getColor());out.println("</td>");
            out.print("<td>");out.print(plant.getSector());out.println("</td>");
            out.println("</tr>");
        }
        out.println("</table>");
    }
}
