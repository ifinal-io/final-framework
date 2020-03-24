package org.finalframework.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-19 21:11:35
 * @since 1.0
 */
public interface Dates {

    @Nullable
    static Date from(@Nullable LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime)
            .map(it -> Date.from(it.atZone(ZoneId.systemDefault()).toInstant()))
            .orElse(null);
    }

    @Nullable
    static LocalDateTime to(@Nullable Date date) {
        return Optional.ofNullable(date)
            .map(it -> it.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
            .orElse(null);
    }
}
