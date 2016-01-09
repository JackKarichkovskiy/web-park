/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.command.concrete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import org.webpark.controller.command.Command;
import org.webpark.controller.command.CommandResult;
import org.webpark.controller.command.WebTags;
import org.webpark.locale.Language;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class ChangeLanguageCommand implements Command{

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String shortLang = (String) request.getParameter(WebTags.LANGUAGE_SELECT_TAG);
        String currentUri = (String) request.getParameter(WebTags.CURRENT_URI_TAG);
        Language lang = Language.getLanguageByShortLang(shortLang);
        Config.set(request.getSession(false), Config.FMT_LOCALE, lang.getLocale());
        return new CommandResult(currentUri, CommandResult.JumpType.REDIRECT);
    }
    
}
