/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.locale;

import java.util.Locale;

/**
 * Enumeration of available languages in application.
 *
 * @author Karichkovskiy Yevhen
 */
public enum Language {

    EN(Locale.US), RU("ru", "RU"), UA("uk", "UA");

    /**
     * Language code.
     */
    private final String lang;

    /**
     * Country code.
     */
    private final String country;

    /**
     * Locale object of language.
     */
    private final Locale locale;

    private Language(String lang, String country) {
        this.lang = lang;
        this.country = country;
        this.locale = new Locale(lang, country);
    }

    private Language(Locale locale) {
        this.locale = locale;
        this.lang = locale.getLanguage();
        this.country = locale.getCountry();
    }

    public String getCountry() {
        return country;
    }

    public String getLang() {
        return lang;
    }

    public Locale getLocale() {
        return locale;
    }

    /**
     * Finds Language by locale object.
     *
     * @param locale - input locale
     * @return Language enumeration value
     */
    public static Language getLanguageByLocale(Locale locale) {
        for (Language lang : Language.values()) {
            if (lang.getLocale().equals(locale)) {
                return lang;
            }
        }
        return null;
    }

    /**
     * Finds Language by lang code string.
     *
     * @param shortLang - input lang code
     * @return Language enumeration value
     */
    public static Language getLanguageByShortLang(String shortLang) {
        for (Language lang : Language.values()) {
            if (lang.getLang().equals(shortLang)) {
                return lang;
            }
        }
        return null;
    }
}
