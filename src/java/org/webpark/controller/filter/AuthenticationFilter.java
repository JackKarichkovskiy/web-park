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
 *
 * @author Karichkovskiy Yevhen
 */
@WebFilter(filterName = "/AuthenticationFilter", urlPatterns = "/secured/*")
public class AuthenticationFilter implements Filter {

    private static final String FILTER_URI_TAG = "log.filter_uri";
    private static final String FILTER_URI_USER_SESSION_TAG = "log.filter_uri_user_session";
    private static final String REDIRECTED_URI_TAG = "log.redirected_uri";
    private static final ResourceBundle BUNDLE = AppBundleFactory.getInstance().getAppBundle();
    private static final String DEFAULT_PAGE = UriBuilder.getUri("init_page");

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
                                    session.getAttribute(WebTags.USER_TAG),
                                    session.getId()));
        }
        
        if (session == null || session.getAttribute(WebTags.USER_TAG) == null) {
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
