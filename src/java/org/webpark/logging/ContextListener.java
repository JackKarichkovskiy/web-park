/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.logging;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Karichkovskiy Yevhen
 */
@WebListener("application context listener")
public class ContextListener implements ServletContextListener {

    private static final String lOG4J_CONTEXT_PARAM = "log4j-config-location";

    /**
     * Initialize log4j when the application is being started
     *
     * @param event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        // initialize log4j
        ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter(lOG4J_CONTEXT_PARAM);
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;

        PropertyConfigurator.configure(fullPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // do nothing
    }
}
