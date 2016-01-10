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
import org.webpark.dao.entities.User;
import org.webpark.dao.exception.DAOException;
import org.webpark.locale.AppBundleFactory;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class AllForestersSelectTag extends SimpleTagSupport {

    private static final String DATABASE_CONN_ERROR = "log.database_conn_error";
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();
    private static final String ERROR_PAGE = UriBuilder.getUri("error_page", CommandResult.JumpType.FORWARD);

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

        List<User> allForesters = null;
        try {
            allForesters = AppDaoFactory.getInstance().getUserDao().getAllForesters();
        } catch (DAOException ex) {
            String errorMessage = BUNDLE.getString(DATABASE_CONN_ERROR);
            Logger.getLogger(AllForestersSelectTag.class).error(errorMessage, ex);
            request.setAttribute(WebTags.ERROR_MESSAGE_TAG, errorMessage);
            request.setAttribute(WebTags.ERROR_CODE_TAG, 500);
            try {
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            } catch (ServletException ex1) {
                Logger.getLogger(AllForestersSelectTag.class).error(null, ex1);
            }
        }

        if (allForesters == null || allForesters.isEmpty()) {
            return;
        }

        JspWriter out = getJspContext().getOut();
        out.println(String.format("<select name=\"%s\">", WebTags.PERFORMED_BY_TAG));
        for(User forester : allForesters){
            out.println(String.format("<option value=\"%s\">%s</option>", forester.getId(), forester.getEmail()));
        }
        
        out.println("</select>");
    }
}
