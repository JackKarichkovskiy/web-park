/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.filter;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.webpark.controller.command.WebTags;
import org.webpark.controller.uri.UriBuilder;
import org.webpark.locale.AppBundleFactory;

/**
 * Class that filters urls by pattern and allows access only for authorized
 * users.
 * It's in build mechanism to add filters.
 *
 * @author Karichkovskiy Yevhen
 */
@WebFilter(filterName = "/AuthenticationFilter", urlPatterns = "/secured/*")
public class AuthenticationFilter implements Filter {

    /**
     * Logging message tag for filtering urls.
     */
    private static final String FILTER_URI_TAG = "log.filter_uri";
    
    /**
     * Logging message tag for filtering urls with session info.
     */
    private static final String FILTER_URI_USER_SESSION_TAG = "log.filter_uri_user_session";
    
    /**
     * Logging message tag for situations of redirecting.
     */
    private static final String REDIRECTED_URI_TAG = "log.redirected_uri";
    
    /**
     * Application standard locale bundle.
     */
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();
    
    /**
     * URI of index page.
     */
    private static final String DEFAULT_PAGE = UriBuilder.getUri("init_page");

    /**
     * Object that contains the information of servlet context.
     */
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);
        if (session == null) {
            Logger.getLogger(AuthenticationFilter.class)
                    .info(String.format(BUNDLE.getString(FILTER_URI_TAG), uri));
        } else {
            Logger.getLogger(AuthenticationFilter.class)
                    .info(String.format(BUNDLE.getString(FILTER_URI_USER_SESSION_TAG),
                                    uri,
                                    session.getAttribute(WebTags.SESSION_USER_TAG),
                                    session.getId()));
        }

        if (session == null || session.getAttribute(WebTags.SESSION_USER_TAG) == null) {
            String redirect = DEFAULT_PAGE;
            Logger.getLogger(AuthenticationFilter.class).
                    info(String.format(BUNDLE.getString(REDIRECTED_URI_TAG), uri, redirect));
            res.sendRedirect(redirect);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
