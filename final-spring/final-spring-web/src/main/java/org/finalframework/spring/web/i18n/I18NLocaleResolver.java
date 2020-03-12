package org.finalframework.spring.web.i18n;


import org.finalframework.core.Assert;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-12 12:21:41
 * @since 1.0
 */
public class I18NLocaleResolver extends CookieLocaleResolver {

    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
    private final List<Locale> supportedLocales = new ArrayList<Locale>(4);

    private String paramName;
    private String headerName;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {

        Locale locale = null;

        String lang = request.getParameter(paramName);
        if (Assert.nonBlank(lang)) {
            locale = Locale.forLanguageTag(lang);
        }

        if (locale == null && Assert.nonBlank(headerName)) {
            String header = request.getHeader(headerName);
            if (Assert.nonBlank(header)) {
                locale = Locale.forLanguageTag(header);
            }
        }

        if (locale == null && request.getHeader("Accept-Language") != null) {
            locale = request.getLocale();
            if (!isSupportedLocale(locale)) {
                locale = findSupportedLocale(request, locale);
            }
        }

        if (locale == null) {
            locale = super.resolveLocale(request);
        }

        if (!isSupportedLocale(locale)) {
            locale = getDefaultLocale();
        }

        return locale;
    }

    private boolean isSupportedLocale(Locale locale) {
        List<Locale> supportedLocales = getSupportedLocales();
        return (supportedLocales.isEmpty() || supportedLocales.contains(locale));
    }

    private Locale findSupportedLocale(HttpServletRequest request, Locale fallback) {
        Enumeration<Locale> requestLocales = request.getLocales();
        while (requestLocales.hasMoreElements()) {
            Locale locale = requestLocales.nextElement();
            if (getSupportedLocales().contains(locale)) {
                return locale;
            }
        }
        return fallback;
    }

    public List<Locale> getSupportedLocales() {
        return this.supportedLocales;
    }

    public void setSupportedLocales(List<Locale> locales) {
        this.supportedLocales.clear();
        if (locales != null) {
            this.supportedLocales.addAll(locales);
        }
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }
}

