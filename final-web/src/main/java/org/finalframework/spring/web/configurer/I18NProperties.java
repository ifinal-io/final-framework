

package org.finalframework.spring.web.configurer;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-12 12:13:34
 * @since 1.0
 */
@ConfigurationProperties(prefix = "final.i18n")
public class I18NProperties implements Serializable {

    private static final String DEFAULT_LOCALE_PARAM_NAME = "lang";
    private static final String DEFAULT_LOCALE_HEADER_NAME = "lang";
    private static final String DEFAULT_LOCALE_COOKIE_NAME = "locale";
    private static final Integer DEFAULT_LOCALE_COOKIE_MAX_AGE = 24 * 3600;
    private static final String DEFAULT_LOCALE_COOKIE_PATH = "/";

    private Locale defaultLocale = Locale.getDefault();

    private List<Locale> supportedLocales;

    private String paramName = DEFAULT_LOCALE_PARAM_NAME;
    private String headerName = DEFAULT_LOCALE_HEADER_NAME;
    private String cookieName = DEFAULT_LOCALE_COOKIE_NAME;
    private Integer cookieMaxAge = DEFAULT_LOCALE_COOKIE_MAX_AGE;
    private String cookieDomain;
    private String cookiePath = DEFAULT_LOCALE_COOKIE_PATH;

    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public List<Locale> getSupportedLocales() {
        return supportedLocales;
    }

    public void setSupportedLocales(List<Locale> supportedLocales) {
        this.supportedLocales = supportedLocales;
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

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public Integer getCookieMaxAge() {
        return cookieMaxAge;
    }

    public void setCookieMaxAge(Integer cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public String getCookiePath() {
        return cookiePath;
    }

    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }
}

