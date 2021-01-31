package org.ifinal.finalframework.web.converter;

import java.time.LocalDateTime;
import org.ifinal.finalframework.util.format.LocalDateTimeFormatters;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * LocalDateTimeConverter.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Nullable
    @Override
    public LocalDateTime convert(final String source) {
        return LocalDateTimeFormatters.DEFAULT.parse(source);
    }

}
