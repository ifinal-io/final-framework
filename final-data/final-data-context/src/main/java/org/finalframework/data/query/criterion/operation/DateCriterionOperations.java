package org.finalframework.data.query.criterion.operation;


import org.finalframework.core.formatter.DateFormatter;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-08 22:13:20
 * @since 1.0
 */
public class DateCriterionOperations extends AbsDateCriterionOperations<Date> {

    public static final DateCriterionOperations INSTANCE = new DateCriterionOperations();

    public DateCriterionOperations() {
        super(Date.class);
    }

    @Override
    protected String date(Date date) {
        return DateFormatter.YYYY_MM_DD.format(date);
    }

    @Override
    protected String dateTime(Date date) {
        return DateFormatter.YYYY_MM_DD_HH_MM_SS.format(date);
    }
}
