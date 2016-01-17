/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.webpark.configuration.AppConfiguration;

/**
 * Class that sets character encoding for all requests from user.
 *
 * @author Karichkovskiy Yevhen
 */
@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

    /**
     * Tag-key to app config file to request encoding value.
     */
    private static final String REQUEST_ENCODING_TAG = "request_encoding";

    @Override
    public void init(FilterConfig config) throws ServletException {
        // NOOP.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String encoding = AppConfiguration.getInstance().getProperty(REQUEST_ENCODING_TAG);
        if (encoding != null) {
            request.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // NOOP.
    }
}
