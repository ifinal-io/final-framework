package org.finalframework.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-19 21:11:35
 * @since 1.0
 */
public interface Dates {

    @Nullable
    static Date to(@Nullable LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime)
                .map(it -> Date.from(it.atZone(LocaleContextHolder.getTimeZone().toZoneId()).toInstant()))
                .orElse(null);
    }

    @Nullable
    static LocalDateTime from(@Nullable Date date) {
        return Optional.ofNullable(date)
                .map(it -> it.toInstant().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toLocalDateTime())
                .orElse(null);
    }


}
