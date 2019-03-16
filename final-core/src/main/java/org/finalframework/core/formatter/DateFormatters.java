package org.finalframework.core.formatter;

import org.finalframework.core.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-15 21:58:15
 * @since 1.0
 */
public class DateFormatters implements Formatters<Date> {

    public static final DateFormatters DEFAULT = new DateFormatters();

    private final List<DateFormatter> dateFormatters = new ArrayList<>();

    public DateFormatters(List<DateFormatter> dateFormatters) {
        this.dateFormatters.addAll(dateFormatters);
    }

    public DateFormatters() {
        this(Arrays.asList(
                DateFormatter.YYYY_MM_DD_HH_MM_SS, DateFormatter.YYYY_MM_DD,
                DateFormatter.YYYY__MM__DD_HH_MM_SS, DateFormatter.YYYY__MM__DD,
                DateFormatter.YYYYMMDD_HH_MM_SS, DateFormatter.YYYYMMDD,
                DateFormatter.YYYYMMDDHHMMSS
        ));
    }

    @Override
    public Date parse(String source) {
        if (Assert.isEmpty(source)) {
            return null;
        }
        for (DateFormatter formatter : dateFormatters) {
            if (formatter.matches(source)) {
                return formatter.parse(source);
            }
        }
        return null;
    }
}
