/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.locale.web;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import org.webpark.locale.AppBundleFactory;

/**
 * Class gives localized messages for jsp pages.
 *
 * @author Karichkovskiy Yevhen
 */
public class WebPagesBundle extends ListResourceBundle {

    /**
     * Size of array for key-value pairs.
     */
    private static final int KEY_VALUE_PAIR = 2;

    /**
     * Application standard localization bundle factory.
     */
    protected final AppBundleFactory factory = AppBundleFactory.getInstance();

    @Override
    protected Object[][] getContents() {
        ResourceBundle appBundle = factory.getAppBundle();
        return loadBundle(appBundle);
    }

    /**
     * Loads all key-value pairs from input bundle.
     *
     * @param appBundle - input bundle
     * @return array of key-value localized messages
     */
    protected Object[][] loadBundle(ResourceBundle appBundle) {
        Set<String> keySet = appBundle.keySet();
        Object[][] result = new Object[keySet.size()][KEY_VALUE_PAIR];
        int index = 0;
        for (String key : keySet) {
            result[index][0] = key;
            result[index][1] = appBundle.getObject(key);
            index++;
        }
        return result;
    }

}
