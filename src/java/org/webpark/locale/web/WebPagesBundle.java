/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.locale.web;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.log4j.Logger;
import org.webpark.locale.AppBundleFactory;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class WebPagesBundle extends ListResourceBundle{
    
    protected final AppBundleFactory factory = AppBundleFactory.getInstance();

    @Override
    protected Object[][] getContents() {
        ResourceBundle appBundle = factory.getAppBundle();
        return loadBundle(appBundle);
    }

    protected Object[][] loadBundle(ResourceBundle appBundle) {
        Set<String> keySet = appBundle.keySet();
        Object[][] result = new Object[keySet.size()][2];
        int index = 0;
        for(String key : keySet){
            result[index][0] = key;
            result[index][1] = appBundle.getObject(key);
            index++;
        }
        return result;
    }
    
    
}
