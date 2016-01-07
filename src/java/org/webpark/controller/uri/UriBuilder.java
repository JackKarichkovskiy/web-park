/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.uri;

import org.webpark.configuration.AppConfiguration;
import org.webpark.controller.command.CommandResult;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class UriBuilder {

    private static final String APP_NAME_TAG = "app_name";
    private static final String APP_NAME = AppConfiguration.getInstance().getProperty(APP_NAME_TAG);
    private static final String INIT_PAGE_TAG = "init_page";
    private static final String INIT_PAGE = AppConfiguration.getInstance().getProperty(INIT_PAGE_TAG);
    private static final String ACCESS_DENIED_TAG = "access_denied_page";
    private static final String ACCESS_DENIED_PAGE = AppConfiguration.getInstance().getProperty(ACCESS_DENIED_TAG);
    private static final String ERROR_PAGE_TAG = "error_page";
    private static final String ERROR_PAGE = AppConfiguration.getInstance().getProperty(ACCESS_DENIED_TAG);
    private static final String ALL_PLANTS_TAG = "all_plants";
    private static final String ALL_PLANTS_PAGE = AppConfiguration.getInstance().getProperty(ALL_PLANTS_TAG);

    public static String getUri(String uriName) {
        return getUri(uriName, CommandResult.JumpType.REDIRECT);
    }
    
    public static String getUri(String uriName, CommandResult.JumpType jumpType) {
        StringBuffer resultUri;
        if (jumpType.equals(CommandResult.JumpType.REDIRECT)) {
            resultUri = new StringBuffer(APP_NAME + "/");
        }else{
            resultUri = new StringBuffer("");
        } 

        switch (uriName) {
            case ACCESS_DENIED_TAG:
                resultUri.append(ACCESS_DENIED_PAGE);
                break;
            case ERROR_PAGE_TAG:
                resultUri.append(ERROR_PAGE);
                break;
            case ALL_PLANTS_TAG:
                resultUri.append(ALL_PLANTS_PAGE);
                break;
            default:
            case INIT_PAGE_TAG:
                resultUri.append(INIT_PAGE);
                break;
        }

        return resultUri.toString();
    }

}
