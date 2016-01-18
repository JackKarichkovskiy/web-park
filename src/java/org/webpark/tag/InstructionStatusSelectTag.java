/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.tag;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.webpark.controller.command.WebTags;
import org.webpark.dao.entities.Instruction.Status;
import org.webpark.locale.AppBundleFactory;
import org.webpark.locale.Language;

/**
 * Class realizes custom jsp tag 'instructionStatuses'.
 * 
 * @author Karichkovskiy Yevhen
 */
public class InstructionStatusSelectTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        HttpSession session = request.getSession();
        
        Locale curLocale = (Locale) Config.get(session, Config.FMT_LOCALE);
        Language sessionLang = Language.getLanguageByLocale(curLocale);
        ResourceBundle sessionBundle = AppBundleFactory.getInstance().createBundle(sessionLang);

        JspWriter out = getJspContext().getOut();
        out.println(String.format("<select name=\"%s\">", WebTags.INSTRUCTION_STATUS_TAG));
        for (Status status : Status.values()) {
            out.println(String.format("<option value=\"%s\">%s</option>",
                    status, 
                    sessionBundle.getString(status.getLocaleKey())));
        }
        out.println("</select>");
    }
}
