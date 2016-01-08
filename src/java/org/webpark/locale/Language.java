/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.locale;

import java.util.Locale;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public enum Language {

    EN(Locale.US), RU("ru", "RU"), UA("uk", "UA");

    private final String lang;

    private final String country;

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
}
