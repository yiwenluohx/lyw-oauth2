package com.lyw.cloud.oauth2.server.component;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/10/27 0027 13:45
 */
public class MessagesLocalResolver implements LocaleResolver {

    @Nullable
    private Locale defaultLocale;

    public void setDefaultLocale(@Nullable Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    @Nullable
    public Locale getDefaultLocale() {
        return this.defaultLocale;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale defaultLocale = this.getDefaultLocale();
        if(defaultLocale != null && request.getHeader("Accept-Language") == null) {
            return defaultLocale;
        } else {
            Locale requestLocale = request.getLocale();
            String localeFlag = request.getParameter("lang");
            //LOG.info("localeFlag:{}",localeFlag);
            if (!StringUtils.isEmpty(localeFlag)) {
                String[] split = localeFlag.split("_");
                requestLocale = new Locale(split[0], split[1]);
            }
            return requestLocale;
        }
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
