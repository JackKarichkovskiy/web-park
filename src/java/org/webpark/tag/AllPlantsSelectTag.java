/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.tag;

import java.io.IOException;
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
 * Class realizes custom jsp tag 'allPlantsSelect'.
 * 
 * @author Karichkovskiy Yevhen
 */
public class AllPlantsSelectTag extends SimpleTagSupport {

    /**
     * Error message tag for some database operation problems.
     */
    private static final String DATABASE_CONN_ERROR = "log.database_conn_error";

    /**
     * Application standard locale bundle.
     */
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();

    /**
     * Class of Plant entity.
     */
    private static final Class<Plant> PLANT_CLASS = Plant.class;

    /**
     * URI that refers to error page.
     */
    private static final String ERROR_PAGE = UriBuilder.getUri("error_page", CommandResult.JumpType.FORWARD);

    /**
     * HTTP error code status if some problems occurs.
     */
    private static final int ERROR_CODE = 500;

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

        List<Plant> allPlants = null;
        try {
            allPlants = AppDaoFactory.getInstance().getCRUDDao().getAllEntities(PLANT_CLASS);
        } catch (DAOException ex) {
            String errorMessage = BUNDLE.getString(DATABASE_CONN_ERROR);
            Logger.getLogger(AllPlantsSelectTag.class).error(errorMessage, ex);
            request.setAttribute(WebTags.ERROR_MESSAGE_TAG, errorMessage);
            request.setAttribute(WebTags.ERROR_CODE_TAG, ERROR_CODE);
            try {
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            } catch (ServletException ex1) {
                Logger.getLogger(AllPlantsSelectTag.class).error(null, ex1);
            }
        }

        if (allPlants == null || allPlants.isEmpty()) {
            return;
        }

        JspWriter out = getJspContext().getOut();
        out.println(String.format("<select name=\"%s\">", WebTags.TASK_PLANT_TAG));
        for (Plant plant : allPlants) {
            out.println(String.format("<option value=\"%s\">%s</option>", plant.getId(), plant.getName()));
        }
        out.println("</select>");
    }
}
