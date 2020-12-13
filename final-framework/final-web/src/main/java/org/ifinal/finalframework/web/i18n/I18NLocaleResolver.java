package org.ifinal.finalframework.web.i18n;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
public class I18NLocaleResolver extends CookieLocaleResolver {

    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";

    private final List<Locale> supportedLocales = new ArrayList<>(4);

    private String paramName;

    private String headerName;

    @Override
    public Locale resolveLocale(final @NonNull HttpServletRequest request) {

        Locale locale = null;

        String lang = request.getParameter(paramName);
        if (Asserts.nonBlank(lang)) {
            locale = Locale.forLanguageTag(lang);
        }

        if (locale == null && Asserts.nonBlank(headerName)) {
            String header = request.getHeader(headerName);
            if (Asserts.nonBlank(header)) {
                locale = Locale.forLanguageTag(header);
            }
        }

        if (locale == null && request.getHeader(ACCEPT_LANGUAGE_HEADER) != null) {
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

    private boolean isSupportedLocale(final Locale locale) {

        return supportedLocales.isEmpty() || supportedLocales.contains(locale);
    }

    private Locale findSupportedLocale(final HttpServletRequest request, final Locale fallback) {

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

    public void setSupportedLocales(final List<Locale> locales) {

        this.supportedLocales.clear();
        if (locales != null) {
            this.supportedLocales.addAll(locales);
        }
    }

}

