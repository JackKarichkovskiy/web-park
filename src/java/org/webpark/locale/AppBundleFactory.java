/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.locale;

import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.webpark.configuration.AppConfiguration;
import org.webpark.configuration.exception.ConfigurationPropertyNotFoundException;
import static org.webpark.utils.ProjectUtils.checkNotNull;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class AppBundleFactory {

    private final static String DEFAULT_LANG_TAG = "app_lang";
    private final static String DEFAULT_LANG_ERROR = "Cannot find default language property (" + DEFAULT_LANG_TAG + ") in app config file";
    private final static Language DEFAULT_LANGUAGE = Language.EN;
    private final static String APP_DEFAULT_LANG = AppConfiguration.getInstance().getProperty(DEFAULT_LANG_TAG);
    private final static String BUNDLE_PATH = "locale.lang";

    private static AppBundleFactory instance;

    private Language appLang;
    private ResourceBundle bundle;

    private AppBundleFactory(String dummyStr) {
        initLocale();
    }

    public static AppBundleFactory getInstance() {
        if (instance == null) {
            instance = new AppBundleFactory(null);
        }
        return instance;
    }

    public ResourceBundle getAppBundle() {
        return bundle;
    }
    
    public Language getAppLanguage(){
        return appLang;
    }

    public ResourceBundle createBundle(Language lang) {
        checkNotNull(lang);

        return ResourceBundle.getBundle(BUNDLE_PATH, lang.getLocale());
    }

    private void initLocale() {
        if (APP_DEFAULT_LANG == null) {
            Logger.getLogger(AppBundleFactory.class).
                    error(DEFAULT_LANG_ERROR, new ConfigurationPropertyNotFoundException());
            bundle = createBundle(appLang = DEFAULT_LANGUAGE);
        } else {
            bundle = createBundle(appLang = Language.valueOf(APP_DEFAULT_LANG));
        }
    }
}
