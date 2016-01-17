/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.tag;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.log4j.Logger;
import org.webpark.controller.command.CommandResult;
import org.webpark.controller.command.WebTags;
import org.webpark.controller.uri.UriBuilder;
import org.webpark.dao.AppDaoFactory;
import org.webpark.dao.entities.Plant;
import org.webpark.dao.exception.DAOException;
import org.webpark.locale.AppBundleFactory;
import org.webpark.locale.Language;

/**
 * Class realizes custom jsp tag 'allPlants'.
 *
 * @author Karichkovskiy Yevhen
 */
public class AllPlantsTag extends SimpleTagSupport {

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

    /**
     * Title of plants table. Serves as an attribute of custom tag.
     */
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        HttpSession session = request.getSession();

        List<Plant> allPlants = null;
        try {
            allPlants = AppDaoFactory.getInstance().getCRUDDao().getAllEntities(PLANT_CLASS);
        } catch (DAOException ex) {
            String errorMessage = BUNDLE.getString(DATABASE_CONN_ERROR);
            Logger.getLogger(AllPlantsTag.class).error(errorMessage, ex);
            request.setAttribute(WebTags.ERROR_MESSAGE_TAG, errorMessage);
            request.setAttribute(WebTags.ERROR_CODE_TAG, ERROR_CODE);
            try {
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            } catch (ServletException ex1) {
                Logger.getLogger(AllPlantsTag.class).error(null, ex1);
            }
        }

        if (allPlants == null || allPlants.isEmpty()) {
            return;
        }
        Locale curLocale = (Locale) Config.get(session, Config.FMT_LOCALE);
        Language sessionLang = Language.getLanguageByLocale(curLocale);
        ResourceBundle sessionBundle = AppBundleFactory.getInstance().createBundle(sessionLang);

        JspWriter out = getJspContext().getOut();
        out.println("<table id=\"plants\" border=\"1\">");
        if (title != null) {
            out.print("<caption>");
            out.print(title);
            out.println("</caption>");
        }

        out.println("<tr>");
        out.println(String.format("<td>%s</td>", sessionBundle.getString(LocaleKeys.NUMBER)));
        out.println(String.format("<td>%s</td>", sessionBundle.getString(LocaleKeys.NAME)));
        out.println(String.format("<td>%s</td>", sessionBundle.getString(LocaleKeys.ORIGIN)));
        out.println(String.format("<td>%s</td>", sessionBundle.getString(LocaleKeys.COLOR)));
        out.println(String.format("<td>%s</td>", sessionBundle.getString(LocaleKeys.SECTOR)));
        out.println("</tr>");
        int index = 1;
        for (Plant plant : allPlants) {
            out.println("<tr>");
            out.println(String.format("<td>%s</td>", index++));
            out.println(String.format("<td>%s</td>", plant.getName()));
            out.println(String.format("<td>%s</td>", plant.getOrigin()));
            out.println(String.format("<td>%s</td>", plant.getColor()));
            out.println(String.format("<td>%s</td>", plant.getSector()));
            out.println("</tr>");
        }
        out.println("</table>");
    }

    /**
     * Keys in locale properties file.
     */
    private interface LocaleKeys {

        String NUMBER = "plant_tag.number";
        String NAME = "plant_tag.name";
        String ORIGIN = "plant_tag.origin";
        String COLOR = "plant_tag.color";
        String SECTOR = "plant_tag.sector";
    }
}
