package org.ifinal.finalframework.boot.autoconfigure.web.i18n;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "final.i18n")
public class I18NProperties implements Serializable {

    private static final String DEFAULT_LOCALE_PARAM_NAME = "lang";

    private static final String DEFAULT_LOCALE_HEADER_NAME = "lang";

    private static final String DEFAULT_LOCALE_COOKIE_NAME = "locale";

    private static final Integer DEFAULT_LOCALE_COOKIE_MAX_AGE = 24 * 3600;

    private static final String DEFAULT_LOCALE_COOKIE_PATH = "/";

    /**
     * default locale
     *
     * @see Locale#getDefault()
     */
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

    public void setDefaultLocale(final Locale defaultLocale) {

        this.defaultLocale = defaultLocale;
    }

    public List<Locale> getSupportedLocales() {

        return supportedLocales;
    }

    public void setSupportedLocales(final List<Locale> supportedLocales) {

        this.supportedLocales = supportedLocales;
    }

    public String getParamName() {

        return paramName;
    }

    public void setParamName(final String paramName) {

        this.paramName = paramName;
    }

    public String getHeaderName() {

        return headerName;
    }

    public void setHeaderName(final String headerName) {

        this.headerName = headerName;
    }

    public String getCookieName() {

        return cookieName;
    }

    public void setCookieName(final String cookieName) {

        this.cookieName = cookieName;
    }

    public Integer getCookieMaxAge() {

        return cookieMaxAge;
    }

    public void setCookieMaxAge(final Integer cookieMaxAge) {

        this.cookieMaxAge = cookieMaxAge;
    }

    public String getCookieDomain() {

        return cookieDomain;
    }

    public void setCookieDomain(final String cookieDomain) {

        this.cookieDomain = cookieDomain;
    }

    public String getCookiePath() {

        return cookiePath;
    }

    public void setCookiePath(final String cookiePath) {

        this.cookiePath = cookiePath;
    }

}

