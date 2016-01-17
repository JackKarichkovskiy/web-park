/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.locale;

import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.webpark.configuration.AppConfiguration;
import org.webpark.configuration.exception.ConfigurationPropertyNotFoundException;
import static org.webpark.utils.ProjectUtils.checkNotNull;

/**
 * Class represents factory for creating localization bundles.
 *
 * @author Karichkovskiy Yevhen
 */
public class AppBundleFactory {

    /**
     * Default lang tag-key from app config file.
     */
    private final static String DEFAULT_LANG_TAG = "app_lang";

    /**
     * Error message for cannot found property from config file.
     */
    private final static String DEFAULT_LANG_ERROR = "Cannot find default language property (" + DEFAULT_LANG_TAG + ") in app config file";

    /**
     * Default lang if property file is empty.
     */
    private final static Language DEFAULT_LANGUAGE = Language.EN;

    /**
     * Default lang value that read from app config file.
     */
    private final static String APP_DEFAULT_LANG = AppConfiguration.getInstance().getProperty(DEFAULT_LANG_TAG);

    /**
     * Location of lang properties files.
     */
    private final static String BUNDLE_PATH = "locale.lang";

    /**
     * Singleton instance.
     */
    private static AppBundleFactory instance;

    /**
     * Standard language for logging and another messages.
     */
    private Language appLang;

    /**
     * Standard bundle of application.
     */
    private ResourceBundle bundle;

    private AppBundleFactory(String dummyStr) {
        initLocale();
    }

    /**
     * Returns Singleton instance.
     *
     * @return Singleton instance
     */
    public static AppBundleFactory getInstance() {
        if (instance == null) {
            instance = new AppBundleFactory(null);
        }
        return instance;
    }

    public ResourceBundle getAppBundle() {
        return bundle;
    }

    public Language getAppLanguage() {
        return appLang;
    }

    /**
     * Look up a bundle for input lang.
     *
     * @param lang - input language
     * @return appropriate bundle
     */
    public ResourceBundle createBundle(Language lang) {
        checkNotNull(lang);

        return ResourceBundle.getBundle(BUNDLE_PATH, lang.getLocale());
    }

    /**
     * Initialize standard language and bundle for application.
     */
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
