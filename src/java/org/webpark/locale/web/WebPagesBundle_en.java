/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.locale.web;

import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.webpark.locale.Language;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class WebPagesBundle_en extends WebPagesBundle{

    @Override
    protected Object[][] getContents() {
        ResourceBundle bundle = factory.createBundle(Language.EN);
        return loadBundle(bundle);
    }
    
    
}
