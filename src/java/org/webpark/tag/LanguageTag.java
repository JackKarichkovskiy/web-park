/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.tag;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.webpark.controller.command.WebTags;
import org.webpark.locale.AppBundleFactory;
import org.webpark.locale.Language;

/**
 * Class realizes custom jsp tag 'lang'.
 *
 * @author Karichkovskiy Yevhen
 */
public class LanguageTag extends SimpleTagSupport {

    /**
     * Standard application localization bundle.
     */
    private static final AppBundleFactory FACTORY = AppBundleFactory.getInstance();

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpSession session = request.getSession(false);
        Locale currentLocale;
        if (Config.get(session, Config.FMT_LOCALE) == null) {
            Config.set(session, Config.FMT_LOCALE, currentLocale = FACTORY.getAppLanguage().getLocale());
        } else {
            currentLocale = (Locale) Config.get(session, Config.FMT_LOCALE);
        }
        Language sessionLang = Language.getLanguageByLocale(currentLocale);

        JspWriter out = getJspContext().getOut();
        out.println(String.format("<form name=\"%s\" method=\"POST\" action=\"/WebPark/Controller?command=changeLang\">", WebTags.LANGUAGE_FORM_TAG));
        out.println(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\" />", WebTags.CURRENT_URI_TAG, request.getRequestURI()));
        out.println(String.format("<select name=\"%s\" onchange=\"document.forms['%s'].submit();\">", WebTags.LANGUAGE_SELECT_TAG, WebTags.LANGUAGE_FORM_TAG));
        for (Language lang : Language.values()) {
            String selected = "";
            if (lang.equals(sessionLang)) {
                selected = "selected=\"selected\"";
            }
            out.println(String.format("<option %s>%s</option>", selected, lang.getLang()));
        }
        out.println("</select>");
        out.println("</form>");
    }
}
