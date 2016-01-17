/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.uri;

import org.webpark.configuration.AppConfiguration;
import org.webpark.controller.command.CommandResult;

/**
 * Class that constructs URIs by its tag and jump type.
 *
 * @author Karichkovskiy Yevhen
 */
public class UriBuilder {

    /**
     * Tag-key to application name in app config file.
     */
    private static final String APP_NAME_TAG = "app_name";

    /**
     * Name of application.
     */
    private static final String APP_NAME = AppConfiguration.getInstance().getProperty(APP_NAME_TAG);

    /**
     * Tag-key to index page location in app config file.
     */
    private static final String INIT_PAGE_TAG = "init_page";

    /**
     * URI to index page.
     */
    private static final String INIT_PAGE = AppConfiguration.getInstance().getProperty(INIT_PAGE_TAG);

    /**
     * Tag-key to access denied page location in app config file.
     */
    private static final String ACCESS_DENIED_TAG = "access_denied_page";

    /**
     * URI to access denied page.
     */
    private static final String ACCESS_DENIED_PAGE = AppConfiguration.getInstance().getProperty(ACCESS_DENIED_TAG);

    /**
     * Tag-key to error page location in app config file.
     */
    private static final String ERROR_PAGE_TAG = "error_page";

    /**
     * URI to error page.
     */
    private static final String ERROR_PAGE = AppConfiguration.getInstance().getProperty(ERROR_PAGE_TAG);

    /**
     * Tag-key to account page location in app config file.
     */
    private static final String ACCOUNT_PAGE_TAG = "account_page";

    /**
     * URI to account page.
     */
    private static final String ACCOUNT_PAGE = AppConfiguration.getInstance().getProperty(ACCOUNT_PAGE_TAG);

    /**
     * Gets uri by its tag name. Default jump type: redirecting.
     *
     * @param uriName - tag name of uri in app config file
     * @return uri location of page
     */
    public static String getUri(String uriName) {
        return getUri(uriName, CommandResult.JumpType.REDIRECT);
    }

    /**
     * Gets uri by its tag name and jump type.
     *
     * @param uriName - tag name of uri in app config file
     * @param jumpType - type of transition to the next page
     * @return uri location of page
     */
    public static String getUri(String uriName, CommandResult.JumpType jumpType) {
        StringBuffer resultUri;
        if (jumpType.equals(CommandResult.JumpType.REDIRECT)) {
            resultUri = new StringBuffer(APP_NAME + "/");
        } else {
            resultUri = new StringBuffer("");
        }

        switch (uriName) {
            case ACCESS_DENIED_TAG:
                resultUri.append(ACCESS_DENIED_PAGE);
                break;
            case ERROR_PAGE_TAG:
                resultUri.append(ERROR_PAGE);
                break;
            case ACCOUNT_PAGE_TAG:
                resultUri.append(ACCOUNT_PAGE);
                break;
            default:
            case INIT_PAGE_TAG:
                resultUri.append(INIT_PAGE);
                break;
        }

        return resultUri.toString();
    }

}
