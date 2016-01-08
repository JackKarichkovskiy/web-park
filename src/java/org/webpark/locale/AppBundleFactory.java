/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.locale;

import java.util.Locale;
import java.util.ResourceBundle;
import org.webpark.configuration.AppConfiguration;
import static org.webpark.utils.ProjectUtils.checkNotNull;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class AppBundleFactory {

    private final static String DEFAULT_LANG_TAG = "app_lang";
    private final static String APP_DEFAULT_LANG = AppConfiguration.getInstance().getProperty(DEFAULT_LANG_TAG);
    private final static String BUNDLE_PATH = "locale.lang";

    private static AppBundleFactory instance;

    private Language appLang;
    private ResourceBundle bundle;

    {
        if (APP_DEFAULT_LANG != null) {
            bundle = createBundle(appLang = Language.valueOf(APP_DEFAULT_LANG));
        } else {
            bundle = ResourceBundle.getBundle(BUNDLE_PATH, Locale.getDefault());
        }
    }

    private AppBundleFactory(String dummyStr) {
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

    public ResourceBundle createBundle(Language lang) {
        checkNotNull(lang);

        return ResourceBundle.getBundle(BUNDLE_PATH, lang.getLocale());
    }
}
